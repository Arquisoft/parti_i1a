package uniovi.asw.serializers;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommentMessageSerializer implements Serializer<CommentMessage> {

	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
	}

	@Override
	public byte[] serialize(String arg0, CommentMessage comment) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(comment)
					.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

}
