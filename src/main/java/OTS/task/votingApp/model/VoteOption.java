package OTS.task.votingApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "option")
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "counter_votes")
    private int counterVotes;

    @JoinColumn(name = "voting_id")
    @ManyToOne()
    @NotNull
    @JsonIgnore
    private Voting voting;

    public VoteOption() {}

    public VoteOption(String name, Voting voting) {
        this.name = name;
        this.voting = voting;
    }

    public VoteOption(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCounterVotes() {
        return counterVotes;
    }

    public void setCounterVotes(int counterVotes) {
        this.counterVotes = counterVotes;
    }

    public Voting getVoting() {
        return voting;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }
}
