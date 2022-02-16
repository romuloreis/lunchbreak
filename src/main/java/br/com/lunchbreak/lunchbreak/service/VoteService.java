package br.com.lunchbreak.lunchbreak.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lunchbreak.lunchbreak.persistence.model.Vote;
import br.com.lunchbreak.lunchbreak.persistence.repository.VoteRepository;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    
    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> loadAll(){
        return voteRepository.findAll();
    }

    public Optional<Vote> findById(long id){
        return voteRepository.findById(id);
    }

    public void registerVote(Vote vote){
        vote.setId(voteRepository.count()+1);
        voteRepository.save(vote);
    }

    public boolean existsById(long id){
        return voteRepository.existsById(id);
    }

    public boolean exists(Vote vote){
        return voteRepository.exists(vote);
    }

    public void delete(Vote vote){
        voteRepository.delete(vote);
    }

    public boolean userHasVotedThisDate(String username, LocalDate date){
        return voteRepository.findAll().stream()
        .filter(vote -> vote.getUsername().equals(username) &&
        vote.getDate().equals(date)).findFirst().isPresent();
    }

    public long count(){
        return voteRepository.count();
    }

    public List<Vote> loadOldElectionRegistration(){
        return voteRepository.findAll().stream()
        .filter(vote -> vote.getDate().isBefore(LocalDate.now())).toList();
    }

    public void clearOldElectionRegistration(){
        for (Vote vote : loadOldElectionRegistration()) {
            delete(vote);
        }
    }
}
