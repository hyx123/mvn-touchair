import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author yanxin.huang
 */
public class PublishSample {
	private static String topic = "hyx2020";
	private static String content = "Hello Mqtt";
	private static int qos = 1;
	private static String broker = "tcp://192.168.1.69:1883";
	private static String userName = "user1";
	private static String password = "123456";
	private static String clientId = "pubClient";
	private static MemoryPersistence persistence = new MemoryPersistence();

	public static void main(String[] args) {
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
