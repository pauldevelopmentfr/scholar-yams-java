package fr.pauldevelopment.yams.exceptions;

public class TooMuchPlayersException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * IncorrectAmountOfPlayersException constructor
     *
     * @param errorMessage
     */
    public TooMuchPlayersException(String errorMessage) {
        super(errorMessage);
    }
}