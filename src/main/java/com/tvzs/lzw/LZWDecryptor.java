package com.tvzs.lzw;

import com.google.common.base.Splitter;

import java.util.*;
import java.util.stream.Collectors;

public class LZWDecryptor {
    private List<String> codeTable;
    private List<String> output;
    private LinkedList<Integer> codes;
    private Integer charBuffer;
    private String next;
    private String current;
    private String input;

    public LZWDecryptor() {
        codeTable = new ArrayList<>();
        output = new LinkedList<>();
    }

    public void decrypt(String input, String[] alphabet) {
        cleanUp();
        this.input = input;
        initialize(alphabet);
        System.out.println("Decryptor cleaned up and initialized\n");
        charBuffer = codes.pop();
        current = codeTable.get(charBuffer);
        output.add(current);
        charBuffer = codes.pop();
    }

    private void cleanUp() {
        codeTable.clear();
        output.clear();
    }

    private void initialize(String[] alphabet) {
        Collections.addAll(codeTable, alphabet);
        getBitLengthAndCodes();
    }

    private void getBitLengthAndCodes() {
        String[] lengthAndData = input.split(" ");
        int length = Integer.parseInt(lengthAndData[0]);
        Splitter splitter = Splitter.fixedLength(length);
        List<String> bitcodeList = splitter.splitToList(lengthAndData[1]);
        codes = bitcodeList.stream()
                .map(this::getIndexFromBitcode)
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("Length is " + length + "\n" +
                "List of bitcodes: " + bitcodeList.toString() + "\n");
    }

    private int getIndexFromBitcode(String code) {
        return Integer.parseInt(code, 2);
    }

}
