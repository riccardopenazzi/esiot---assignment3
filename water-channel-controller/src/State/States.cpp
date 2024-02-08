#include "States.h"

String getStateNameString(StateName stateName){
    switch(stateName){
        case StateName::Remote:
            return "Remote";
        case StateName::Manual:
            return "Manual";
        default:
            return "Error";
    }
}