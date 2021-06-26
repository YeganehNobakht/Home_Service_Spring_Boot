package ir.maktab.homeservices.exceptions.checkes;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
