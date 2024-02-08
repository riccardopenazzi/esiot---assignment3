#ifndef __PIR__
#define __PIR__

#include "MovementDetector.h"

class Pir: public MovementDetector {        
    protected:
        unsigned int CALIBRATION_TIME_MILLI_SEC = 10000;
        unsigned long initialCalibrationTime;
        int pin;
    public:
        Pir(int pin);
        bool isCalibrationFinished();
        bool isMotionDetected();
};

#endif