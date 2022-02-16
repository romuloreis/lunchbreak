package br.com.lunchbreak.lunchbreak.persistence.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.lunchbreak.lunchbreak.persistence.model.Restaurant;

@Repository
public class RestaurantRepository {

    private final InMemoryDataStorage dataStorage;

    public RestaurantRepository() {
        this.dataStorage = InMemoryDataStorage.getInstance();
    }

    public List<Restaurant> findAll(){
        return dataStorage.restaurants();
    }

    public Restaurant findById(long id){
        Restaurant result = null;

        result = dataStorage.restaurants().stream()
        .filter(restaurant -> restaurant.getId()==id).findFirst().get();

        return result;
    }

    public void save(Restaurant restaurant){
            dataStorage.restaurants().add(restaurant);
    }

    public void update(Restaurant restaurant){
        Restaurant old = findById(restaurant.getId());
        old.setName(restaurant.getName());
        old.setDescription(restaurant.getDescription());
    }

    public boolean existsById(long id){
        boolean result = false;

        result = dataStorage.restaurants().stream()
        .filter(restaurant -> restaurant.getId()==id).findFirst().isPresent();

        return result;
    }

    public boolean exists(Restaurant restaurant){
        return dataStorage.restaurants().contains(restaurant);
    }

    public void delete(Restaurant restaurant){
        dataStorage.restaurants().remove(restaurant);
    }

    public void deleteById(long id){
        dataStorage.restaurants().remove(findById(id));
    }
    
    public long count(){
        return dataStorage.restaurants().size();
    }
}