#include "EnableInterruptLib.h"

#include <EnableInterrupt.h>

void enableInterruptLib(int pin, void (*function)(), int mode) {
    enableInterrupt(pin, function, mode);
}

void disableInterruptLib(int pin) {
    disableInterrupt(pin);
}