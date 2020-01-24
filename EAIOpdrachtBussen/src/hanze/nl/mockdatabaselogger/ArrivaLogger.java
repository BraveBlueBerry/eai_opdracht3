package hanze.nl.mockdatabaselogger;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import com.thoughtworks.xstream.XStream;

public class ArrivaLogger {
	private static String queueName = "ARRIVALOGGER";
	private static int aantalBerichten=0;
	private static int aantalETAs=0;

	public static void main (String[] args){
    try {
	        Connection connection = getConnection();
			Session session = getSession(connection);
			Destination destination = getDestination(session);
	        MessageConsumer consumer = getMessageConsumer(session, destination);
	        receiveMessagesAndConsume(consumer);
	        closeAll(consumer, session, connection);
	        System.out.println(aantalBerichten + " berichten met " + aantalETAs + " ETAs verwerkt.");
    	} catch (Exception e) {
    		System.out.println("Caught: " + e);
    		e.printStackTrace();
    	}
	}

	private static Connection getConnection() throws JMSException {
		ActiveMQConnectionFactory connectionFactory =
				new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		return connection;
	}

	private static Session getSession(Connection connection) throws JMSException {
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	private static Destination getDestination(Session session) throws JMSException {
		return session.createQueue(queueName);
	}

	private static MessageConsumer getMessageConsumer(Session session, Destination destination) throws JMSException {
		return session.createConsumer(destination);
	}

	private static void closeAll(MessageConsumer consumer, Session session, Connection connection) throws JMSException {
		consumer.close();
		session.close();
		connection.close();
	}

	private static void receiveMessagesAndConsume(MessageConsumer consumer) throws JMSException {
		boolean newMessage=true;
		while (newMessage) {
			Message message = consumer.receive(2000);
			newMessage=false;
			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				newMessage=true;
				XStream xstream = new XStream();
				xstream.alias("Bericht", Bericht.class);
				xstream.alias("ETA", ETA.class);
				Bericht bericht=(Bericht)xstream.fromXML(text);
				aantalBerichten++;
				aantalETAs+=bericht.ETAs.size();
			} else {
				System.out.println("Received: " + message);
			}
		}
	}
}
