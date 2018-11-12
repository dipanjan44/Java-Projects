
/**
 * This is the class for handling all the exception scenarios in plagiarism detector
 * @author Dipanjan
 *
 */
public class NoPlagException extends Exception {
    private String message;
    /**
     * This is the constructor
     * @param message
     */
    public NoPlagException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
