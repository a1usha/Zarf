package ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage;

import ru.nsu.fit.ojp.ushaev.ojp_4.domain.types.Type;

public class Variable {

    private final String name;
    private final Type type;

    public Variable(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
