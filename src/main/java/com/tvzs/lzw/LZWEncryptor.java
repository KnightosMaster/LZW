package com.tvzs.lzw;

import org.apache.commons.lang3.StringUtils;

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

    //This initializes the alphabet from the string
    public void encryptWithoutAlphabet(String data) {
        String[] alphabet = Arrays.stream(data.split(""))
                .sorted()
                .distinct()
                .toArray(String[]::new);
        encryptWithAlphabet(data, alphabet);
    }

    public void encryptWithAlphabet(String data, String... alphabet) {
        if (data.isEmpty() || alphabet.length == 0) {
            System.out.println("Why would you do that?");
            return;
        }
        encrypt(data, alphabet);
    }

    private void encrypt(String data, String[] alphabet) {
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
        printTheOutput();
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

    private void printTheOutput() {
        System.out.println("Code table is: " + codeTable);
        System.out.println("Raw output is: " + output);
        //Due to floating point fuckery this may not always be correct
        int bitsNeeded = (int) Math.ceil(Math.log(output.size()) / Math.log(2));
        String code = getCodeFromOutput(bitsNeeded);
        System.out.println("Bits needed: " + bitsNeeded + "\n" +
                "Encoded string: " + code);
    }

    private String getCodeFromOutput(int bitsNeeded) {
        StringBuilder code = new StringBuilder();
        for (String s: output) {
            String binaryNoLeadingZero = Integer.toBinaryString(Integer.parseInt(s));
            code.append(StringUtils.leftPad(binaryNoLeadingZero, bitsNeeded, '0'));
        }
        return code.toString();
    }

}
