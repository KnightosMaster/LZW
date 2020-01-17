package com.tvzs.lzw;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        LZWEncryptor encryptor = new LZWEncryptor();
        encryptor.encrypt("ababcababa", "a", "b", "c");
    }

}
