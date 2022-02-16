package br.com.lunchbreak.lunchbreak.persistence.model;

import java.time.LocalDate;

public class Restaurant {
    private long id;
    private String name;
    private String description;
    private LocalDate victoryDate;
    private int votes;

    public Restaurant() {
    }

    public Restaurant(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.victoryDate = null;
        this.votes = 0;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getVictoryDate() {
        return victoryDate;
    }

    public void setVictoryDate(LocalDate victoryDate) {
        this.victoryDate = victoryDate;
    }

    public int getVotes() {
        return votes;
    }

    public void RegisterVote() {
        this.votes += 1;
    }

    public void resetVotes(){
        this.votes = 0;
    }
    
}
