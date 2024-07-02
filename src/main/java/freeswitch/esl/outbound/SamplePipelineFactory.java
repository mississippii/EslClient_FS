package freeswitch.esl.outbound;

import org.freeswitch.esl.client.outbound.AbstractOutboundClientHandler;
import org.freeswitch.esl.client.outbound.AbstractOutboundPipelineFactory;

/**
 * @author Tanvir
 */
public class SamplePipelineFactory extends AbstractOutboundPipelineFactory {

    @Override
    protected AbstractOutboundClientHandler makeHandler() {
        return new SampleOutboundHandler();
    }
}
