#include "ManualValve.h"
#include "Config.h"
#include "Arduino.h"
#include "MsgService.h"

ManualValve::ManualValve(Components* components, int initialAngle){
  this->components = components;    
  this->potentiometer = 0;
  this->lastAngle = initialAngle;
}
  
void ManualValve::init(int period){
  Task::init(period);
}
  
void ManualValve::tick(){
  this->potentiometer= analogRead(PIN_POTENTIOMETER);
  int angle = map(this->potentiometer, 0, 1023, 0, 180);
  if(angle != this->lastAngle){
    this->lastAngle = angle;
    this->components->getValve()->on();
    this->components->getValve()->setPosition(angle);
    String msg = '{\"mode\":\"Manual\",\"valve\":\"'+String(angle)+'\"}'; 
    MsgService.sendMsg(msg);
  }
}
