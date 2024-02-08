#ifndef _COMPONENTS_
#define _COMPONENTS_

#include <ButtonImpl.h>
#include <Sonar.h>
#include <LcdScreen.h>
#include <Led.h>
#include <Pir.h>
#include <servo_motor_impl.h>
#include <AnalogTempSensor.h>

class Components {
    private:
        ButtonImpl* modeButton;
        ServoMotorImpl* valve;
        LcdScreen* lcd;
    public:
        Components();
        ButtonImpl* getModeButton();
        ServoMotorImpl* getValve();
        LcdScreen* getLcd();
};

#endif