#ifndef __ENABLE_INTERRUPT_LIB_H__
#define __ENABLE_INTERRUPT_LIB_H__

extern void enableInterruptLib(int pin, void (*function)(), int mode);
extern void disableInterruptLib(int pin);


#endif // __ENABLE_INTERRUPT_LIB_H__