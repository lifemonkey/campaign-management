package campaign.web.rest;

import campaign.domain.RefreshToken;
import campaign.domain.TokenBlackList;
import campaign.security.SecurityUtils;
import campaign.security.jwt.JWTConfigurer;
import campaign.security.jwt.TokenProvider;
import campaign.security.jwt.TokenRefreshException;
import campaign.service.RefreshTokenService;
import campaign.service.TokenBlackListService;
import campaign.web.rest.vm.LoginVM;
import campaign.web.rest.vm.TokenRefreshRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final TokenBlackListService tokenBlackListService;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, TokenBlackListService tokenBlackListService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.tokenBlackListService = tokenBlackListService;
    }

    @PostMapping("/login")
    @Timed
    public ResponseEntity<JWTToken> login(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);

        RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(loginVM.getUsername());
        return new ResponseEntity<>(new JWTToken(jwt, refreshToken.getToken()), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Timed
    public ResponseEntity<String> logout(final HttpServletRequest request) throws ServletException {
        Optional<String> userOpt = SecurityUtils.getCurrentUserLogin();

        if (userOpt.isPresent()) {
            int result = refreshTokenService.revokeUserAuthentication(userOpt.get());
            if (result == 1) {
                this.tokenBlackListService.addTokenToBlackList(SecurityUtils.resolveToken(request));
                HttpSession httpSession = request.getSession(false);
                if (httpSession != null) {
                    httpSession.invalidate();
                }
                return ResponseEntity.ok("User has been logout successfully!");
            }
        }

        return ResponseEntity.ok("User is not valid!");
    }

    @PostMapping("/refreshToken")
    @Timed
    public ResponseEntity<JWTToken> refreshToken(final HttpServletRequest request, @Valid @RequestBody TokenRefreshRequest tokenRequest) {
        String requestRefreshToken = tokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String jwt = tokenProvider.createToken(authentication, false);
                // add old token to black list
                this.tokenBlackListService.addTokenToBlackList(SecurityUtils.resolveToken(request));
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
                return new ResponseEntity<>(new JWTToken(jwt, requestRefreshToken), httpHeaders, HttpStatus.OK);
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                "Refresh token is not in database!"));

    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired every day, at 01:00 (am).
     */
    @PostMapping("/cleanToken")
    @Scheduled(cron = "0 0 1 * * ?")
    @Timed
    public void removeNotActivatedUsers() {
        log.debug("Deleting expired black list tokens {}");
        tokenBlackListService.deleteExpiredToken();
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        private String refreshToken;

        JWTToken(String idToken, String refreshToken) {
            this.idToken = idToken;
            this.refreshToken = refreshToken;
        }

        @JsonProperty("accessToken")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("refreshToken")
        String getRefreshToken() {
            return refreshToken;
        }

        void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
