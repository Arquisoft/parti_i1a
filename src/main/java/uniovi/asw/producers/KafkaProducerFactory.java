package uniovi.asw.producers;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.serializers.ProposalSerializer;

@Configuration
@EnableKafka
public class KafkaProducerFactory {

	 @Bean
	    public ProducerFactory<String, String> producerFactory() {
	        return new DefaultKafkaProducerFactory<>(producerConfigs());
	    }

	    @Bean
	    public Map<String, Object> producerConfigs() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        props.put(ProducerConfig.RETRIES_CONFIG, 0);
	        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
	        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	        return props;
	    }

	    @Bean
	    public KafkaTemplate<String, String> kafkaTemplate() {
	        return new KafkaTemplate<String, String>(producerFactory());
	    }
	    
	    ////////////////////////////////////////////////////////////////////////////////////////////
	    
	    @Bean
	    public KafkaTemplate<String, Proposal> kafkaProposalTemplate() {
	        return new KafkaTemplate<String, Proposal>(proposalProducerFactory());
	    }

	    @Bean
	    public ProducerFactory<String, Proposal> proposalProducerFactory() {
		return new DefaultKafkaProducerFactory<>(proposalProducerConfigs());
	    }
	    
	    @Bean
	    public Map<String, Object> proposalProducerConfigs() {
	        Map<String, Object> props = new HashMap<>();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	        props.put(ProducerConfig.RETRIES_CONFIG, 0);
	        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
	        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ProposalSerializer.class);
	        return props;
	    }
}
