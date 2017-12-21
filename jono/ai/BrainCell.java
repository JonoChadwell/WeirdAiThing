package jono.ai;

import java.util.Random;

public class BrainCell extends Cell {

    private static final int IO_MEM_MASK = 0xFF;
    private static final int INTERNAL_MEM_MASK = 0xFFF;
    private static final int PROGRAM_MEM_MASK = 0xFFF;

    private int[] inputMemory = new int[IO_MEM_MASK + 1];
    private int[] outputMemory = new int[IO_MEM_MASK + 1];
    private int[] internalMemory = new int[INTERNAL_MEM_MASK + 1];
    private int[] programMemory = new int[PROGRAM_MEM_MASK + 1];

    private int programCounter = 0;

    public BrainCell(long seed) {
        Random r = new Random(seed);
        for (int i = 0; i <= IO_MEM_MASK; i++) {
            inputMemory[i] = r.nextInt();
            outputMemory[i] = r.nextInt();
        }

        for (int i = 0; i <= INTERNAL_MEM_MASK; i++) {
            internalMemory[i] = r.nextInt();
        }

        for (int i = 0; i <= PROGRAM_MEM_MASK; i++) {
            programMemory[i] = r.nextInt();
        }
    }

    @Override
    public int readValue(Cell dest) {
        return outputMemory[dest.hashCode() & IO_MEM_MASK];
    }

    @Override
    public void writeValue(Cell source, int value) {
        inputMemory[source.hashCode() & IO_MEM_MASK] = value;
    }

    private void executeInstruction() {
        programCounter &= PROGRAM_MEM_MASK;
        int instruction = programMemory[programCounter];
        int opcode =    (instruction & 0xF0000000) >>> 28;
        int source =    (instruction & 0x0FFF0000) >>> 16;
        int immediate =  instruction & 0x0000FFFF;
        int dest =      (instruction & 0x0000FFF0) >> 4;
        int mathop =     instruction & 0x0000000F;
        programCounter++;

        if (opcode == 0x0) { // no-op
            return;
        } else if (opcode == 0x1) { // move
            internalMemory[dest] = internalMemory[source];
        } else if (opcode == 0x2) { // load upper immediate
            internalMemory[dest] = immediate << 16;
        } else if (opcode == 0x3) { // load lower immediate
            internalMemory[dest] = immediate;
        } else if (opcode == 0x4) { // goto
            programCounter = immediate;
        } else if (opcode == 0x5) { // goto memory
            programCounter = internalMemory[source];
        } else if (opcode == 0x6) { // goto shift
            programCounter += immediate;
        } else if (opcode == 0x7) { // pass io
            outputMemory[IO_MEM_MASK & dest] = inputMemory[IO_MEM_MASK & source];
        } else if (opcode == 0x8) { // output value
            outputMemory[IO_MEM_MASK & dest] = internalMemory[source];
        } else if (opcode == 0x9) { // output lower immediate
            outputMemory[IO_MEM_MASK & dest] = immediate;
        } else if (opcode == 0xA) { // output upper immediate
            outputMemory[IO_MEM_MASK & dest] = immediate << 16;
        } else if (opcode == 0xB) { // input value
            internalMemory[dest] = inputMemory[IO_MEM_MASK & source];
        } else if (opcode == 0xC) { // unused
            return;
        } else if (opcode == 0xD) { // unused
            return;
        } else if (opcode == 0xE) { // if
            boolean skip;
            if (mathop == 0x0) { // a
                skip = internalMemory[dest] == 0;
            } else if (mathop == 0x1) { // b
                skip = internalMemory[source] == 0;
            } else if (mathop == 0x2) { // a == b
                skip = internalMemory[dest] == internalMemory[source];
            } else if (mathop == 0x3) { // a < b
                skip = internalMemory[dest] < internalMemory[source];
            } else if (mathop == 0x4) { // a <= b
                skip = internalMemory[dest] <= internalMemory[source];
            } else if (mathop == 0x5) { // a > b
                skip = internalMemory[dest] > internalMemory[source];
            } else if (mathop == 0x6) { // a >= b
                skip = internalMemory[dest] >= internalMemory[source];
            } else if (mathop == 0x7) { // a != b
                skip = internalMemory[dest] != internalMemory[source];
            } else if (mathop == 0x8) { // !a
                skip = internalMemory[dest] != 0;
            } else if (mathop == 0x9) { // !b
                skip = internalMemory[dest] != 0;
            } else { // unused
                return;
            }
            if (skip) {
                programCounter++;
            }
        } else if (opcode == 0xF) { // math
            if (mathop == 0x0) { // zero
                internalMemory[dest] = 0;
            } else if (mathop == 0x1) { // add
                internalMemory[dest] += internalMemory[source];
            } else if (mathop == 0x2) { // sub
                internalMemory[dest] -= internalMemory[source];
            } else if (mathop == 0x3) { // mult
                internalMemory[dest] *= internalMemory[source];
            } else if (mathop == 0x4) { // div
                internalMemory[dest] /= internalMemory[source];
            } else if (mathop == 0x5) { // mod
                internalMemory[dest] %= internalMemory[source];
            } else if (mathop == 0x6) { // and
                internalMemory[dest] &= internalMemory[source];
            } else if (mathop == 0x7) { // or
                internalMemory[dest] |= internalMemory[source];
            } else if (mathop == 0x8) { // xor
                internalMemory[dest] ^= internalMemory[source];
            } else if (mathop == 0x9) { // rshift
                internalMemory[dest] >>>= internalMemory[source];
            } else if (mathop == 0xA) { // rshift unsigned
                internalMemory[dest] >>= internalMemory[source];
            } else if (mathop == 0xB) { // lshift
                internalMemory[dest] <<= internalMemory[source];
            } else { // unused
                return;
            }
        }
    }

    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
            executeInstruction();
        }
    }
}
