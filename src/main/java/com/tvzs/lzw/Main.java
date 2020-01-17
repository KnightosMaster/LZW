package com.tvzs.lzw;

public class Main {

    public static void main(String[] args) {
        LZWEncryptor encryptor = new LZWEncryptor();
        encryptor.encryptWithoutAlphabet("ababcababa");
    }

}
