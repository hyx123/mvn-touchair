package helper.test1;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author yanxin.huang
 */
public class SubscribeSample {
	private static String topic = "hyx2020";
	private static int qos = 1;
	private static String HOST= "tcp://192.168.1.69:1883";
	private static String userName = "user1";
	private static String password = "123456";
	private static String clientId = "pubClient";

	public static void main(String[] args) {
		try {
			MqttClient client = new MqttClient(HOST, clientId, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			//设置是否情况连接缓存, false保留服务器记录
			options.setCleanSession(false);
			options.setUserName(userName);
			options.setPassword(password.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			client.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable throwable) {
					System.out.println("connectionLost");
				}

				@Override
				public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
					System.out.println("topic:"+topic);
					System.out.println("Qos:"+mqttMessage.getQos());
					System.out.println("message content:"+new String(mqttMessage.getPayload()));
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
					System.out.println("deliveryComplete---------"+ iMqttDeliveryToken.isComplete());
				}
			});
			client.connect(options);
			client.subscribe(topic, qos);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
