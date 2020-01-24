package com.tvzs.lzw;

import com.google.common.base.Splitter;

import java.util.*;
import java.util.stream.Collectors;

public class LZWDecoder extends Decoder {
    private List<String> codeTable;
    private List<String> output;
    private LinkedList<Integer> inStream;
    private Integer charBuffer;
    private String current;
    private String next;

    public LZWDecoder() {
        codeTable = new ArrayList<>();
        output = new LinkedList<>();
    }

    @Override
    protected void cleanUp() {
        codeTable.clear();
        output.clear();
    }

    @Override
    protected void initialize(String input, String[] alphabet) {
        Collections.addAll(codeTable, alphabet);
        getBitLengthAndCodes(input);
    }

    private void getBitLengthAndCodes(String input) {
        String[] lengthAndData = input.split(" ");
        int length = Integer.parseInt(lengthAndData[0]);
        Splitter splitter = Splitter.fixedLength(length);
        List<String> bitcodeList = splitter.splitToList(lengthAndData[1]);
        inStream = bitcodeList.stream()
                .map(this::getIndexFromBitcode)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Length is " + length + "\n" +
                "List of bitcodes: " + bitcodeList.toString() + "\n" +
                "List of codes: " + inStream.toString() + "\n");
    }

    private int getIndexFromBitcode(String code) {
        return Integer.parseInt(code, 2);
    }

    @Override
    protected void decodeTheInput() {
        decodeFirstCharacter();
        readSecondCharacterToBuffer();
        boolean shouldRun = true;
        while (shouldRun) {
            getNextAndAddItToOutput();
            addDebtToCodeTableAndProceed();
            if (inStream.isEmpty()) {
                shouldRun = false;
            } else {
                readNextCharacter();
            }
        }
    }

    private void decodeFirstCharacter() {
        charBuffer = inStream.pop();
        current = codeTable.get(charBuffer);
        output.add(current);
        System.out.println("First code is " + charBuffer + " which corresponds to " + current);
    }

    private void readSecondCharacterToBuffer() {
        charBuffer = inStream.pop();
        System.out.println("Second code is " + charBuffer);
    }

    private void getNextAndAddItToOutput() {
        if (isCharBufferInCodeTable()) {
            next = codeTable.get(charBuffer);
            System.out.print("charBuffer is in codeTable, next is ");
        } else {
            next = current + getFirstCharOf(current);
            System.out.print("charBuffer is NOT in codeTable, next is ");
        }
        System.out.println(next);
        output.add(next);
    }

    private void addDebtToCodeTableAndProceed() {
        String stringDebt = current + getFirstCharOf(next);
        codeTable.add(stringDebt);
        current = next;
        System.out.println(stringDebt + " added to code table\n" +
                "current is now: " + current + "\n");
    }

    private boolean isCharBufferInCodeTable() {
        return charBuffer < codeTable.size();
    }

    private char getFirstCharOf(String string) {
        return string.charAt(0);
    }

    private void readNextCharacter() {
        charBuffer = inStream.pop();
        System.out.println("Reading next character, charBuffer is: " + charBuffer);
    }

    @Override
    protected void printTheOutput() {
        System.out.println("Final code table: " + codeTable.toString() + "\n" +
                "Final output: " + output.toString() + "\n");
    }
}
