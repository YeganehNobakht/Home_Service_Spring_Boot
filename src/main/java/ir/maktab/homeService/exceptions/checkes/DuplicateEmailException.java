package ir.maktab.homeService.exceptions.checkes;

public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
