#include <Config.h>
#include "StateManager.h"
#include "State/State.h"
#include "State/StateManual/StateManual.h"
#include "State/StateAutomatic/StateAutomatic.h"

StateManager::StateManager(Components* components, Scheduler* scheduler){
    this->components = components;
    this->scheduler = scheduler;
    this->state = new StateAutomatic(VALVE_CLOSE, this->components, this->scheduler);
}

void StateManager::switchState(){
    if(this->state->goNext()){
        StateName currentState = this->state->name();
        State* nextState;
        if(currentState == StateName::Manual){
            nextState = stateFactory(StateName::Automatic);
        } else {
            nextState = stateFactory(StateName::Manual);
        }
        delete this->state;
        this->state = nextState;
    }
}

State* StateManager::stateFactory(StateName stateName){
    if(stateName==StateName::Manual){
        return new StateManual(this->state->valveAngle, this->components, this->scheduler);
    }
    else if(stateName==StateName::Automatic){
        return new StateAutomatic(this->state->valveAngle, this->components, this->scheduler);
    }
}

StateName StateManager::getCurrentState(){
    return this->state->name();
}