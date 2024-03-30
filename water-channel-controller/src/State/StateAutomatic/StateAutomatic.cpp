#include <Arduino.h>
#include <Scheduler.h>
#include <Config.h>
#include "StateAutomatic.h"
#include "EnableinterruptLib.h"

bool goToManual;

StateName StateAutomatic::name(){
    return StateName::Automatic;
}

StateAutomatic::StateAutomatic(int valveAngle, Components* components, Scheduler* scheduler){
    this->components = components;
    this->scheduler = scheduler;
    this->valveAngle = valveAngle;
    goToManual = false;

    this->components->getValve()->on();
    this->components->getValve()->setPosition(this->valveAngle);

    components->getLcd()->clear();
    components->getLcd()->setCursor(0, 0); 
    components->getLcd()->print("Automatic");

    enableInterruptLib(PIN_BUTTON, this->buttonPressedCallback, RISING);
}

bool StateAutomatic::goNext(){
    return goToManual;
}

void StateAutomatic::openValve(){
    this->valveAngle = VALVE_OPEN;
    this->components->getValve()->setPosition(this->valveAngle);
}

void StateAutomatic::closeValve(){
    this->valveAngle = VALVE_CLOSE;
    this->components->getValve()->setPosition(this->valveAngle);
}

StateAutomatic::~StateAutomatic(){
    this->components->getValve()->off();
}