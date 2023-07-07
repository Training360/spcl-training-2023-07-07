package training.basicsecuritydemo;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordEncoderTest {

    @Test
    void encode() {
        var encoder = new BCryptPasswordEncoder();
        var hash = encoder.encode("user");
        System.out.println(hash);
        hash = encoder.encode("admin");
        System.out.println(hash);
    }
}
