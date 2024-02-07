package mg.springboot;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class SpringbootApplication {
    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }

    @Bean
    FirebaseApp firebaseApp(GoogleCredentials credentials) {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        boolean hasBeenInitialized = FirebaseApp.getApps().stream().anyMatch(app -> app.getName().equals(FirebaseApp.DEFAULT_APP_NAME));
        if (hasBeenInitialized) return FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME);

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    GoogleCredentials googleCredentials() {
        try (InputStream serviceAccount = new ClassPathResource("google-services.json").getInputStream()) {
            return GoogleCredentials.fromStream(serviceAccount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
