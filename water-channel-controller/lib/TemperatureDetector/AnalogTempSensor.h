#ifndef __AnalogTempSensor__
#define __AnalogTempSensor__

#include "TemperatureDetector.h"

class AnalogTempSensor: public TemperatureDetector {
    protected:
        int pin;
    public:
        AnalogTempSensor(int pin);
        double getTemperature();
};

#endif
