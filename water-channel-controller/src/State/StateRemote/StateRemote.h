#ifndef __STATE_REMOTE__
#define __STATE_REMOTE__

#include <Scheduler.h>
#include "State/State.h"
#include "Components/Components.h"

extern bool goToManual;

class StateRemote: public State {
    private:
        static void buttonPressedCallback(){
            goToManual = true;
        }
        Components* components;
        Scheduler* scheduler;
    public:
        StateRemote(int valveAngle, Components* components, Scheduler* scheduler);
        ~StateRemote();
        StateName name();
        bool goNext();
        void openValve();
        void closeValve();
};

#endif