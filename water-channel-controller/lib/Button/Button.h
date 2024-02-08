#ifndef __BUTTON__
#define __BUTTON__

class Button {
    public:
        virtual bool isPressed() = 0;
        virtual bool isPressedDebounced() = 0;
};

#endif
