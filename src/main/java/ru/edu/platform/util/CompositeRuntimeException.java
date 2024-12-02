package ru.edu.platform.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CompositeRuntimeException extends RuntimeException {

    private final String message;
    List<Exception> exceptions;

    public CompositeRuntimeException() {
        this(null, List.of());
    }

    public CompositeRuntimeException(String message) {
        this(message, List.of());
    }

    public CompositeRuntimeException(String message, Exception... inner) {
        this(message, Arrays.asList(inner));
    }

    public CompositeRuntimeException(Exception... inner) {
        this(null, Arrays.asList(inner));
    }

    public CompositeRuntimeException(Collection<Exception> exceptions) {
        this(null, exceptions);
    }

    public CompositeRuntimeException(String message, Collection<Exception> exception) {
        this.exceptions = new ArrayList<>(exception);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        printStackTrace(new WrappedPrintStream(s));
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        printStackTrace(new WrappedPrintWriter(s));
    }

    private void printStackTrace(PrintStreamOrWriter out) {
        out.append(this).append("\n");
        for (StackTraceElement element : getStackTrace()) {
            out.append("\tat").append(element).append("\n");
        }
        for (int i = 0; i < this.exceptions.size(); i++) {
            Exception e = exceptions.get(i);
            out.append("\t").append("Inner exception: ").append(i);
            appendStackTrace(out, e, "\t");
        }
    }

    private void appendStackTrace(PrintStreamOrWriter output, Throwable ex, String prefix) {
        output.append(prefix).append(ex).append('\n');
        for (StackTraceElement stackElement : ex.getStackTrace()) {
            output.append("\t\tat ").append(stackElement).append('\n');
        }
        if (ex.getCause() != null) {
            output.append("\tCaused by: ");
            appendStackTrace(output, ex.getCause(), "");
        }
    }

    public List<? extends Exception> getExceptions() {
        return this.exceptions;
    }

    private interface PrintStreamOrWriter {

        PrintStreamOrWriter append(Object o);
    }

    private final class WrappedPrintStream implements PrintStreamOrWriter {
        private final PrintStream printStream;

        public WrappedPrintStream(PrintStream printStream) {
            this.printStream = printStream;
        }

        @Override
        public PrintStreamOrWriter append(Object o) {
            printStream.print(o);
            return this;
        }
    }

    private static final class WrappedPrintWriter implements PrintStreamOrWriter {
        private final PrintWriter printWriter;

        public WrappedPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        @Override
        public PrintStreamOrWriter append(Object o) {
            printWriter.print(o);
            return this;
        }
    }
}
