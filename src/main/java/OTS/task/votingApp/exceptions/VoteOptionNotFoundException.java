package OTS.task.votingApp.exceptions;

public class VoteOptionNotFoundException extends RuntimeException {

    public VoteOptionNotFoundException(String voteOptionId) {
        super("The vote option having id = " + voteOptionId + " not exists!");
    }
}
