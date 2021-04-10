package ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage;

import java.util.ArrayList;
import java.util.List;

public class FlagStorage {

    private final List<Flag> flags = new ArrayList<>();

    public List<Flag> getAllFlags() {
        return flags;
    }

    public void addFlag(Flag flag) {
        flags.add(flag);
    }

    public Flag getFlag(String name) {
        return flags.stream()
                .filter(variable -> variable.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No flag found with name: " + name));
    }


}
