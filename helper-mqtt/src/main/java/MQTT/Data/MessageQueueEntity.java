package MQTT.Data;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author yanxin.huang
 */
public class MessageQueueEntity implements Externalizable {
	private int qos = 1;
	private String identify = "undefined";
	private String userName;
	private String password;
	private String host;

	private ArrayList<String> topic = new ArrayList<String>();
	private String clientId = "undefined";

	public MessageQueueEntity(String identify, String clientId, ArrayList<String> topic) {
		this.identify = identify;
		this.clientId = clientId;
		addTopic(topic);
	}

	public MessageQueueEntity() {
	}

	public int getQos() {
		return qos;
	}

	public String getClientId() {
		return clientId;
	}

	public String getHost() {
		return host;
	}


	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	private String getIdentify() {
		return this.identify;
	}

	private ArrayList<String> getTopic() {
		return topic;
	}

	public void setTopic(ArrayList<String> topic) {
		this.topic = topic;
	}

	private void addTopic(ArrayList<String> topic) {
		this.topic.addAll(topic);
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}

	public Boolean equal(MessageQueueEntity entity) {
		return stringToMD5(this.getIdentify()).equals(entity.getIdentify());
	}

	public MessageQueueEntity copy(MessageQueueEntity entityInitial, MessageQueueEntity entityNew) {
		entityNew.addTopic(entityInitial.getTopic());
		entityNew.setUserName(entityInitial.getUserName());
		entityNew.setPassword(entityInitial.getPassword());
		entityNew.setHost(entityInitial.getHost());
		return entityNew;
	}

	@Override
	public String toString() {
		return "MessageQueueEntity{" +
				"topic='" + topic + '\'' +
				", qos=" + qos +
				", host='" + host + '\'' +
				", clientId='" + clientId + '\'' +
				", username='" + userName + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(stringToMD5(identify));
		out.writeObject(topic);
		out.writeObject(userName);
		out.writeObject(password);
		out.writeObject(host);
		out.writeObject(clientId);
		out.writeInt(qos);
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		setIdentify(in.readObject().toString());
		ArrayList topic = (ArrayList) in.readObject();
		setTopic(topic);
		setUserName(in.readObject().toString());
		setPassword(in.readObject().toString());
		setHost(in.readObject().toString());
		setClientId(in.readObject().toString());
		setQos(in.readInt());
	}

	private String stringToMD5(String plainText) {
		byte[] secretBytes;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有这个md5算法！");
		}
		StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code.insert(0, "0");
		}
		return md5code.toString();
	}
}
