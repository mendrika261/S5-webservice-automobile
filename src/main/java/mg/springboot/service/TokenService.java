package mg.springboot.service;

import jakarta.servlet.http.HttpServletRequest;
import mg.springboot.entity.Utilisateur;
import mg.springboot.repository.TokenRepository;
import mg.springboot.security.Token;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository utilisateurRepository) {
        this.tokenRepository = utilisateurRepository;
    }

    public Token createFor(Utilisateur utilisateur) {
        Token token = new Token(utilisateur);
        tokenRepository.save(token);
        return token;
    }

    public Token getToken(HttpServletRequest request) {
        String bearerToken = Token.getBearerToken(request);
        if(bearerToken != null) {
            try {
                UUID uuid = UUID.fromString(bearerToken);
                return tokenRepository.findByValue(uuid).orElse(null);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    public void delete(Token token) {
        if (token != null) {
            token.setDeleted(true);
            tokenRepository.save(token);
        }
    }

    public void updateLastActivityByValue(LocalDateTime now, Token token) {
        tokenRepository.updateLastActivityByValue(now, token.getValue());
    }
}
