package asw.serializers;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import asw.persistence.model.Proposal;

public class ProposalDeserializer implements Deserializer<Proposal> {

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
    }

    @Override
    public Proposal deserialize(String arg0, byte[] arg1) {
	ObjectMapper mapper = new ObjectMapper();
	Proposal proposal = null;
	try {
	    proposal = mapper.readValue(arg1, Proposal.class);
	} catch (Exception e) {

	    e.printStackTrace();
	}
	return proposal;
    }
}
