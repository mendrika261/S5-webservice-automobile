package mg.springboot.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.Getter;
import lombok.Setter;
import mg.springboot.entity.Utilisateur;
import mg.springboot.repository.TokenRepository;
import mg.springboot.security.Token;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Getter
@Setter
public class NotificationService {
    private final TokenRepository tokenRepository;
    private final FirebaseMessaging firebaseMessaging;
    public final static String FIREBASE_IMG_URL = "https://firebasestorage.googleapis.com/v0/b/fiara-363ef.appspot.com/o/";

    public NotificationService(TokenRepository tokenRepository, FirebaseMessaging firebaseMessaging) {
        this.tokenRepository = tokenRepository;
        this.firebaseMessaging = firebaseMessaging;
    }

    public String[] getTokensNotification(String idUtilisateur) {
        return tokenRepository.findByUtilisateurIdAndNotificationTokenNotNull(idUtilisateur)
                .stream()
                .filter(Token::isValid)
                .map(Token::getNotificationToken)
                .toArray(String[]::new);
    }

    public void sendNotification(String title, String body, String imageUrl, String token) {
        Notification.Builder notificationBuilder = Notification.builder()
                .setTitle(title)
                .setBody(body);
        if (imageUrl != null) notificationBuilder.setImage(imageUrl);
        Notification notification = notificationBuilder.build();
        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();
        try {
            System.out.println(firebaseMessaging.send(message));
        } catch (FirebaseMessagingException e) {throw new RuntimeException(e);}
    }

    public void sendNotificationTo(String title, String body, String imageUrl, String utilisateurId) {
        System.out.println(imageUrl);
        for (String token : getTokensNotification(utilisateurId)) {
            sendNotification(title, body, imageUrl, token);
        }
    }

}
