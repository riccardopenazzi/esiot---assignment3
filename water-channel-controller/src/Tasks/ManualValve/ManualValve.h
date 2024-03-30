#ifndef __TASK_BLINK_LED__
#define __TASK_BLINK_LED__

#include "Task.h"
#include "servo_motor_impl.h"
#include "Components/Components.h"

class ManualValve: public Task {
  private:
    Components* components;
    int potentiometer;
  public:
    ManualValve(Components* components);  
    void init(int period);  
    void tick();
};

#endif

