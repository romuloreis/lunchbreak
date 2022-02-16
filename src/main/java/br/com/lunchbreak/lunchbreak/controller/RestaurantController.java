package br.com.lunchbreak.lunchbreak.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.lunchbreak.lunchbreak.exception.AppException;
import br.com.lunchbreak.lunchbreak.persistence.model.Restaurant;
import br.com.lunchbreak.lunchbreak.service.RestaurantService;

@Controller
@RequestMapping("/admin")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

     private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public ModelAndView restaurants() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("restaurants");

        try {
            List<Restaurant> loadAllRestaurants = restaurantService.loadAll();
            if(loadAllRestaurants.isEmpty()){
                modelAndView.addObject("restaurants", loadAllRestaurants);
            }else{
                modelAndView.addObject("restaurants", loadAllRestaurants);
            }
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            error("Something went wrong... " + e.getMessage());
        }
        
        return modelAndView;
    }

    @RequestMapping(value = {"/restaurants/create"}, method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create");
        
        Restaurant restaurant = new Restaurant();
        
        modelAndView.addObject("restaurant", restaurant);

        return modelAndView;
    }

    @RequestMapping(value = {"/restaurants/save"}, method = RequestMethod.POST)
    public ModelAndView save(Restaurant restaurant) {
        try {
            restaurantService.save(restaurant);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            error("Something went wrong... " + e.getMessage());
        }        
        return restaurants();
    }

    @RequestMapping(value = {"/restaurants/edit/{id}"}, method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") long id) { 
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit");
        
        try {
            //Restaurant restaurant = new Restaurant();
            Restaurant restaurant = restaurantService.loadById(id);
            modelAndView.addObject("restaurant", restaurant);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            error("Something went wrong... " + e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/restaurants/delete/{id}"}, method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") long id) {       
        try {
            restaurantService.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage());
            error("Something went wrong... " + e.getMessage());
        }
        return restaurants();
    }

    @GetMapping(value = {"/error"})
    public ModelAndView error(String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

}
