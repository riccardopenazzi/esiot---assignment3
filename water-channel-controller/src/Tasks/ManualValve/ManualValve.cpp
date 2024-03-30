#include "ManualValve.h"
#include "Config.h"
#include "Arduino.h"

ManualValve::ManualValve(Components* components){
  this->components = components;    
  this->potentiometer = 0;
}
  
void ManualValve::init(int period){
  Task::init(period);
  Serial.println("ManualValve init");
}
  
void ManualValve::tick(){
  this->potentiometer= analogRead(PIN_POTENTIOMETER);
  int angle = map(this->potentiometer, 0, 1023, 0, 180);
  this->components->getValve()->on();
  this->components->getValve()->setPosition(angle);
}
