package eu.garudaacademy.api;

import eu.garudaacademy.api.services.DatabaseBackupService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"eu.garudaacademy.api"})
public class ApiApplication {

	private static ConfigurableApplicationContext context;
	private static final DatabaseBackupService databaseBackupService = new DatabaseBackupService();

	public static void main(String[] args) {
		context = SpringApplication.run(ApiApplication.class, args);
	}

	@Scheduled(cron = "0 * * * * ?")
	public static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			databaseBackupService.backupDb();
			context = SpringApplication.run(ApiApplication.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}

}
