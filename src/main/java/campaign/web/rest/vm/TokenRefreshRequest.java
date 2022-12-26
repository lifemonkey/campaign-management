package campaign.web.rest.vm;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "TokenRefreshRequest{" +
            "refreshToken='" + refreshToken + '\'' +
            '}';
    }
}
