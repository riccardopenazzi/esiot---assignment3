#include "Arduino.h"
#include "Pir.h"

Pir::Pir(int pin) {
    this->initialCalibrationTime = millis();
    this->pin = pin;
    pinMode(pin,INPUT);
}

bool Pir::isCalibrationFinished() {
    unsigned long currentTime = millis();
    return currentTime-(this->initialCalibrationTime) > this->CALIBRATION_TIME_MILLI_SEC;
}

bool Pir::isMotionDetected() {
    return digitalRead(pin) == HIGH;
}