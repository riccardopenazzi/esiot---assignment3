#include <Arduino.h>
#include <Scheduler.h>
#include <Config.h>
#include "StateAutomatic.h"
#include "EnableinterruptLib.h"
#include "Tasks/RemoteValve/RemoteValve.h"
#include "MsgService.h"

bool goToManual;

StateName StateAutomatic::name(){
    return StateName::Automatic;
}

StateAutomatic::StateAutomatic(int valveAngle, Components* components, Scheduler* scheduler){
    this->components = components;
    this->scheduler = scheduler;
    this->valveAngles = new ValveAngles(valveAngle, valveAngle);
    goToManual = false;

    this->components->getValve()->on();
    this->components->getValve()->setPosition(this->valveAngle);

    components->getLcd()->clear();
    components->getLcd()->setCursor(0, 0); 
    components->getLcd()->print("Automatic");

    enableInterruptLib(PIN_BUTTON, this->buttonPressedCallback, RISING);

    int openingPercentage = map(valveAngle, 0, 180, 0, 100); 
    MsgService.sendMsg("{\"mode\":\"Automatic\",\"valve\":\""+String(openingPercentage)+"\"}");

    //automatic valve controlled by serial communication
    Task* remoteValveTask = new RemoteValve(this->components, this->valveAngles);
    remoteValveTask->init(100);
    this->scheduler->addTask(remoteValveTask);
}

bool StateAutomatic::goNext(){
    return goToManual;
}

StateAutomatic::~StateAutomatic(){
    scheduler->removeLastTask(); //remove remote valve task
    this->components->getValve()->off();
}