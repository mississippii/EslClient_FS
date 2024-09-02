package freeswitch;

import freeswitch.inbound.EslClient;
import freeswitch.outbound.SamplePipelineFactory;
import org.freeswitch.esl.client.outbound.SocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/**
 * @author T@nvir
 */

@SpringBootApplication
public class EslSpringBootApplication {
	private static final String FREESWITCH_IP="192.168.0.144";
	private static final int PORT=8021;
	private static final String PASSWORD="";

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(EslSpringBootApplication.class, args);
		//<--------------------Inbound----------------------->
		EslClient client = context.getBean(EslClient.class);
		client.connect(FREESWITCH_IP,PORT,PASSWORD);
		//<--------------------OutBound----------------------->
		new Thread(() -> {
			SocketClient socketClient = new SocketClient(8086, context.getBean(SamplePipelineFactory.class));
			socketClient.start();
		}).start();
	}

}
