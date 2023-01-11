package campaign.web.rest;

import campaign.config.Constants;
import campaign.security.AuthoritiesConstants;
import campaign.service.UserService;
import campaign.service.dto.UserDTO;
import campaign.web.rest.util.PaginationUtil;
import campaign.web.rest.vm.ResponseCode;
import campaign.web.rest.vm.ResponseVM;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<List<UserDTO>> getAllUsers(
        Pageable pageable,
        @RequestParam(required = false) String search
    ) {
        final Page<UserDTO> page;
        if (search != null) {
            page = userService.searchUsers(pageable, search);
        } else {
            page = userService.getAllUsers(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /user/{id} : get user by Id
     *
     * @PathVariable id of user
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/user/{id}")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public ResponseEntity<Object> getUserById(@Valid @PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
            new ResponseVM(
                ResponseCode.RESPONSE_NOT_FOUND,
                ResponseCode.ERROR_CODE_USER_NOT_FOUND,
                "User ID:" + id + " not found!"),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND);
    }

    /**
     * @return list string of the roles
     */
    @GetMapping("/users/roles")
    @Timed
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "') or hasAuthority('" + AuthoritiesConstants.FIN_STAFF + "')")
    public List<String> getRoles() {
        return userService.getRoles();
    }

    /**
     * GET /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @Timed
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return new ResponseEntity<>(userService.getUserWithRoleByUsername(login), new HttpHeaders(), HttpStatus.OK);
    }
}
