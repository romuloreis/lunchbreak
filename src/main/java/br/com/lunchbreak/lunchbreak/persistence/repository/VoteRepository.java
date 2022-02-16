package br.com.lunchbreak.lunchbreak.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.lunchbreak.lunchbreak.persistence.model.Vote;

@Repository
public class VoteRepository {
    private final InMemoryDataStorage dataStorage;

    public VoteRepository() {
        this.dataStorage = InMemoryDataStorage.getInstance();
    }

    public List<Vote> findAll(){
        return dataStorage.votes();
    }

    public Optional<Vote> findById(long id){
        Optional<Vote> result = null;

        result = dataStorage.votes().stream()
        .filter(vote -> vote.getId()==id).findFirst();

        return result;
    }

    public void save(Vote vote){
        dataStorage.votes().add(vote);
    }

    public boolean existsById(long id){
        boolean result = false;

        result = dataStorage.votes().stream()
        .filter(vote -> vote.getId()==id).findFirst().isPresent();

        return result;
    }

    public boolean exists(Vote vote){
        return dataStorage.votes().contains(vote);
    }

    public void delete(Vote vote){
        dataStorage.votes().remove(vote);
    }

    public long count(){
        return dataStorage.votes().size();
    }

}
