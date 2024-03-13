#ifndef __STATE_MANAGER_TASK__
#define __STATE_MANAGER_TASK__

#include <Scheduler.h>
#include <Task.h>
#include "Components/Components.h"
#include "State/State.h"
#include "State/StateManager.h"

class StateManagerTask : public Task {
    protected:
        StateManager* stateManager;
    public:
        StateManagerTask(StateManager* StateManager);
        void init(int period);
        void tick();
};


#endif