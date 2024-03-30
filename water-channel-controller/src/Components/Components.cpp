#include "Arduino.h"
#include "Components.h"

#include <Config.h>
#include <ButtonImpl.h>
#include <Sonar.h>
#include <LcdScreen.h>
#include <Led.h>
#include <Pir.h>
#include <servo_motor_impl.h>
#include <AnalogTempSensor.h>

Components::Components() {
    this->modeButton = new ButtonImpl(PIN_BUTTON);
    this->valve = new ServoMotorImpl(PIN_SERVO);
    this->lcd = new LcdScreen();

    this->valve->on();
    pinMode(PIN_POTENTIOMETER, INPUT);
}

ButtonImpl* Components::getModeButton() {
    return modeButton;
}

ServoMotorImpl* Components::getValve() {
    return valve;
}

LcdScreen* Components::getLcd() {
    return lcd;
}
