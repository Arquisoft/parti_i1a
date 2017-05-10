package uniovi.asw.serializers;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommentMessageDeserializer implements Deserializer<CommentMessage> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public CommentMessage deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		CommentMessage comment = null;
		try {
			comment = mapper.readValue(arg1, CommentMessage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment;
	}
}
