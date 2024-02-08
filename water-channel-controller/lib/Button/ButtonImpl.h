#ifndef __BUTTONIMPL__
#define __BUTTONIMPL__

#include "Button.h"

class ButtonImpl: public Button {
    protected:
        int pin;
        unsigned long lastPressedTime;
    public:
        ButtonImpl(int pin);
        bool isPressed();
        bool isPressedDebounced();
};

#endif
