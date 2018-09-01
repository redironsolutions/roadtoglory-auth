package io.rediron.auth.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * Altered org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder to
 * be compatible with PHP bcrypt()
 */
@Slf4j
public class BCryptPasswordEncoder implements PasswordEncoder {

    private Pattern BCRYPT_PATTERN;
    private final int strength;
    private final SecureRandom random;

    public BCryptPasswordEncoder() {
        this(-1);
    }

    public BCryptPasswordEncoder(int strength) {
        this(strength, (SecureRandom)null);
    }

    public BCryptPasswordEncoder(int strength, SecureRandom random) {

        // TODO: Revert this when java supports 2y bcrypt
        // Change to 2y from 2a
        //this.BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        this.BCRYPT_PATTERN = Pattern.compile("\\A\\$2y?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        if (strength == -1 || strength >= 4 && strength <= 31) {
            this.strength = strength;
            this.random = random;
        } else {
            throw new IllegalArgumentException("Bad strength");
        }
    }

    public String encode(CharSequence rawPassword) {
        String salt;
        if (this.strength > 0) {
            if (this.random != null) {
                salt = BCrypt.gensalt(this.strength, this.random);
            } else {
                salt = BCrypt.gensalt(this.strength);
            }
        } else {
            salt = BCrypt.gensalt();
        }

        // TODO: Revert this when java supports 2y bcrypt
        // Converting 2a to 2y
        salt = salt.replaceFirst("2a", "2y");
        return BCrypt.hashpw(rawPassword.toString(), salt).replaceFirst("2a", "2y");
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
                log.warn("Encoded password does not look like BCrypt");
                return false;
            } else {
                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
            }
        } else {
            log.warn("Empty encoded password");
            return false;
        }
    }
}