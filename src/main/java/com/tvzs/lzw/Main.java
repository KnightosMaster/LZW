package com.tvzs.lzw;

public class Main {

    private static final LZWEncoder LZW_ENCODER = new LZWEncoder();
    private static final LZWDecoder LZW_DECODER = new LZWDecoder();

    public static void main(String[] args) {
        encryptAndDecrypt("ababcababa");
    }

    private static void encryptAndDecrypt(String input) {
        long lzwStartTime = System.nanoTime();
        LZW_ENCODER.encryptWithoutAlphabet(input);
        long lzwMidTime = System.nanoTime();
        LZW_DECODER.decode(LZW_ENCODER.getDecodableString(), LZW_ENCODER.getAlphabet());
        long lzwEndTime = System.nanoTime();
        System.out.println("==LZW==\n" +
                "Encryption took " + (lzwMidTime - lzwStartTime)/1e6 + " milliseconds\n" +
                "Decoding took " + (lzwEndTime - lzwMidTime)/1e6 + " milliseconds\n" +
                "Compressed/original ratio is " + LZW_ENCODER.getEfficiency() + "%\n");
    }

}
