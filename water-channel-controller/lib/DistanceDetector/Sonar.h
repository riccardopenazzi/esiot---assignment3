#ifndef __SONAR__
#define __SONAR__

#include "DistanceDetector.h"

class Sonar: public DistanceDetector {
    protected:
        int trigPin;
        int echoPin;
        float temperature_celsius;
    public:
        Sonar(int trigPin, int echoPin);
        void setTemperature(float temperature_celsius);
        float getDistance();
};

#endif
