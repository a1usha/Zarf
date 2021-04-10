package ru.nsu.fit.ojp.ushaev.ojp_4.domain.types;

public enum SupportedTypes implements Type {

    INT("int", int.class, "I"),
    STRING("string", String.class, "Ljava/lang/String;"),
    BOOL("bool",boolean.class,"Z"),;

    private final String name;
    private final Class<?> typeClass;
    private final String descriptor;

    SupportedTypes(String name, Class<?> typeClass, String descriptor) {
        this.name = name;
        this.typeClass = typeClass;
        this.descriptor = descriptor;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescriptor() {
        return descriptor;
    }

    @Override
    public Class<?> getTypeClass() {
        return typeClass;
    }
}
