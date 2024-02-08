#include <Arduino.h>
#include "Sonar.h"

Sonar::Sonar(int trigPin, int echoPin) {
    this->temperature_celsius = 20;
    this->trigPin = trigPin;
    this->echoPin = echoPin;
    pinMode(trigPin,OUTPUT);
    pinMode(echoPin,INPUT);
};

void Sonar::setTemperature(float temperature_celsius) {
    this->temperature_celsius = temperature_celsius;
};

float Sonar::getDistance(){
    float vs = 331.45 + 0.62*temperature_celsius;

    /* Triggering stage: sending the impulse */

    digitalWrite(trigPin,LOW);
    delayMicroseconds(3);
    digitalWrite(trigPin,HIGH);
    delayMicroseconds(5);
    digitalWrite(trigPin,LOW);
    
    /* Receiving the echo */

    float tUS = pulseIn(echoPin, HIGH);
    float t = tUS / 1000.0 / 1000.0 / 2;
    float d = t*vs;
    return d;
}
