#include "States.h"

String getStateNameString(StateName stateName){
    switch(stateName){
        case StateName::Automatic:
            return "Automatic";
        case StateName::Manual:
            return "Manual";
        default:
            return "Error";
    }
}