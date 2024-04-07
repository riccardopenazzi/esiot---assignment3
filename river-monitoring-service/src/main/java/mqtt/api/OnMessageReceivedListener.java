package mqtt.api;

public interface OnMessageReceivedListener {
    void onMessageReceived(String topic, String message);
}
