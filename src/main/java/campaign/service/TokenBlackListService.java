package campaign.service;

import campaign.domain.TokenBlackList;
import campaign.repository.TokenBlackListRepository;
import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TokenBlackListService {

    private final JHipsterProperties jHipsterProperties;

    private final TokenBlackListRepository tokenBlackListRepository;

    public TokenBlackListService(JHipsterProperties jHipsterProperties, TokenBlackListRepository tokenBlackListRepository) {
        this.jHipsterProperties = jHipsterProperties;
        this.tokenBlackListRepository = tokenBlackListRepository;
    }

    public List<TokenBlackList> getTokenBlackLists() {
        return tokenBlackListRepository.findAll();
    }

    public void addTokenToBlackList(String token) {

        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
        byte[] keyBytes;
        if (!StringUtils.isEmpty(secret)) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            keyBytes = Decoders.BASE64.decode(jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret());
        }
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Date expiration = ((Claims)Jwts.parser().setSigningKey(key).parse(token).getBody()).getExpiration();
        if (expiration == null) {
            expiration = new Date();
        }
        if (!token.isEmpty()) {
            tokenBlackListRepository.save(new TokenBlackList(token, ServiceUtils.convertToLocalDateTime(expiration)));
        }
    }

    public void deleteExpiredToken() {
        tokenBlackListRepository.deleteAllByExpiredDateBefore(ServiceUtils.convertToLocalDateTime(new Date()));
    }
}
