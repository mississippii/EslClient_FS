package freeswitch.inbound;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.springframework.stereotype.Component;
/**
 * @author T@nvir
 */

@Component
public class EslEventListener implements IEslEventListener{

    @Override
    public void eventReceived(EslEvent eslEvent) {
        String eventName = eslEvent.getEventName();
        if (eventName.startsWith("CHANNEL_")) {
            String calleeNumber = eslEvent.getEventHeaders().get("Caller-Callee-ID-Number");
            String callerNumber = eslEvent.getEventHeaders().get("Caller-Caller-ID-Number");
            switch (eventName) {
                case "CHANNEL_PARK":
                    System.out.println("----------call park----------");
                    break;
                case "CHANNEL_BRIDGE":
                    System.out.println("----------Bridge call----------");
                    break;
                case "CHANNEL_HANGUP":
                    System.out.println("----------Hangup call----------");
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void backgroundJobResultReceived(EslEvent eslEvent) {
        String jobUuid = eslEvent.getEventHeaders().get("Job-UUID");
        System.out.println(jobUuid);
    }
}
