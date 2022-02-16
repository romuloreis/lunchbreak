package br.com.lunchbreak.lunchbreak.persistence.model;

import java.time.LocalDate;

public class Vote {
    private long id;
    private LocalDate date;
    private String username;
    private long restaurantId;
    
    public Vote(String username, long restaurantId) {
        this.id = -1;
        this.date = LocalDate.now();
        this.username = username;
        this.restaurantId = restaurantId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

}