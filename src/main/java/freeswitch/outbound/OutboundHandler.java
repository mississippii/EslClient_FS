package freeswitch.outbound;

import org.freeswitch.esl.client.outbound.AbstractOutboundClientHandler;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
/**
 * @author T@nvir
 */

@Component
public class OutboundHandler extends AbstractOutboundClientHandler {

    @Override
    protected void handleEslEvent(ChannelHandlerContext ctx, EslEvent event) {
        System.out.println("received event:" + event);
    }

    @Override
    protected void handleConnectResponse(ChannelHandlerContext ctx, EslEvent event) {
        System.out.println("Received connect response :" + event);
        if (event.getEventName().equalsIgnoreCase("CHANNEL_DATA")) {
            System.out.println("=======================  incoming channel data  =============================");
            System.out.println("Event-Date-Local: " + event.getEventDateLocal());
            System.out.println("Unique-ID: " + event.getEventHeaders().get("Unique-ID"));
            System.out.println("Channel-ANI: " + event.getEventHeaders().get("Channel-ANI"));
            System.out.println("Answer-State: " + event.getEventHeaders().get("Answer-State"));
            System.out.println("Caller-Destination-Number: " + event.getEventHeaders().get("Caller-Destination-Number"));
            System.out.println("=======================  = = = = = = = = = = =  =============================");
            String callerNumber = event.getEventHeaders().get("Caller-Caller-ID-Number");
            if (callerNumber.equalsIgnoreCase("1002")) {
                System.out.println(callerNumber + " is in blacklist !");
            } else {
                System.out.println(callerNumber + " is in whitelist !");
            }
        } else {
            throw new IllegalStateException("Unexpected event after connect: [" + event.getEventName() + ']');
        }
    }
    @Override
    protected void handleDisconnectionNotice() {
        super.handleDisconnectionNotice();
        System.out.println("Received disconnection notice");
    }
}
