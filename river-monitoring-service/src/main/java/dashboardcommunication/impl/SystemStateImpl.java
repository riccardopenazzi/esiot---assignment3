package dashboardcommunication.impl;

import dashboardcommunication.api.SystemState;

public class SystemStateImpl implements SystemState {

    private final State state;

    public SystemStateImpl(final State state) {
        this.state = state;
    }

    @Override
    public State getSystemState() {
        return this.state;
    }

    @Override
    public String toString() {
        return this.state.toString();
    }
}
