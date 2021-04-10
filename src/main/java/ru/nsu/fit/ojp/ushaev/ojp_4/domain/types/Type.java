package ru.nsu.fit.ojp.ushaev.ojp_4.domain.types;

public interface Type {
    String getName();
    String getDescriptor();
    Class<?> getTypeClass();
}
