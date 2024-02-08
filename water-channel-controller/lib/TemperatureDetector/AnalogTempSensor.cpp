#include <Arduino.h>
#include "AnalogTempSensor.h"

/* Lm 36 */
AnalogTempSensor::AnalogTempSensor(int pin) {
    this->pin = pin;
    pinMode(pin,INPUT);
}

double AnalogTempSensor::getTemperature() {
    int value = analogRead(this->pin);

    /* value : 1023 = value_in_mV : 5000 */
    /* value_in_mV = 5000/1023 * value */
    /* value_in_C = value_in_mV / 10 (since we have 10 mV => 1 °C ) */

    return  ((value * 0.00488) - 0.5) / 0.01;;
};
