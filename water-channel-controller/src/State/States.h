#ifndef __STATES__
#define __STATES__

#include "Arduino.h"

enum StateName { Automatic, Manual };

String getStateNameString(StateName stateName);

#endif
