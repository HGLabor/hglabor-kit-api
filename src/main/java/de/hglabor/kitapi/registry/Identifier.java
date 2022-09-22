package de.hglabor.kitapi.registry;

import java.util.List;

public class Identifier {

    public static final String DEFAULT_NAMESPACE = "kitapi";
    private static final List<Character> ALLOWED_CHARACTERS = List.of(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
        '5', '5', '6', '7', '8', '9', '-', '_'
    );

    private final String namespace;
    private final String path;

    public Identifier(String identifier) {
        if (!identifier.contains(":")) {
            this.namespace = "kitapi";
            validate(identifier);
            this.path = identifier;
        } else {
            String[] split = identifier.split(":");
            validate(split[0]);
            validate(split[1]);
            this.namespace = split[0];
            this.path = split[1];
        }
    }

    public Identifier(String namespace, String path) {
        validate(namespace);
        validate(path);
        this.namespace = namespace;
        this.path = path;
    }

    private boolean validate(String string) {
        int index = 0;
        for (char c : string.toLowerCase().toCharArray()) {
            if (!ALLOWED_CHARACTERS.contains(c)) {
                try {
                    throw new MalformedIdentifierException(string, index);
                } catch (MalformedIdentifierException e) {
                    throw new RuntimeException(e);
                }
            }
            index++;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Identifier && ((Identifier) obj).namespace == this.namespace && ((Identifier) obj).path == this.path;
    }

    @Override
    protected Object clone()  {
        return new Identifier(namespace, path);
    }

    @Override
    public String toString() {
        return namespace + ":" + path;
    }
}
