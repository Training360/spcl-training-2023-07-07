package training.tasklauncherdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.launcher.annotation.EnableTaskLauncher;

@SpringBootApplication
@EnableTaskLauncher
public class TaskLauncherDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskLauncherDemoApplication.class, args);
	}

}
