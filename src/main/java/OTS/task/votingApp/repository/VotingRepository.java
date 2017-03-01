package OTS.task.votingApp.repository;

import OTS.task.votingApp.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {

    @Modifying
    @Query("update Voting v set v.closed = true where v.id = ?1")
    void closeVotingById(Long votingId);
}
