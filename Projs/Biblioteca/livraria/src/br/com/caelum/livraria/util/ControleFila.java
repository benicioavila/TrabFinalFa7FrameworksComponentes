package br.com.caelum.livraria.util;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.plaf.synth.SynthSeparatorUI;

import br.com.caelum.livraria.modelo.Livro;

public class ControleFila {
	@SuppressWarnings("unused")
	public static void inserirNaFila(Livro livro) throws Exception {

		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.pedido", "in-queue");

		InitialContext context = new InitialContext(properties);

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination fila = (Destination) context.lookup("pedido");

		MessageProducer producer = session.createProducer(fila);
		javax.jms.Message message = session.createTextMessage(livro.getTitulo() + ":" + livro.getQuantidade());
		producer.send(message);

		session.close();
		connection.close();
		context.close();

	}

	@SuppressWarnings("unused")
	public static Livro buscarNaFila() throws JMSException {
		Livro l = new Livro();
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");

		properties.setProperty("java.naming.provider.url", "tcp://localhost:61616");
		properties.setProperty("queue.retorno", "out-queue");

		InitialContext context;
		try {
			context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Connection connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination fila = (Destination) context.lookup("retorno");
			MessageConsumer consumer = session.createConsumer(fila);
			Message message = consumer.receiveNoWait();
			TextMessage textMessage = (TextMessage) message;
			
			try{
				
				String a[] = textMessage.getText().split(":");
				
				l.setTitulo(a[0]);
				l.setQuantidade(Integer.parseInt(a[1]));
			}catch (Exception e) {
				e.printStackTrace();
			}
		

			session.close();
			connection.close();
			context.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}
	public static void main(String[] args){
		try {
			Livro l = ControleFila.buscarNaFila();
			System.out.println(l.getTitulo());
			System.out.println(l.getQuantidade());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
