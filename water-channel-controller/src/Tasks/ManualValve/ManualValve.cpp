#include "ManualValve.h"
#include "Config.h"
#include "Arduino.h"
#include "MsgService.h"

ManualValve::ManualValve(Components* components, ValveAngles* valveAngles){
  this->components = components;    
  this->potentiometer = 0;
  this->valveAngles = valveAngles;
}
  
void ManualValve::init(int period){
  Task::init(period);
}
  
void ManualValve::tick(){
  this->potentiometer= analogRead(PIN_POTENTIOMETER);
  int angle = map(this->potentiometer, 0, 1023, 0, 180);
  if(angle != this->valveAngles->potentiometer && angle != this->valveAngles->remote){
    this->valveAngles->potentiometer = angle;
    this->components->getValve()->on();
    this->components->getValve()->setPosition(angle);
    int openingPercentage = map(angle, 0, 180, 0, 100); 
    MsgService.sendMsg("{\"mode\":\"Manual\",\"valve\":\""+String(openingPercentage)+"\"}");
  }
}
