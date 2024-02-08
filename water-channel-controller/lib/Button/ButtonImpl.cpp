#include "ButtonImpl.h"
#include "Arduino.h"

#define DEBOUNCE_TIME 200

ButtonImpl::ButtonImpl(int pin) {
    this->pin = pin;
    pinMode(pin, INPUT_PULLUP);
    this->lastPressedTime = 0;
}

bool ButtonImpl::isPressed() {
    return digitalRead(pin) == LOW;
}


bool ButtonImpl::isPressedDebounced() {
    if(digitalRead(pin) == LOW){
        unsigned long now = millis();
        if(now - lastPressedTime > DEBOUNCE_TIME){
            lastPressedTime = millis();
            return true;
        }
    }
    return false;
}
