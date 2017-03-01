package OTS.task.votingApp.repository;

import OTS.task.votingApp.model.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteOptionRepository  extends JpaRepository<VoteOption, Long> {

    @Modifying
    @Query("update VoteOption vo set vo.counterVotes = vo.counterVotes + 1 where vo.id = ?1")
    void increaseCounterVotesById(Long votingId);

}
