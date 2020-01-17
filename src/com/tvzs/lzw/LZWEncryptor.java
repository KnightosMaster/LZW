package com.tvzs.lzw;

import java.util.*;

public class LZWEncryptor {
    private List<String> codeTable;
    private List<String> output;
    private LinkedList<String> charStream;
    private String charBuffer;
    private String previous;
    private String current;

    public LZWEncryptor() {
        codeTable = new ArrayList<>();
        output = new LinkedList<>();
    }

    public String encrypt(String data, String... alphabet) {
        if (data.isEmpty() || alphabet.length == 0) {
            return "Why would you do that?";
        }
        cleanUp();
        initializeFields(data, alphabet);
        while (!charStream.isEmpty()) {
            if (codeTable.contains(current)) {
                readNextCharacterAndUpdateTheOthers();
            } else {
                addStringToTableAndAddThePreviousStringToOutput();
            }
        }
        output.add(getCodeOfString(current));
        System.out.println("Output is: " + output);
        return "";
    }

    private void cleanUp() {
        codeTable.clear();
        output.clear();
    }

    private void initializeFields(String data, String[] alphabet) {
        Collections.addAll(codeTable, alphabet);
        System.out.println("Code table initialized as: " + codeTable);
        charStream = new LinkedList<>(Arrays.asList(data.split("")));
        charBuffer = charStream.pop();
        previous = "";
        current = String.valueOf(charBuffer);
        System.out.println("Initial reading done, c is " + charBuffer + "\n" +
                "curr is " + current + "\n" +
                "Now come de LØØP\n");
    }

    private String getCodeOfString(String s) {
        return String.valueOf(codeTable.indexOf(s));
    }

    private void readNextCharacterAndUpdateTheOthers() {
        charBuffer = charStream.pop();
        previous = current;
        current = previous + charBuffer;
        System.out.println("Code table already contains " + previous + "\n" +
                "Reading new character c: " + charBuffer + "\n" +
                "curr is now " + current + "\n");
    }

    private void addStringToTableAndAddThePreviousStringToOutput() {
        String codeOfPrev = getCodeOfString(previous);
        output.add(codeOfPrev);
        codeTable.add(current);
        System.out.println(previous + " (code: " + codeOfPrev + ") added to output\n" +
                current + " added to code table\n" +
                "curr is now " + charBuffer + "\n" +
                "Code table is: " + codeTable + "\n");
        current = charBuffer;
    }
}
