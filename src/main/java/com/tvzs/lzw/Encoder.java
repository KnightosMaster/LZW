package com.tvzs.lzw;

import java.util.Arrays;

public abstract class Encoder {
    protected final double UTF_CHAR_SIZE = 16;
    protected String encodedString;
    protected int encodedBitLength;
    protected String[] alphabet;
    protected int inputLength;

    public abstract String getDecodableString();

    public String[] getAlphabet() {
        return alphabet;
    }

    public double getEfficiency() {
        double inputSize = inputLength * UTF_CHAR_SIZE;
        int outputSize = encodedString.length() * encodedBitLength;
        return (outputSize / inputSize) * 100;
    }

    //This initializes the alphabet from the string
    public void encryptWithoutAlphabet(String input) {
        String[] alphabet = Arrays.stream(input.split(""))
                .sorted()
                .distinct()
                .toArray(String[]::new);
        encryptWithAlphabet(input, alphabet);
    }

    public void encryptWithAlphabet(String input, String... alphabet) {
        if (input.isEmpty() || alphabet.length == 0) {
            System.out.println("Why would you do that?");
            return;
        }
        this.alphabet = alphabet;
        encrypt(input);
    }

    private void encrypt(String input) {
        cleanUp();
        initializeFields(input);
        doTheEncryption();
        printTheOutput();
    }

    protected abstract void cleanUp();

    protected abstract void initializeFields(String input);

    protected abstract void doTheEncryption();

    protected abstract void printTheOutput();
}
