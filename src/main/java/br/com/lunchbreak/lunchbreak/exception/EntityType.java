package br.com.lunchbreak.lunchbreak.exception;

public enum EntityType {

    USER("User"),
    RESTAURANT("Restaurant"),
    VOTE("Vote");

    private String name;

    private EntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
