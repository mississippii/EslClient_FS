package freeswitch.outbound;

import org.freeswitch.esl.client.outbound.AbstractOutboundClientHandler;
import org.freeswitch.esl.client.outbound.AbstractOutboundPipelineFactory;
import org.springframework.stereotype.Component;
/**
 * @author T@nvir
 */
@Component
public class SamplePipelineFactory extends AbstractOutboundPipelineFactory {
    private final AbstractOutboundClientHandler outboundClientHandler;

    public SamplePipelineFactory(AbstractOutboundClientHandler outboundClientHandler) {
        this.outboundClientHandler = outboundClientHandler;
    }

    @Override
    protected AbstractOutboundClientHandler makeHandler() {
        return outboundClientHandler;
    }
}
