package ru.nsu.fit.ojp.ushaev.ojp_4.domain.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class VariableStorage {

    private final List<Variable> variables = new ArrayList<>();

    public VariableStorage() {
    }

    public VariableStorage(VariableStorage variableStorage) {
        this.variables.addAll(variableStorage.getAllVariables());
    }

    public List<Variable> getAllVariables() {
        return variables;
    }

    public void addVariable(Variable variable) {

        for (Variable var : variables) {
            if (var.getName().equals(variable.getName())) {
                throw new IllegalArgumentException("Variable is already defined: " + variable.getName());
            }
        }

        variables.add(variable);
    }

    public Variable getVariable(String name) {
        return variables.stream()
                .filter(variable -> variable.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No variable found with name: " + name));
    }

    public void removeVariable(String name) {
        Predicate<Variable> isEquals = variable -> variable.getName().equals(name);
        variables.removeIf(isEquals);
    }

    public int getVariableIndex(String name) {
        return variables.indexOf(getVariable(name));
    }
}
