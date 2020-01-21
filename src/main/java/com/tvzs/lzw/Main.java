package com.tvzs.lzw;

public class Main {

    public static void main(String[] args) {
        LZWEncryptor encryptor = new LZWEncryptor();
        encryptor.encryptWithoutAlphabet("ababcababa");
        LZWDecryptor decryptor = new LZWDecryptor();
        String[] alphabet = {"a", "b", "c"};
        decryptor.decrypt("3 000001011010011111", alphabet);
    }

}
