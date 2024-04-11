#include <Arduino.h>
#include <Wire.h>
#include <WiFi.h>
#include <PubSubClient.h>

#define ECHO_PIN 33
#define TRIG_PIN 32
#define GREEN_LED 18
#define RED_LED 19

#define mqtt_server "test.mosquitto.org"
#define water_level_topic "sensor/sonar/water_level"
#define frequency_topic "sensor/sonar/frequency"

long duration, distance;
int frequency = 1000;

const char *ssid = "**";
const char *psw = "**";

WiFiClient espClient;
PubSubClient client(espClient);

void sonar_monitor();
void setup_wifi();
void check_network();
void reconnect();
void set_frequency(const char *freq);
void callback(char *topic, byte *payload, unsigned int length);

void setup()
{
	Serial.begin(9600);
	pinMode(TRIG_PIN, OUTPUT);
	pinMode(ECHO_PIN, INPUT);
	pinMode(GREEN_LED, OUTPUT);
	pinMode(RED_LED, OUTPUT);
	digitalWrite(RED_LED, HIGH);
	setup_wifi();
	client.setServer(mqtt_server, 1883); // default port
	client.setCallback(callback);
}

void loop()
{
	if (!client.connected())
	{
		reconnect();
	}
	client.loop();
	check_network();
	sonar_monitor();
	delay(frequency);
}

void sonar_monitor()
{
	digitalWrite(TRIG_PIN, LOW);
	delayMicroseconds(2);
	digitalWrite(TRIG_PIN, HIGH);
	delayMicroseconds(10);
	digitalWrite(TRIG_PIN, LOW);

	duration = pulseIn(ECHO_PIN, HIGH);
	distance = duration / 58.2;
	Serial.println("Distance: " + String(distance));
	client.publish(water_level_topic, String(distance).c_str(), false);
}

void setup_wifi()
{
	delay(10);
	Serial.println(String("Connecting to ") + ssid);
	// WiFi.useStaticBuffers(true);
	WiFi.mode(WIFI_STA);
	WiFi.begin(ssid, psw);
	while (WiFi.status() != WL_CONNECTED)
	{
	}
	Serial.println("WiFi connected");
	digitalWrite(RED_LED, LOW);
	digitalWrite(GREEN_LED, HIGH);
}

void check_network()
{
	if (WiFi.status() == WL_CONNECTED)
	{
		digitalWrite(RED_LED, LOW);
		digitalWrite(GREEN_LED, HIGH);
	}
	else
	{
		digitalWrite(GREEN_LED, LOW);
		digitalWrite(RED_LED, HIGH);
	}
}

void reconnect()
{
	// Loop until we're reconnected
	while (!client.connected())
	{
		Serial.print("Attempting MQTT connection...");

		// Create a random client ID
		String clientId = String("esiot-assignment-3") + String(random(0xffff), HEX);

		// Attempt to connect
		if (client.connect(clientId.c_str()))
		{
			Serial.println("connected");
			client.subscribe(water_level_topic);
			client.subscribe(frequency_topic);
		}
		else
		{
			Serial.print("failed, rc=");
			Serial.print(client.state());
			Serial.println(" try again in 5 seconds");
			delay(5000);
		}
	}
}

void set_frequency(const char *freq)
{
	if (strcmp(freq, "0.08") == 0)
	{
		frequency = 12000;
	}
	else if (strcmp(freq, "0.12") == 0)
	{
		frequency = 8000;
	}
}

void callback(char *topic, byte *payload, unsigned int length)
{
	payload[length] = '\0';
	String message = String((char *)payload);

	if (strcmp(topic, frequency_topic) == 0)
	{
		Serial.println("Frequency received: " + message);
		set_frequency(message.c_str());
	}
}