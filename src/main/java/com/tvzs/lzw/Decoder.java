package com.tvzs.lzw;

public abstract class Decoder {
    public void decode(String input, String[] alphabet) {
        cleanUp();
        initialize(input, alphabet);
        decodeTheInput();
        printTheOutput();
    }

    protected abstract void cleanUp();

    protected abstract void initialize(String input, String[] alphabet);

    protected abstract void decodeTheInput();

    protected abstract void printTheOutput();
}
