package model.impl;

import model.api.Logic;
import model.api.SystemState.State;

public class LogicImpl implements Logic {

    private static final double WL1 = 10;
    private static final double WL2 = 20;
    private static final double WL3 = 30;
    private static final double WL4 = 40;
    private static final double F1 = 0.08;
    private static final double F2 = 0.12;
    private static final int VL1 = 0;
    private static final int VL2 = 25;
    private static final int VL3 = 50;
    private static final int VL4 = 100;

    private final double waterLevel;
    private double frequency;
    private int valveLevel;
    private State state;

    public LogicImpl(final double wl, final boolean isManual) {
        this.waterLevel = wl;
        updateEnvironment(this.waterLevel, isManual);
    }

    @Override
    public void updateEnvironment(final double wl, boolean isManual) {
        /* water level < WL1 */
        if (wl < WL1) {
            this.state = State.ALARM_TOO_LOW;
            this.frequency = F1;
            if (!isManual) {
                this.valveLevel = VL1;
            }
        /* WL1 <= water level <= WL2 */
        } else if (wl <= WL2) {
            this.state = State.NORMAL;
            this.frequency = F1;
            if (!isManual) {
                this.valveLevel = VL2;
            }
        /* WL2 < water level <= WL3 */
        } else if (wl <= WL3) {
            this.state = State.PRE_ALARM_TOO_HIGH;
            this.frequency = F2;
            if (!isManual) {
                this.valveLevel = VL2;
            }
        /* WL3 < water level <= WL4 */
        } else if (wl <= WL4) {
            this.state = State.ALARM_TOO_HIGH;
            this.frequency = F2;
            if (!isManual) {
                this.valveLevel = VL3;
            }
        /* water level > WL4 */
        } else {
            this.state = State.ALARM_TOO_HIGH_CRITIC;
            this.frequency = F2;
            if (!isManual) {
                this.valveLevel = VL4;
            }
        }

        System.out.println(
                "WATER: " + wl + "\n" +
                        "STATE: " + this.state + "\n" +
                        "FREQ: " + this.frequency + "\n" +
                        "VALVE: " + this.valveLevel + "\n");
    }

    @Override
    public double getFrequency() {
        return this.frequency;
    }

    @Override
    public int getValveLevel() {
        return valveLevel;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setValveLevel(int value) {
        this.valveLevel = value;
    }

}
