#ifndef __STATE_MANUAL__
#define __STATE_MANUAL__

#include <Scheduler.h>
#include "State/State.h"
#include "Components/Components.h"

extern bool backToRemote;

class StateManual: public State {
    private:
        static void buttonPressedCallback(){
            backToRemote = true;
        }
        Components* components;
        Scheduler* scheduler;
    public:
        StateManual(int valveAngle, Components* components, Scheduler* scheduler);
        ~StateManual();
        StateName name();
        bool goNext();
        void openValve();
        void closeValve();
};

#endif