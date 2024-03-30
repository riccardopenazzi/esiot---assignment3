#include <Arduino.h>
#include <Scheduler.h>
#include "Components/Components.h"
#include "State/StateManager.h"
#include "MsgService.h"
#include "Tasks/StateManagerTask.h"
#include "EnableinterruptLib.h"

Scheduler* sched;
Components* components;
StateManager* stateManager;

void setup() {
  MsgService.init();
  components = new Components();
  sched = new Scheduler();
  sched->init(100); //GCD of all tasks

  stateManager = new StateManager(components,sched);

  Task* stateManagerTask = new StateManagerTask(stateManager);
  stateManagerTask->init(100);
  sched->addTask(stateManagerTask);
}

void loop() {
  sched->schedule();
}
