package model.impl;

import model.api.Logic;
import model.api.SystemState.State;

public class LogicImpl implements Logic {

    private static final double WL1 = 2;
    private static final double WL2 = 4;
    private static final double WL3 = 6;
    private static final double WL4 = 8;
    private static final double F1 = 20;
    private static final double F2 = 40;
    private static final int VL1 = 0;
    private static final int VL2 = 25;
    private static final int VL3 = 50;
    private static final int VL4 = 100;

    private final double waterLevel;
    private double frequency;
    private int valveLevel;
    private State state;

    public LogicImpl(final double wl) {
        this.waterLevel = wl;
        updateEnvironment(this.waterLevel);
    }

    @Override
    public void updateEnvironment(final double wl) {
        /* water level < WL1 */
        if (wl < WL1) {
            this.state = State.ALARM_TOO_LOW;
            this.frequency = F1;
            this.valveLevel = VL1;
        /* WL1 <= water level <= WL2 */
        } else if (wl <= WL2) {
            this.state = State.NORMAL;
            this.valveLevel = VL2;
            this.frequency = F1;
        /* WL2 < water level <= WL3 */
        } else if (wl <= WL3) {
            this.state = State.PRE_ALARM_TOO_HIGH;
            this.frequency = F2;
            this.valveLevel = VL2;
        /* WL3 < water level <= WL4 */
        } else if (wl <= WL4) {
            this.state = State.ALARM_TOO_HIGH;
            this.frequency = F2;
            this.valveLevel = VL3;
        /* water level > WL4 */
        } else {
            this.state = State.ALARM_TOO_HIGH_CRITIC;
            this.frequency = F2;
            this.valveLevel = VL4;
        }

        System.out.println(
            "WATER: " + wl + "\n" +
            "STATE: " + this.state + "\n" +
            "FREQ: " + this.frequency + "\n" +
            "VALVE: " + this.valveLevel + "\n"
        );
    }
}
