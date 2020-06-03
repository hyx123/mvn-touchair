import Data.MessageQueueEntity;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;

/**
 * @author yanxin.huang
 */
class MQTTServiceRepository {
	private MqttClient client;
	private MessageQueueEntity entity;

	MQTTServiceRepository(MessageQueueEntity entity) {
		initEnity();
		if (!entity.equal(this.entity)) {
			throw new Error("MessageQueueEntity Identify Inconsistency");
		}
		this.entity = entity.copy(this.entity, entity);
		createService();
	}

	private void initEnity() {
		ObjectInputStream ois;
		try {
			URL url = getClass().getClassLoader().getResource("entity.txt");
			ois = new ObjectInputStream(new FileInputStream(url.getFile()));
			this.entity = (MessageQueueEntity) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void createService() {
		if (client != null && client.isConnected()) {
			return;
		}
		try {
			client = new MqttClient(entity.getHost(), entity.getClientId(), new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			//设置是否情况连接缓存, false保留服务器记录
			options.setCleanSession(false);
			options.setUserName(entity.getUserName());
			options.setPassword(entity.getPassword().toCharArray());
			options.setConnectionTimeout(5);
			options.setKeepAliveInterval(20);
			client.connect(options);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	void subscribe(String topic) {
		createService();
		try {
			client.subscribe(topic, entity.getQos());
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	void publish(String topic, String content) {
		createService();
		MqttMessage message = new MqttMessage(content.getBytes());
		message.setQos(entity.getQos());
		try {
			client.publish(topic, message);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	void setCallBack(MqttCallback callBack) {
		createService();
		client.setCallback(callBack);
	}


	public void onDestroy() {
		try {
			if (client != null) {
				if (client.isConnected()) {
					client.disconnect();
				}
				client.close();
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
