package br.com.lunchbreak.lunchbreak.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lunchbreak.lunchbreak.exception.EntityType;
import br.com.lunchbreak.lunchbreak.exception.ExceptionType;
import br.com.lunchbreak.lunchbreak.exception.AppException;
import br.com.lunchbreak.lunchbreak.persistence.model.Restaurant;
import br.com.lunchbreak.lunchbreak.persistence.repository.RestaurantRepository;

@Service
public class RestaurantService {
       
    private final RestaurantRepository restaurantRepository;
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> loadAll(){
        return restaurantRepository.findAll();
    }

    public List<Restaurant> loadAllWinners(){
        return restaurantRepository.findAll().stream().filter(p -> (p.getVictoryDate() != null)).toList();
    }

    public Restaurant loadWinnerByDate(LocalDate localDate){
        List<Restaurant> loadAllWinners = loadAllWinners();
        return loadAllWinners.stream().filter(p -> (p.getVictoryDate().isEqual(localDate))).findFirst().get();
    }

    public List<Restaurant> loadAllNotWinners(){
        return restaurantRepository.findAll().stream().filter(p -> (p.getVictoryDate() == null)).toList();
    }

    public Restaurant loadById(long id){
        if(existsById(id)){
            return restaurantRepository.findById(id);
        }else{
            return null;
        }
    }

    public void save(Restaurant restaurant){
        if(existsById(restaurant.getId())){
            restaurantRepository.update(restaurant);
        }else{
            restaurant.setId(restaurantRepository.count()+1);
            restaurantRepository.save(restaurant);
        }        
    }

    public boolean existsById(long id){
        return restaurantRepository.existsById(id);
    }

    public boolean exists(Restaurant restaurant){
        return restaurantRepository.exists(restaurant);
    }

    public void delete(Restaurant restaurant){
        if(existsById(restaurant.getId())){
            Restaurant res = loadById(restaurant.getId());
            restaurantRepository.delete(res);
        }else{
            logger.error("Exception - ID: " + restaurant.getId() + " not found");
            throw AppException.throwException(EntityType.RESTAURANT, ExceptionType.NOT_FOUND);
        }
    }

    public void deleteById(long id) throws AppException{
        if(id>0){
            if(existsById(id)){
                restaurantRepository.deleteById(id);;
            }else{    
                logger.error("Exception - ID: " + id + " not found");
                throw AppException.throwException(EntityType.RESTAURANT, ExceptionType.NOT_FOUND);
            }
        }else{
            logger.error("Exception - No valid ID provided");
            throw AppException.throwException(EntityType.RESTAURANT, ExceptionType.VALIDATION_ERROR);
        }
    }

    public long count(){
        return restaurantRepository.count();
    }

    public void registerTodaysWinner(){
            List<Restaurant> restaurants = restaurantRepository.findAll();
            if(!restaurants.isEmpty()){
                Restaurant restaurant = loadMostVoted(restaurants);
                if(restaurant!=null){
                    restaurant.setVictoryDate(LocalDate.now());
                    clearVotes(restaurants);
                }else{
                    logger.error("Exception - There is no winner");
                    throw AppException.throwException(EntityType.RESTAURANT, ExceptionType.NOT_FOUND);
                }
            }
    }

    public Restaurant loadMostVoted(List<Restaurant> restaurants){
        return restaurants.stream().max(Comparator.comparing(Restaurant::getVotes)).orElseThrow(NoSuchElementException::new);
    }
    
    public void clearVotes(List<Restaurant> restaurants){
        if(!restaurants.isEmpty()){
            for (Restaurant restaurant : restaurants) {
                restaurant.resetVotes();
            }
        }else{
            logger.error("Exception - There is no restaurants");
            throw AppException.throwException(EntityType.RESTAURANT, ExceptionType.NOT_FOUND);
        }
    }

    public void clearWinners(){
        List<Restaurant> restaurants = loadAllWinners();
        if(!restaurants.isEmpty()){
            for (Restaurant restaurant : restaurants) {
                restaurant.setVictoryDate(null);
            }
        }else{
            logger.error("Exception - There is no restaurants");
            throw AppException.throwException(EntityType.RESTAURANT, ExceptionType.NOT_FOUND);
        }
    }

}
