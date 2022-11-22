package campaign.service;

import campaign.domain.RefreshToken;
import campaign.domain.User;
import campaign.repository.RefreshTokenRepository;
import campaign.repository.UserRepository;
import campaign.security.jwt.TokenRefreshException;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final JHipsterProperties jHipsterProperties;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshTokenService(JHipsterProperties jHipsterProperties, RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.jHipsterProperties = jHipsterProperties;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String userName) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findByUsername(userName).get());
        Instant expiredDate = Instant.now().plusMillis(jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe());
        refreshToken.setExpiredDate(ServiceUtils.convertToLocalDateTime(expiredDate));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int revokeUserAuthentication(String userName) {
        Optional<User> userOpt = userRepository.findByUsername(userName);
        if (userOpt.isPresent()) {
            return refreshTokenRepository.deleteByUser(userOpt.get());
        }

        return 0;
    }
}
