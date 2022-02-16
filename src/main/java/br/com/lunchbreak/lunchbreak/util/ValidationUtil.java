package br.com.lunchbreak.lunchbreak.util;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {
    
    private final static LocalTime DEFAULT_EXPIRED_TIME_VOTING = LocalTime.parse("11:30");

    public boolean isVotingTimeExpired() {
        return LocalTime.now().isAfter(DEFAULT_EXPIRED_TIME_VOTING);
    }

}
