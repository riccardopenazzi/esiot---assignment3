package model.api;

public interface SystemState {

    enum State {

        NORMAL("NORMAL"),

        ALARM_TOO_LOW("ALARM-TOO-LOW"),

        PRE_ALARM_TOO_HIGH("PRE-ALARM-TOO-HIOGH"),

        ALARM_TOO_HIGH("ALARM-TOO-HIGH"),

        ALARM_TOO_HIGH_CRITIC("ALARM-TOO-HIGH-CRITIC");

        private final String state;

        State(final String state) {
            this.state = state;
        }

        public String getState() {
            return this.state;
        }
    }

    State getSystemState();
}
