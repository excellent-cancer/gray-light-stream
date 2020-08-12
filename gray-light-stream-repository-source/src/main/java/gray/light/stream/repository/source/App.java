package gray.light.stream.repository.source;

import gray.light.owner.entity.ProjectDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableRepositorySource(PollingUpdateIntegrationFlowFactory.class)
@SpringBootApplication
@Configuration
@Slf4j
@RequiredArgsConstructor
public class App {

    @Bean
    public RepositoryPollingUpdateProperties one() {
        return new RepositoryPollingUpdateProperties(
                () -> new ProjectDetails(1L, null, null, null, null, null, null, null),
                60L,
                TimeUnit.SECONDS
        );
    }

    @Bean
    public RepositoryPollingUpdateProperties two() {
        return new RepositoryPollingUpdateProperties(
                () -> new ProjectDetails(2L, null, null, null, null, null, null, null),
                60L,
                TimeUnit.SECONDS
        );
    }



    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
