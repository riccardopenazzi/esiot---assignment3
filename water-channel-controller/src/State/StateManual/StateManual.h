#ifndef __STATE_MANUAL__
#define __STATE_MANUAL__

#include <Scheduler.h>
#include "State/State.h"
#include "Components/Components.h"
#include "State/StateManual/ValveAngles.h"

extern bool backToRemote;

class StateManual: public State {
    private:
        static void buttonPressedCallback(){
            backToRemote = true;
        }
        Components* components;
        Scheduler* scheduler;
        ValveAngles* valveAngles;
    public:
        StateManual(int valveAngle, Components* components, Scheduler* scheduler);
        ~StateManual();
        StateName name();
        bool goNext();
};

#endif