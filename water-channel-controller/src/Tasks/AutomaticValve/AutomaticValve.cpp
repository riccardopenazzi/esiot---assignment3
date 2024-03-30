#include "AutomaticValve.h"
#include "Config.h"
#include "Arduino.h"
#include "MsgService.h"

AutomaticValve::AutomaticValve(Components* components){
  this->components = components;    
  this->potentiometer = 0;
}
  
void AutomaticValve::init(int period){
  Task::init(period);
}
  
void AutomaticValve::tick(){
  if(MsgService.isMsgAvailable()){
        Msg* msg = MsgService.receiveMsg();
        String msgContent = msg->getContent();
        int percentage = msgContent.toInt(); 
        int angle = map(percentage, 0, 100, 0, 180);
        this->components->getValve()->on();
        this->components->getValve()->setPosition(angle);
        delete msg;
  }
}
