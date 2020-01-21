package com.tvzs.lzw;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Common {

    public static int getBitLength(List<String> strings) {
        return (int) Math.ceil(Math.log(strings.size()) / Math.log(2));
    }

    public static String getCodeFromOutput(List<String> output, int bitsNeeded) {
        StringBuilder code = new StringBuilder();
        for (String s : output) {
            String binaryNoLeadingZero = Integer.toBinaryString(Integer.parseInt(s));
            code.append(StringUtils.leftPad(binaryNoLeadingZero, bitsNeeded, '0'));
        }
        return code.toString();
    }

}
