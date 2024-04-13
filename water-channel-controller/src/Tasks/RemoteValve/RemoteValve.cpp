#include "RemoteValve.h"
#include "Config.h"
#include "Arduino.h"
#include "MsgService.h"

RemoteValve::RemoteValve(Components* components, ValveAngles* valveAngles){
  this->components = components;
  this->valveAngles = valveAngles;
}
  
void RemoteValve::init(int period){
  Task::init(period);
}
  
void RemoteValve::tick(){
  if(MsgService.isMsgAvailable()){
        Msg* msg = MsgService.receiveMsg();
        String msgContent = msg->getContent();
        int percentage = msgContent.toInt(); 
        int angle = map(percentage, 0, 100, 0, 180);
        valveAngles->remote = angle;
        this->components->getValve()->on();
        this->components->getValve()->setPosition(angle);
        delete msg;
  }
}
