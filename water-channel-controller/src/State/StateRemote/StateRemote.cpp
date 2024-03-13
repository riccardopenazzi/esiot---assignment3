#include <Arduino.h>
#include <Scheduler.h>
#include <Config.h>
#include "StateRemote.h"
#include "EnableinterruptLib.h"

bool goToManual;

StateName StateRemote::name(){
    return StateName::Manual;
}

StateRemote::StateRemote(int valveAngle, Components* components, Scheduler* scheduler){
    this->components = components;
    this->scheduler = scheduler;
    this->valveAngle = valveAngle;
    goToManual = false;

    this->components->getValve()->on();
    this->components->getValve()->setPosition(this->valveAngle);

    enableInterruptLib(PIN_BUTTON, this->buttonPressedCallback, RISING);
}

bool StateRemote::goNext(){
    return goToManual;
}

void StateRemote::openValve(){
    this->valveAngle = VALVE_OPEN;
    this->components->getValve()->setPosition(this->valveAngle);
}

void StateRemote::closeValve(){
    this->valveAngle = VALVE_CLOSE;
    this->components->getValve()->setPosition(this->valveAngle);
}

StateRemote::~StateRemote(){
    this->components->getValve()->off();
    disableInterruptLib(PIN_BUTTON);
}