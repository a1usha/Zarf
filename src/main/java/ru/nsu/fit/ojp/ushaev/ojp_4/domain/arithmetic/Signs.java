package ru.nsu.fit.ojp.ushaev.ojp_4.domain.arithmetic;

import org.objectweb.asm.Opcodes;

import java.util.Arrays;

public enum Signs {

    EQ("==", Opcodes.IF_ICMPEQ),
    NEQ("!=", Opcodes.IF_ICMPNE),
    LE("<",Opcodes.IF_ICMPLT),
    GE(">",Opcodes.IF_ICMPGT),
    LEQ("<=",Opcodes.IF_ICMPLE),
    GEQ(">=",Opcodes.IF_ICMPGE);

    private final String sign;
    //reversed. '>' evalutes to le -> less or equal
    private final int opcode;

    Signs(String s, int opcode) {
        sign = s;
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }

    public static Signs fromString(String sign) {
        return Arrays.stream(values()).filter(cmpSign -> cmpSign.sign.equals(sign))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sign not implemented"));
    }
}
