#ifndef __TASK_REMOTE_VALVE__
#define __TASK_REMOTE_VALVE__

#include "Task.h"
#include "servo_motor_impl.h"
#include "Components/Components.h"
#include <ValveAngles.h>

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

