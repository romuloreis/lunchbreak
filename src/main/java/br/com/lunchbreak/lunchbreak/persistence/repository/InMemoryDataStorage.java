package br.com.lunchbreak.lunchbreak.persistence.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.lunchbreak.lunchbreak.persistence.model.Restaurant;
import br.com.lunchbreak.lunchbreak.persistence.model.Vote;

final class InMemoryDataStorage {
    private static InMemoryDataStorage instance;
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();
    private List<Vote> votes = new ArrayList<Vote>();

    public static InMemoryDataStorage getInstance() {
        if (instance == null) {
            instance = new InMemoryDataStorage();
        }
        return instance;
    }

    public List<Restaurant> restaurants() {
        return restaurants;
    }

    public List<Vote> votes() {
        return votes;
    }
        
}
