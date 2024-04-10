package model.api;

import model.api.SystemState.State;

public interface Logic {

    void updateEnvironment(double waterLevel);

    double getFrequency();

    int getValveLevel();

    State getState();

    void setValveLevel(int value);
}
