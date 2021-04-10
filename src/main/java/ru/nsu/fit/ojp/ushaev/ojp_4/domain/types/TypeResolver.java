package ru.nsu.fit.ojp.ushaev.ojp_4.domain.types;

public class TypeResolver {

    public static Type typeFromValue(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return SupportedTypes.STRING;
        }

        return SupportedTypes.INT;
    }
}
