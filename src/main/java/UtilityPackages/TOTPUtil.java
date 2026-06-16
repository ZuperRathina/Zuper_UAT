package UtilityPackages;

import java.time.Duration;
import java.time.Instant;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;
import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

public class TOTPUtil {

    public static String generateTOTP(String base32Secret) throws Exception {
        // Validate Base32 secret
        Base32 base32 = new Base32();
        if (!base32.isInAlphabet(base32Secret.toUpperCase())) {
            throw new IllegalArgumentException("Invalid Base32 secret");
        }

        // Decode base32 secret
        byte[] keyBytes = base32.decode(base32Secret.toUpperCase());

        // Create SecretKey for HMAC-SHA1
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA1");

        // Create TOTP generator: 30 seconds, 6 digits
        TimeBasedOneTimePasswordGenerator totp =
                new TimeBasedOneTimePasswordGenerator(Duration.ofSeconds(30), 6);

        // Get current time
        Instant now = Instant.now();

        // Generate OTP
        int otp = totp.generateOneTimePassword(key, now);

        // Ensure 6-digit output by formatting
        String formattedOtp = String.format("%06d", otp % 1000000);
        return formattedOtp;
    }

}