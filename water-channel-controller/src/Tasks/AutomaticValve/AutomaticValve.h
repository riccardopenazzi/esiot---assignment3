#ifndef __TASK_AUTOMATIC_VALVE__
#define __TASK_AUTOMATIC_VALVE__

#include "Task.h"
#include "servo_motor_impl.h"
#include "Components/Components.h"

class AutomaticValve: public Task {
  private:
    Components* components;
    int potentiometer;
  public:
    AutomaticValve(Components* components);  
    void init(int period);  
    void tick();
};

#endif

