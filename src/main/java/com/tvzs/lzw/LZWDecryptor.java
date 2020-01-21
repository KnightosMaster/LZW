package com.tvzs.lzw;

import java.util.*;

public class LZWDecryptor {
    private List<String> codeTable;
    private List<String> output;
    private LinkedList<String> charStream;
    private String charBuffer;
    private String next;
    private String current;

    public LZWDecryptor() {
        codeTable = new ArrayList<>();
        output = new LinkedList<>();
    }

    public void decrypt(String data, String[] alphabet) {
        cleanUp();
        Collections.addAll(codeTable, alphabet);
        charStream = new LinkedList<>(Arrays.asList(data.split("")));
        charBuffer = charStream.pop();
    }

    private void cleanUp() {
        codeTable.clear();
        output.clear();
    }

}
