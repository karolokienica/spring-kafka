package app;

import app.common.Foo2;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JacksonJsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@SpringBootApplication(scanBasePackages = {"app.controller", "app.common"})
public class KafkaApplication {
	Logger logger = LoggerFactory.getLogger(KafkaApplication.class);
	static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@Bean
	public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
		return new DefaultErrorHandler(
				new DeadLetterPublishingRecoverer(template),
				new FixedBackOff(1000L, 2)
		);
	}

	@Bean
	public RecordMessageConverter messageConverter() {
		return new JacksonJsonMessageConverter();
	}

	@KafkaListener(id = "fooGroup", topics = "topic1")
	public void listener(Foo2 foo) {
		logger.info("Received: {}", foo);
		if (foo.foo().startsWith("fail")) {
			throw new RuntimeException("failed");
		}
	}

	@KafkaListener(id = "dlt", topics = "topic1-dlt")
	public void dltListen(byte[] in) {
		logger.info("Received from dlt: {}", new String(in));
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("topic1", 1, (short) 1);
	}

	@Bean
	public NewTopic dlt() {
		return new NewTopic("topic1-dlt", 1, (short) 1);
	}
}
