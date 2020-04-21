package com.solactive.solactivetickapp.utility;

import com.solactive.solactivetickapp.utils.Utility;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(profiles = { "test" })
public class UtilityTest {

    @Test
    public void testValidTimestamp(){
        Boolean valid = Utility.validateTimeStamp(Instant.now().minus(Duration.ofSeconds(30)).toEpochMilli());
        assertEquals(Boolean.FALSE, valid);
    }

    @Test
    public void testNonValidTimestamp(){
        Boolean valid = Utility.validateTimeStamp(Instant.now().minus(Duration.ofSeconds(90)).toEpochMilli());
        assertEquals(Boolean.TRUE, valid);
    }
}
