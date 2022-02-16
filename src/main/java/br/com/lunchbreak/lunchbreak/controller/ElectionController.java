package br.com.lunchbreak.lunchbreak.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.lunchbreak.lunchbreak.persistence.model.Restaurant;
import br.com.lunchbreak.lunchbreak.persistence.model.Vote;
import br.com.lunchbreak.lunchbreak.service.RestaurantService;
import br.com.lunchbreak.lunchbreak.service.VoteService;
import br.com.lunchbreak.lunchbreak.util.ValidationUtil;

@Controller
@RequestMapping("/election")
public class ElectionController {
    private static final Logger logger = LoggerFactory.getLogger(ElectionController.class);

    @Autowired
    private final VoteService voteService;

    @Autowired
    private final RestaurantService restaurantService;

    @Autowired
    private final ValidationUtil validationUtil;

    public ElectionController(VoteService voteService, RestaurantService restaurantService, ValidationUtil validationUtil) {
        this.voteService = voteService;
        this.restaurantService = restaurantService;
        this.validationUtil = validationUtil;
    }

    @GetMapping(value = {"/restaurants"})
    public ModelAndView index(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(validationUtil.isVotingTimeExpired()){
            modelAndView = result(authentication, modelAndView);
        }else{
            modelAndView = election(authentication, modelAndView);
        }

        return modelAndView;
    }

    public ModelAndView election(Authentication authentication, ModelAndView modelAndView) {

        modelAndView.setViewName("election");
        
        modelAndView.addObject("hasUserVotedMessage", getHasUserVotedMessage(authentication));

        try {
            List<Restaurant> loadAllNotWinners = restaurantService.loadAllNotWinners();
            modelAndView.addObject("restaurants", loadAllNotWinners);

            Restaurant mostVoted = restaurantService.loadMostVoted(loadAllNotWinners);
            if(mostVoted!=null){
                modelAndView.addObject("mostVoted", mostVoted);
            }
            
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            error("something went wrong... " + e.getMessage());
        }

        return modelAndView;
    }

    public ModelAndView result(Authentication authentication, ModelAndView modelAndView) {
        modelAndView.setViewName("result");
        modelAndView.addObject("hasUserVotedMessage", getHasUserVotedMessage(authentication));
    
        try {
            modelAndView.addObject("winner", restaurantService.loadWinnerByDate(LocalDate.now()));
        } catch (Exception e) {
            logger.error("Exception:" + e.getMessage());
            error("Something went wrong... " + e.getMessage());
        }           
        return modelAndView;
    }

    @RequestMapping(value = {"/restaurants/vote/{id}"}, method = RequestMethod.GET)
    public ModelAndView vote(@PathVariable("id") long id, Authentication authentication) { 
        ModelAndView modelAndView = new ModelAndView();
        try {
            if(!voteService.userHasVotedThisDate(authentication.getName(), LocalDate.now())){
                restaurantService.loadById(id).RegisterVote();
                voteService.registerVote(new Vote(authentication.getName(), id));
            }
        } catch (Exception e) {
            logger.error("Exception:" + e.getMessage());
            error("Something went wrong... " + e.getMessage());
        }
        return election(authentication, modelAndView);
    }

    @GetMapping(value = {"/error"})
    public ModelAndView error(String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    public String getHasUserVotedMessage(Authentication authentication){
        String hasUserVotedMessage = "You already voted today";
        if(!voteService.userHasVotedThisDate(authentication.getName(), LocalDate.now())){
            hasUserVotedMessage = "You still did not vote today. Please, register your vote until 11:30";
        }
        return hasUserVotedMessage;
    }

}
