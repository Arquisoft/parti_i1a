package uniovi.asw.serializers;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import uniovi.asw.persistence.model.Comment;

public class CommentDeserializer implements Deserializer<Comment> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public Comment deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		Comment proposal = null;
		try {
			proposal = mapper.readValue(arg1, Comment.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proposal;
	}
}
