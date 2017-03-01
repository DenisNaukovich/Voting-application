package OTS.task.votingApp.exceptions;

public class VotingNotFoundException extends RuntimeException {

    public VotingNotFoundException(String votingId) {
        super("The voting having id = " + votingId + " not exists!");
    }
}
