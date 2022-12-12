package campaign.service.dto;

import campaign.config.Constants;
import campaign.domain.ERole;
import campaign.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;

public class UserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String username;

    @NotBlank
    @Size(min = 1, max = 60)
    private String password;

    @Size(max = 200)
    private String firstname;

    @NotBlank
    @Size(min = 1, max = 200)
    private String lastname;

    private LocalDateTime expiredDate;

    private String imageUrl;

    private byte[] imageBlob;

    private ERole role;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.expiredDate = user.getExpiredDate();
        this.imageUrl = user.getImageUrl();
        this.imageBlob = user.getImageBlob();
        if (user.getRole() != null) {
            this.role = user.getRole().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", expiredDate=" + expiredDate +
            ", imageUrl='" + imageUrl + '\'' +
            ", imageBlob=" + Arrays.toString(imageBlob) +
            ", role='" + role + '\'' +
            '}';
    }
}
