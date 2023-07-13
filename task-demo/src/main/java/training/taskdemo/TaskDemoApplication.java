package training.taskdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class TaskDemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TaskDemoApplication.class, args);
	}

	public static class HelloWorldApplicationRunner implements ApplicationRunner {
		@Override
		public void run(ApplicationArguments args) throws Exception {
			LOGGER.debug("Hello World!");
		}
	}

}
