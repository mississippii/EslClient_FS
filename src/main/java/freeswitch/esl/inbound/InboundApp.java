package freeswitch.esl.inbound;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.event.EslEvent;

/**
 * @author Tanvir
 */
public class InboundApp {


    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        try {
            client.connect("localhost", 8021, "ClueCon", 10);

            client.addEventListener(new IEslEventListener() {

                @Override
                public void eventReceived(EslEvent event) {
                    String eventName = event.getEventName();
                    if (eventName.startsWith("CHANNEL_")) {
                        String calleeNumber = event.getEventHeaders().get("Caller-Callee-ID-Number");
                        String callerNumber = event.getEventHeaders().get("Caller-Caller-ID-Number");
                        switch (eventName) {
                            case "CHANNEL_CREATE":
                                System.out.println("Channel Created");
                                break;
                            case "CHANNEL_BRIDGE":
                                System.out.println("Call bridge successfully");
                                break;
                            case "CHANNEL_ANSWER":
                                System.out.println("answar the call");
                                break;
                            case "CHANNEL_HANGUP":
                                String response = event.getEventHeaders().get("variable_current_application_response");
                                String hangupCause = event.getEventHeaders().get("Hangup-Cause");
                                System.out.println("hangup " + callerNumber + " , from：" + calleeNumber + " , response:" + response + " ,hangup cause:" + hangupCause);
                                break;
                            default:
                                break;
                        }
                    }
                }

                @Override
                public void backgroundJobResultReceived(EslEvent event) {
                    String jobUuid = event.getEventHeaders().get("Job-UUID");
                    System.out.println("The uniqid" + jobUuid);
                }
            });

            client.setEventSubscriptions("plain", "all");
            if (client.canSend()) {
                String callResult = client.sendAsyncApiCommand("originate", "user/1000 &playback(/tmp/demo.wav)");
                System.out.println("api uuid:" + callResult);
            }

        } catch (InboundConnectionFailure inboundConnectionFailure) {
            System.out.println("Call failed: " + inboundConnectionFailure);
            inboundConnectionFailure.printStackTrace();
        }

    }
}
