#ifndef __STATES__
#define __STATES__

#include "Arduino.h"

enum StateName { Remote, Manual };

String getStateNameString(StateName stateName);

#endif
