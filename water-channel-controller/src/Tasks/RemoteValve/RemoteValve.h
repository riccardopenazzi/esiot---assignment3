#ifndef __TASK_AUTOMATIC_VALVE__
#define __TASK_AUTOMATIC_VALVE__

#include "Task.h"
#include "servo_motor_impl.h"
#include "Components/Components.h"
#include "State/StateManual/ValveAngles.h"

class RemoteValve: public Task {
  private:
    Components* components;
    ValveAngles* valveAngles;
  public:
    RemoteValve(Components* components, ValveAngles* valveAngles);  
    void init(int period);  
    void tick();
};

#endif

