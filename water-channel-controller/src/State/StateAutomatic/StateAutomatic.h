#ifndef __STATE_AUTOMATIC__
#define __STATE_AUTOMATIC__

#include <Scheduler.h>
#include "State/State.h"
#include "Components/Components.h"
#include <ValveAngles.h>

extern bool goToManual;

class StateAutomatic: public State {
    private:
        static void buttonPressedCallback(){
            goToManual = true;
        }
        Components* components;
        Scheduler* scheduler;
        ValveAngles* valveAngles;
    public:
        StateAutomatic(int valveAngle, Components* components, Scheduler* scheduler);
        ~StateAutomatic();
        StateName name();
        bool goNext();
};

#endif