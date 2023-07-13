package training.streamprotobufproducerdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.schema.registry.avro.AvroSchemaRegistryClientMessageConverter;
import org.springframework.cloud.stream.schema.registry.client.EnableSchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@SpringBootApplication
@EnableSchemaRegistryClient
public class StreamProtobufProducerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamProtobufProducerDemoApplication.class, args);
	}



}
