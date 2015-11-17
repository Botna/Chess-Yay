package chess2.customEXC;

/**
 * Created by Botna on 11/17/2015.
 */
public class VersionMismatchException extends Exception {

    public VersionMismatchException() {
    super();
}
    public VersionMismatchException(String message) {
        super(message);
    }
}
