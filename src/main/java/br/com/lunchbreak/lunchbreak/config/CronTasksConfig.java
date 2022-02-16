package br.com.lunchbreak.lunchbreak.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.lunchbreak.lunchbreak.service.RestaurantService;
import br.com.lunchbreak.lunchbreak.service.VoteService;

@Configuration
@EnableScheduling
public class CronTasksConfig {

    private final RestaurantService restaurantService;
    private final VoteService voteService;

    private static final Logger logger = LoggerFactory.getLogger(CronTasksConfig.class);
    
    @Autowired
    public CronTasksConfig(RestaurantService restaurantService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.voteService = voteService;
    }

    /* Fire at 11:30 AM every day */
    @Scheduled(cron = "0 30 11 * * ?")
    public void scheduleFixedDailyTask() {
        restaurantService.registerTodaysWinner();
        logger.info("Fixed Daily task");
    }

    /* Fire at 11:50 AM every sunday */
    @Scheduled(cron = "0 50 11 ? * SUN")
    public void scheduleFixedWeeklyTask() {
        restaurantService.clearWinners();
        logger.info("Fixed Weekly task");
    }  

    /* Fire At 00:00 on every first day of the month */
    @Scheduled(cron = "0 0 0 1 * ?")
        public void scheduleFixedMonthlyTask() {
        voteService.clearOldElectionRegistration();
        logger.info("Fixed Monthly task");
    } 
}
