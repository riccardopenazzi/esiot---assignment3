#include <Arduino.h>
#include <Scheduler.h>
#include <Config.h>
#include "StateManual.h"
#include "EnableinterruptLib.h"

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
    this->components->getValve()->setPosition(this->valveAngle);

    enableInterruptLib(PIN_BUTTON,this->buttonPressedCallback , RISING);
}

bool StateManual::goNext(){
    return backToRemote;
}

void StateManual::openValve(){
    this->valveAngle = VALVE_OPEN;
    this->components->getValve()->setPosition(this->valveAngle);
}

void StateManual::closeValve(){
    this->valveAngle = VALVE_CLOSE;
    this->components->getValve()->setPosition(this->valveAngle);
}

StateManual::~StateManual(){
    this->components->getValve()->off();
    //disableInterruptLib(PIN_BUTTON);
}