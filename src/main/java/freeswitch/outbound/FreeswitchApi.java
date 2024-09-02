package freeswitch.outbound;

import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.internal.AbstractEslClientHandler;
import org.freeswitch.esl.client.transport.SendMsg;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslHeaders;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import java.util.Map;


import static freeswitch.inbound.EslClient.client;
/**
 * @author T@nvir
 */

@Component
public class FreeswitchApi extends AbstractEslClientHandler {

    public void bridgeCall(String destNumber) {
        SendMsg bridgeMsg = new SendMsg();
        bridgeMsg.addCallCommand("execute");
        bridgeMsg.addExecuteAppName("bridge");
        bridgeMsg.addExecuteAppArg("user/" + destNumber);
        Channel channel = null;
        EslMessage response = sendSyncMultiLineCommand(channel, bridgeMsg.getMsgLines());
        if (response.getHeaderValue(EslHeaders.Name.REPLY_TEXT).startsWith("+OK")) {
            System.out.println(" bridge to " + destNumber + " successful");
        } else {
            System.out.println("Call bridge failed: " + response.getHeaderValue(EslHeaders.Name.REPLY_TEXT));
        }

    }
    public void playSound(Channel channel, EslEvent event) {
        String uuid = event.getEventHeaders().get("Unique-ID");
        SendMsg playbackMsg = new SendMsg(uuid);
        playbackMsg.addCallCommand("execute");
        playbackMsg.addExecuteAppName("playback");
        EslMessage response = sendSyncMultiLineCommand(channel, playbackMsg.getMsgLines());
        if (response.getHeaderValue(EslHeaders.Name.REPLY_TEXT).startsWith("+OK")) {
            System.out.println("playback successful");
        } else {
            System.out.println("playback failed: " + response.getHeaderValue(EslHeaders.Name.REPLY_TEXT));
        }
    }

    public void hangupCall(Channel channel) {
        SendMsg hangupMsg = new SendMsg();
        hangupMsg.addCallCommand("execute");
        hangupMsg.addExecuteAppName("hangup");

        EslMessage response = sendSyncMultiLineCommand(channel, hangupMsg.getMsgLines());
        if (response.getHeaderValue(EslHeaders.Name.REPLY_TEXT).startsWith("+OK")) {
            System.out.println("Call hangup successful");
        } else {
            System.out.println("Call hangup failed: " + response.getHeaderValue(EslHeaders.Name.REPLY_TEXT));
        }
    }

    public void transferUuid(Map<String, Object> eventMap) throws InboundConnectionFailure {
        Map<String, Object> eventHeaders = (Map<String, Object>) eventMap.get("eventHeaders");
        String uuid = (String) eventHeaders.get("Unique-ID");

        String command = String.format("uuid_setvar " + uuid + " effective_caller_id_name 9999");
        client.sendSyncApiCommand(command, "");

        command = String.format("uuid_setvar "+ uuid + " effective_caller_id_number 9999" );
        client.sendSyncApiCommand(command, "");
        String calledNumber = (String) eventHeaders.get("Caller-Destination-Number");
        command = String.format("uuid_transfer " + uuid + " " + "2222" + " XML after_esl");
        client.sendSyncApiCommand(command, "");
    }
    public void dropCall(String uid) {
        client.sendAsyncApiCommand("uuid_kill", uid);
    }

    protected  void handleEslEvent(ChannelHandlerContext ctx, EslEvent event ){}

    protected  void handleAuthRequest( ChannelHandlerContext ctx ){}

    protected  void handleDisconnectionNotice(){
    }
}
