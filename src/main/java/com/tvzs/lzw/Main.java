package com.tvzs.lzw;

public class Main {

    private static final LZWEncoder LZW_ENCODER = new LZWEncoder();
    private static final LZWDecoder LZW_DECODER = new LZWDecoder();
    private static final NaiveEncoder NAIVE_ENCODER = new NaiveEncoder();

    public static void main(String[] args) {
        encryptAndDecrypt("ababcababa");
    }

    private static void encryptAndDecrypt(String input) {
        System.out.println("Begin LZW\n");
        long lzwStartTime = System.nanoTime();
        LZW_ENCODER.encryptWithoutAlphabet(input);
        long lzwMidTime = System.nanoTime();
        LZW_DECODER.decode(LZW_ENCODER.getDecodableString(), LZW_ENCODER.getAlphabet());
        long lzwEndTime = System.nanoTime();
        System.out.println("LZW finished, now comes the Naive\n");
        long naiveStartTime = System.nanoTime();
        NAIVE_ENCODER.encryptWithoutAlphabet(input);
        long naiveMidTime = System.nanoTime();
        long naiveEndTime = System.nanoTime();
        System.out.println("Run complete, now for the stats:\n" +
                "==LZW==\n" +
                "Encryption took " + (lzwMidTime - lzwStartTime)/1e6 + " milliseconds\n" +
                "Decoding took " + (lzwEndTime - lzwMidTime)/1e6 + " milliseconds\n" +
                "Compressed/original ratio is " + LZW_ENCODER.getEfficiency() + "%\n" +
                "==NAIVE==\n" +
                "Encryption took " + (naiveMidTime - naiveStartTime)/1e6 + " milliseconds\n" +
                "Decoding took " + (naiveEndTime - naiveMidTime)/1e6 + " milliseconds\n" +
                "Compressed/original ratio is " + NAIVE_ENCODER.getEfficiency() + "%\n");
    }

}
