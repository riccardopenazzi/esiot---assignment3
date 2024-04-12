#ifndef __TASK_MANUAL_VALVE__
#define __TASK_MANUAL_VALVE__

#include "Task.h"
#include "servo_motor_impl.h"
#include "Components/Components.h"

class ManualValve: public Task {
  private:
    Components* components;
    int potentiometer;
    int lastAngle;
  public:
    ManualValve(Components* components, int initialAngle = 0);  
    void init(int period);  
    void tick();
};

#endif

