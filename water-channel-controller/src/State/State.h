#ifndef __STATE__
#define __STATE__

#include "States.h"

class State {        
    public:
        /*
        Return the StateName associated to the State
        */
        virtual StateName name() = 0;
        /*
        Check if the current state activities are finished,
        and the state can be changed
        */
        virtual bool goNext() = 0;

        virtual ~State() = default;
};

#endif
