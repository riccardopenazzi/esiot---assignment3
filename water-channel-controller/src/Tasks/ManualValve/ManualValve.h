#ifndef __TASK_MANUAL_VALVE__
#define __TASK_MANUAL_VALVE__

#include "Task.h"
#include "servo_motor_impl.h"
#include "Components/Components.h"
#include <ValveAngles.h>

class ManualValve: public Task {
  private:
    Components* components;
    ValveAngles* valveAngles;
    int potentiometer;
  public:
    ManualValve(Components* components, ValveAngles* valveAngles);  
    void init(int period);  
    void tick();
};

#endif

