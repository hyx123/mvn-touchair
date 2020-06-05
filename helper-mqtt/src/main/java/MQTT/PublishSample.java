package MQTT;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author yanxin.huang
 */
public class PublishSample {
	private int qos = 1;
	private String broker = "tcp://192.168.1.69:1883";
	private String userName = "user1";
	private String password = "123456";
	private String clientId = "uppc.touchair.cn";
	private MemoryPersistence persistence = new MemoryPersistence();
	private MqttClient client;
	private MqttConnectOptions connOpts;

	PublishSample() {
		connect();
	}

	private void createService() {
		try {
			client = new MqttClient(broker, clientId, persistence);
			connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);

			connOpts.setUserName(userName);
			connOpts.setPassword(password.toCharArray());
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	private void connect() {
		if(client == null) {
			createService();
		}
		try {
			client.connect(connOpts);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	void pubMsg(String topic, String content) {
		if(client == null) {
			connect();
		}
		MqttMessage message = new MqttMessage(content.getBytes());
		message.setQos(qos);

		try {
			client.publish(topic, message);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	void disconnect() {
		if(client == null)
			return;
		try {
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	void distory() {
		if(client == null)
			return;
		try {
			client.close();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String topic = "hyx2020";
		String content = "Hello Mqtt";
		int qos = 1;
		String broker = "tcp://192.168.1.69:1883";
		String userName = "user1";
		String password = "123456";
		String clientId = "uppc.touchair.cn";
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(false);

			connOpts.setUserName(userName);
			connOpts.setPassword(password.toCharArray());

			sampleClient.connect(connOpts);

			MqttMessage message = new MqttMessage(content.getBytes());

			message.setQos(qos);

			sampleClient.publish(topic, message);

			sampleClient.disconnect();

			sampleClient.close();
		} catch (MqttException e) {
			System.out.println("reason " + e.getReasonCode());
			System.out.println("msg " + e.getMessage());
			System.out.println("loc " + e.getLocalizedMessage());
			System.out.println("cause " + e.getCause());
			System.out.println("excep " + e);
			e.printStackTrace();
		}
	}
}
