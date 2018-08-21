import lombok.Getter;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 */
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    private String code = "500";

    public CommonException(String message) {
        this(message, "500");
    }

    public CommonException(String message, String status) {
        super(message);
        this.code = status;
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(Throwable cause, String status) {
        super(cause);
        this.code = status;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message, Throwable cause, String status) {
        super(message, cause);
        this.code = status;
    }

    public String getMessage() {
        String msg = super.getMessage();

        Throwable child;
        for (Object parent = this; (child = this.getNestedException((Throwable) parent)) != null; parent = child) {
            String msg2 = child.getMessage();
            if (msg2 != null) {
                if (msg != null) {
                    msg = msg + ": " + msg2;
                } else {
                    msg = msg2;
                }
            }

            if (child instanceof CommonException) {
                break;
            }
        }

        return msg;
    }

    private Throwable getNestedException(Throwable parent) {
        return parent.getCause();
    }

    public void printStackTrace() {
        super.printStackTrace();
        Object parent = this;

        Throwable child;
        while ((child = this.getNestedException((Throwable) parent)) != null) {
            System.err.print("Caused by: ");
            child.printStackTrace();
            if (child instanceof CommonException) {
                break;
            }

            parent = child;
        }

    }

    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        Object parent = this;
        printStream(new PrintWriter(s), parent);

    }

    private void printStream(PrintWriter s, Object parent) {
        Throwable child;
        while ((child = this.getNestedException((Throwable) parent)) != null) {
            s.print("Caused by: ");
            child.printStackTrace(s);
            if (child instanceof CommonException) {
                break;
            }

            parent = child;
        }
    }

    public void printStackTrace(PrintWriter w) {
        super.printStackTrace(w);
        Object parent = this;

        printStream(w, parent);

    }
}
