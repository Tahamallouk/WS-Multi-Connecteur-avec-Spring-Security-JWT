package ma.formations.multiconnector.common;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommonTools {
    public Instant now() {
        return Instant.now();
    }
}
