package de.hglabor.kitapi.registry;

public class MalformedIdentifierException extends Exception {

    private final String identifier;
    private final int index;

    public MalformedIdentifierException(String identifier, int index) {
        super("Illegal character in string \"" + identifier + "\" at index " + index + "");
        this.identifier = identifier;
        this.index = index;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder(super.getMessage());
        builder.append("\n \n");
        builder.append(identifier);
        builder.append("\n");
        for (int i = 0; i < index; i++) {
            builder.append(" ");
        }
        builder.append("^");
        builder.append("\n");
        return builder.toString();
    }
}
