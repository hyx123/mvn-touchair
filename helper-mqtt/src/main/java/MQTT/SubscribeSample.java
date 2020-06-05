package MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author yanxin.huang
 */
public class SubscribeSample {
	private String topic = "hyx2020";
	private int qos = 1;
	private String HOST = "tcp://localhost:1883";
	private String userName = "user1";
	private String password = "123456";
	private String clientId = "uppc.touchair.cn";
	private MqttClient client;
	private MqttConnectOptions options;

	public SubscribeSample() {
		createService();
	}

	public void setListener(MqttCallback callback) {
		if(client == null) {
			createService();
		}
		client.setCallback(callback);
	}

	private void createService() {
		try {
			client = new MqttClient(HOST, clientId, new MemoryPersistence());
			options = new MqttConnectOptions();
			//设置是否情况连接缓存, false保留服务器记录
			options.setCleanSession(false);
			options.setUserName(userName);
			options.setPassword(password.toCharArray());
			options.setConnectionTimeout(2);
			options.setKeepAliveInterval(20);
			client.connect(options);
			client.subscribe(topic, qos);
			client.connect(options);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final String topic = "hyx2020";
		int qos = 1;
		String HOST = "tcp://localhost:1883";
		String userName = "user1";
		String password = "123456";
		String clientId = "uppc.touchair.cn";
		MqttClient client = null;
		try {
			client = new MqttClient(HOST, clientId, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			//设置是否情况连接缓存, false保留服务器记录
			options.setCleanSession(false);
			options.setUserName(userName);
			options.setPassword(password.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			client.setCallback(new MqttCallback() {
				public void connectionLost(Throwable throwable) {
					System.out.println("connectionLost");
				}

				public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
					System.out.println("topic:" + s);
					System.out.println("Qos:" + mqttMessage.getQos());
					System.out.println("message content:" + new String(mqttMessage.getPayload()));
				}

				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
					System.out.println("deliveryComplete---------" + iMqttDeliveryToken.isComplete());
				}
			});
			client.connect(options);
			client.subscribe(topic, qos);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
