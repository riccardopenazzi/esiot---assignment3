#ifndef __TASK_MANUAL_VALVE__
#define __TASK_MANUAL_VALVE__

class ValveAngles {
    public:
        ValveAngles(int potentiometer, int remote);
        int potentiometer;
        int remote;
};

#endif