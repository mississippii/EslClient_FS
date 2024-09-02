package freeswitch.inbound;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.springframework.stereotype.Component;
/**
 * @author T@nvir
 */

@Component
public class EslClient {
    public static Client client;
    final IEslEventListener listener;


    public EslClient(IEslEventListener listener) {
        this.listener = listener;
    }
    public void connect(String eslIp, int port,String password) {
        client = new Client();
        try {
            client.connect(eslIp, port, password, 10);
            client.addEventListener(listener);
            client.setEventSubscriptions("plain", "all");
        } catch (InboundConnectionFailure inboundConnectionFailure) {
            System.out.println("Not connected");
            inboundConnectionFailure.printStackTrace();
        }
    }
}
