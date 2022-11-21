package campaign.web.rest;

import campaign.domain.RefreshToken;
import campaign.security.SecurityUtils;
import campaign.security.jwt.JWTConfigurer;
import campaign.security.jwt.TokenProvider;
import campaign.security.jwt.TokenRefreshException;
import campaign.service.RefreshTokenService;
import campaign.service.UserDetailsImpl;
import campaign.web.rest.vm.LoginVM;
import campaign.web.rest.vm.TokenRefreshRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
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
    public ResponseEntity<String> logout() {
        Optional<String> userOpt = SecurityUtils.getCurrentUserLogin();

        if (!userOpt.get().isEmpty()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            int response = refreshTokenService.revokeUserAuthentication(userOpt.get());
            return ResponseEntity.ok("User has been logout successfully!");
        }

        return ResponseEntity.ok("User is not valid!");
    }

    @PostMapping("/refreshToken")
    @Timed
    public ResponseEntity<JWTToken> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String jwt = tokenProvider.createToken(authentication, false);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
                return new ResponseEntity<>(new JWTToken(jwt, requestRefreshToken), httpHeaders, HttpStatus.OK);
            })
            .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                "Refresh token is not in database!"));

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
