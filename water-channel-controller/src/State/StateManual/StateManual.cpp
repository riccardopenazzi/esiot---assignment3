#include <Arduino.h>
#include <Scheduler.h>
#include <Config.h>
#include "StateManual.h"
#include "EnableinterruptLib.h"
#include "Tasks/ManualValve/ManualValve.h"
#include "MsgService.h"

bool backToRemote;

StateName StateManual::name(){
    return StateName::Manual;
}

StateManual::StateManual(int valveAngle, Components* components, Scheduler* scheduler){
    this->components = components;
    this->scheduler = scheduler;
    this->valveAngle = valveAngle;
    backToRemote = false;

    this->components->getValve()->on();

    enableInterruptLib(PIN_BUTTON,this->buttonPressedCallback , RISING);

    components->getLcd()->clear();
    components->getLcd()->setCursor(0, 0); 
    components->getLcd()->print("Manual");

    MsgService.sendMsg("Manual");

    //manual valve controlled by potentiometer
    Task* manaulValveTask = new ManualValve(this->components);
    manaulValveTask->init(100);
    this->scheduler->addTask(manaulValveTask);
}

bool StateManual::goNext(){
    return backToRemote;
}

StateManual::~StateManual(){
    scheduler->removeLastTask(); //remove manual valve task
    this->components->getValve()->off();
}