package mqtt.impl;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqtt.api.OnMessageReceivedListener;

public class MqttManager {
    private MqttClient client;
    private final String broker;
    private final String clientId = "JavaSubscriberPublisher";
    private final String subscriptionTopic = "sensor/sonar/water_level";
    private final String publishingTopic = "sensor/sonar/frequency";
    private OnMessageReceivedListener messageReceivedListener;

    public MqttManager(final String brk) {
        this.broker = brk;
        try {
            client = new MqttClient(this.broker, this.clientId);

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connettendo al broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connesso");

            client.setCallback(new MqttCallback() {
                public void connectionLost(Throwable cause) {
                    System.out.println("Connessione persa al broker MQTT: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (messageReceivedListener != null) {
                        messageReceivedListener.onMessageReceived(topic, new String(message.getPayload()));
                    }
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            System.out.println("Iscrizione al topic: " + subscriptionTopic);
            client.subscribe(subscriptionTopic);

        } catch (MqttException me) {
            System.out.println("Eccezione durante la connessione al broker: " + me.getMessage());
        }
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener) {
        this.messageReceivedListener = listener;
    }

    public void sendMessage(String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);
            System.out.println("Invio del messaggio sul topic: " + publishingTopic + " " + mqttMessage);
            client.publish(publishingTopic, mqttMessage);
        } catch (MqttException e) {
            System.out.println("Errore durante l'invio del messaggio: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            client.disconnect();
            System.out.println("Disconnesso dal broker");
        } catch (MqttException me) {
            System.out.println("Eccezione durante la disconnessione dal broker: " + me.getMessage());
        }
    }

}
