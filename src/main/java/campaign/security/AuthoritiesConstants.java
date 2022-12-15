package campaign.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ADMIN";

    public static final String BO_STAFF = "BO_STAFF";

    public static final String FIN_STAFF = "FIN_STAFF";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
