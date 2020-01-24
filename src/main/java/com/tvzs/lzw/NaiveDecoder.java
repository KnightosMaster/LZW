package com.tvzs.lzw;

import com.google.common.base.Splitter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class NaiveDecoder extends Decoder {

    private LinkedList<Integer> inStream;
    private List<String> alphabet;
    private List<String> output;
    private int encodedBitLength;

    @Override
    protected void cleanUp() {
        //None needed since when starting a new process fields are set to a new value
    }

    @Override
    protected void initialize(String input, String[] alphabet) {
        this.alphabet = Arrays.asList(alphabet);
        encodedBitLength = Common.getBitLengthFromSize(alphabet.length);
        Splitter splitter = Splitter.fixedLength(encodedBitLength);
        inStream = splitter.splitToList(input).stream()
                .map(Common::getIndexFromBitcode)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    protected void decodeTheInput() {
        output = inStream.stream()
                .map(this::getCharacterFromAlphabet)
                .collect(Collectors.toList());
    }

    @Override
    protected void printTheOutput() {
        String out = String.join("", output);
        System.out.println("Alphabet is " + alphabet.toString() + "\n" +
                "Bits needed per character is " + encodedBitLength + " bits\n" +
                "Decoded string is: " + out + "\n");
    }

    private String getCharacterFromAlphabet(Integer i) {
        return alphabet.get(i);
    }
}
