package ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage;

public class Flag {

    private final String name;

    public Flag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "name='" + name + '\'' +
                '}';
    }
}
