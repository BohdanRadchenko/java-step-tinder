import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;
import org.tinder.models.User;
import org.tinder.utils.Config;
import org.tinder.utils.JWTToken;

import static org.junit.Assert.assertEquals;

public class TokeTest {
    @Test
    public void jwtTokenImplement() {

        Integer id = 1;
        String login = "login";
        String email = "email";
        String password = "password";
        String firstName = "first";
        String lastName = "last";
        String profession = "profession";
        String avatar = "avatar";

        User user = User.load(
                id,
                login,
                email,
                password,
                firstName,
                lastName,
                profession,
                avatar
        );

        String token = JWTToken.makeAccess(user).sign();

        DecodedJWT verify = JWTToken.verify(token, Config.getAccessTokenKey());

        Integer idClaim = verify.getClaim("id").asInt();
        String loginClaim = verify.getClaim("login").asString();
        String emailClaim = verify.getClaim("email").asString();
        String passwordClaim = verify.getClaim("password").asString();
        String firstNameClaim = verify.getClaim("firstName").asString();
        String lastNameClaim = verify.getClaim("lastName").asString();
        String professionClaim = verify.getClaim("profession").asString();
        String avatarClaim = verify.getClaim("avatar").asString();

        assertEquals("Equals id from token", id, idClaim);
        assertEquals("Equals login from token", login, loginClaim);
        assertEquals("Equals email from token", email, emailClaim);
        assertEquals("Equals password from token", password, passwordClaim);
        assertEquals("Equals firstName from token", firstName, firstNameClaim);
        assertEquals("Equals lastName from token", lastName, lastNameClaim);
        assertEquals("Equals profession from token", profession, professionClaim);
        assertEquals("Equals avatar from token", avatar, avatarClaim);
    }
}