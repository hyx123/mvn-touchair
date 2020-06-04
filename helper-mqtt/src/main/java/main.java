import Data.MessageQueueEntity;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;

/**
 * @author yanxin.huang
 */
public class main {
//	public static void main(String[] args) {
//		MessageQueueEntity entity = new MessageQueueEntity("p@ssw0rd", "uppc.touchair.cn", new ArrayList<String>());
//		MQTTServiceRepository resp = new MQTTServiceRepository(entity);
//
//		resp.setCallBack(new MqttCallback() {
//			public void connectionLost(Throwable throwable) {
//
//			}
//
//			public void messageArrived(String topic, MqttMessage mqttMessage) {
//				System.out.println("topic:" + topic);
//				System.out.println("getId:" + mqttMessage.getId());
//				System.out.println("Qos:" + mqttMessage.getQos());
//				System.out.println("message content:" + new String(mqttMessage.getPayload()));
//			}
//
//			public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//
//			}
//		});
//		resp.subscribe("1314");
//		resp.publish("1314", "heheda");
////		resp.onDestroy();
///*		try {
//			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("F:\\entity.txt"));
//			MessageQueueEntity entity = new MessageQueueEntity("p@ssw0rd", "TestOne", new ArrayList<String>());
//			entity.setQos(1);
//			entity.setIdentify("p@ssw0rd");
//			entity.setClientId("undefined");
//			entity.setHost("tcp://192.168.1.69:1883");
//			ArrayList<String> topic = new ArrayList<String>();
//			topic.add("Test");
//			entity.setTopic(topic);
//			entity.setUserName("user1");
//			entity.setPassword("123456");
//			oos.writeObject(entity);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}*/
//	}
}
