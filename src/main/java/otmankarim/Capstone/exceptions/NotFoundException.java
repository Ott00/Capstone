package otmankarim.Capstone.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Element: " + id);
    }

    public NotFoundException(UUID id) {
        super("Element: " + id);
    }

    public NotFoundException(String element) {
        super("Element: " + element);
    }
}
