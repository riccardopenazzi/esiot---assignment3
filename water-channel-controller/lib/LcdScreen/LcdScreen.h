#ifndef __SCREEN__
#define __SCREEN__

#include <LiquidCrystal_I2C.h> 

class LcdScreen {
    private:
        /* Wiring: SDA => A4, SCL => A5 */
        /* I2C address of the LCD: 0x27 */
        /* Number of columns: 20 rows: 4 */
        LiquidCrystal_I2C lcd = LiquidCrystal_I2C(0x27, 20, 4);
    public:
        LcdScreen();
        void setCursor(int col, int row);
        void print(const char* text);
        void clear();
};

#endif
