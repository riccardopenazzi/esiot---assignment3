#include <Arduino.h>
#include "StateManagerTask.h"

StateManagerTask::StateManagerTask(StateManager* stateManager) {
    this->stateManager = stateManager; 
}

void StateManagerTask::init(int period) {
    Task::init(period);
}

void StateManagerTask::tick() {
    //Serial.println(this->stateManager->getCurrentState());  
    this->stateManager->switchState();
}
