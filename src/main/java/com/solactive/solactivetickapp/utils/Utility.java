package com.solactive.solactivetickapp.utils;

import java.time.Duration;
import java.time.Instant;

public class Utility {

    /**
     * Validate if timestamp is earlier than 60sec/1min
     * @param input
     * @return true/false
     */
    public static boolean validateTimeStamp(long input){
        Instant minute = Instant.now().minus(Duration.ofSeconds(60));
        Instant instant = Instant.ofEpochMilli(input);
        return instant.isBefore(minute);
    }
}
