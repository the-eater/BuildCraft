/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.expression;

import java.util.ArrayList;
import java.util.List;

import buildcraft.lib.expression.Tokenizer.ITokenizerGobbler;
import buildcraft.lib.expression.Tokenizer.ResultConsume;
import buildcraft.lib.expression.Tokenizer.ResultDiscard;
import buildcraft.lib.expression.Tokenizer.ResultInvalid;
import buildcraft.lib.expression.Tokenizer.ResultSpecific;
import buildcraft.lib.expression.api.NodeTypes;

public class TokenizerDefaults {
    // Lots of gobblers.
    // They like to "gobble" up bits of text.
    public static final ITokenizerGobbler GOBBLER_QUOTE = (ctx) -> {
        // Quoted gobbler, takes a length of text in quotes. Does NOT allow EOL.
        int length = 1;
        char type = ctx.getCharAt(0);
        if (type != '\'' && type != '"') return ResultSpecific.IGNORE;
        while (true) {
            char c = ctx.getCharAt(length);
            if (c == '\\') {
                length++;
            } else if (c == type) {
                return new ResultConsume(length + 1);
            } else if (c == Tokenizer.END_OF_LINE) {
                return new ResultInvalid(length + 1);
            }
            length++;
        }
    };

    private static final String[] MATH_OPS_2_CHAR = { "||", "&&", "<=", ">=", "==", "!=", "<<", ">>" };
    public static final ITokenizerGobbler GOBBLER_MATH_OPERATOR = (ctx) -> {
        String possible = ctx.get(2);
        for (String s : MATH_OPS_2_CHAR) {
            if (s.equals(possible)) {
                return ResultConsume.TWO;
            }
        }
        return ResultSpecific.IGNORE;
    };
    public static final ITokenizerGobbler GOBBLER_HEXADECIMAL = (ctx) -> {
        if ("0x".equals(ctx.get(2))) {
            int size = 2;
            while (true) {
                char c = ctx.getCharAt(size);
                if ('_' == c || (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
                    size++;
                } else {
                    break;
                }
            }
            if (size > 2) {
                return new ResultConsume(size);
            } else {
                return ResultSpecific.IGNORE;
            }
        } else {
            return ResultSpecific.IGNORE;
        }
    };
    public static final ITokenizerGobbler GOBBLER_NUMBER = (ctx) -> {
        int i = 0;
        int dot = -1;
        while (true) {
            char c = ctx.getCharAt(i);
            if (c == '.') {
                if (dot >= 0) {
                    break;
                }
                dot = i;
            } else {
                if (!Character.isDigit(c)) break;
            }
            i++;
        }
        if (i == 0) return ResultSpecific.IGNORE;
        boolean digitsBeforeDot = dot > 0;
        boolean digitsAfterDot = i > dot + 1;

        if (digitsBeforeDot) {
            if (digitsAfterDot) {
                return new ResultConsume(i);
            } else {
                return new ResultConsume(i - 1);
            }
        } else if (digitsAfterDot) {
            return new ResultConsume(i);
        } else {
            return ResultSpecific.IGNORE;
        }
    };
    public static final ITokenizerGobbler GOBBLER_WORD = (ctx) -> {
        /*
         * Allow words to start with ".", so that object method calling works. (for example "5.sub(6)", where ".sub" is
         * treated as a single token)
         * 
         * Allow words to contain "." so that full names work.
         * 
         * Except in the case where "engine.stage.toLowerCase()" -- only take "engine.stage"
         * 
         * I have no idea how this will work in regards to object properties... although they aren't implemented yet
         */
        int firstDot = Integer.MAX_VALUE;
        int lastDot = -1;
        int i = 0;
        for (;; i++) {
            char c = ctx.getCharAt(i);
            if (c == '.') {
                firstDot = Math.min(i, firstDot);
                lastDot = i;
            } else if (c == '(') {
                if (lastDot == -1) {
                    break;
                } else if (lastDot == 0) {
                    break;
                } else if (NodeTypes.getType(ctx.get(0, firstDot + 1)) == null) {
                    if (firstDot != lastDot) {
                        i = lastDot;
                    }
                    break;
                } else {
                    break;
                }
            } else if (!Character.isJavaIdentifierPart(c)) {
                break;
            }
        }
        return i == 0 ? ResultSpecific.IGNORE : new ResultConsume(i);
    };
    public static final ITokenizerGobbler GOBBLER_NON_WHITESPACE = (ctx) -> {
        // Non-special char gobbler, takes a single of not-EOL and not-EOF and not-whitespace
        char c = ctx.getCharAt(0);
        if (c == Tokenizer.END_OF_LINE) {
            return ResultSpecific.IGNORE;
        }
        if (Character.isWhitespace(c)) {
            return ResultSpecific.IGNORE;
        }
        return ResultConsume.ONE;
    };
    public static final ITokenizerGobbler GOBBLER_DISCARD = (ctx) -> ResultDiscard.SINGLE;

    public static List<ITokenizerGobbler> createParts() {
        List<ITokenizerGobbler> list = new ArrayList<>();
        list.add(GOBBLER_QUOTE);
        list.add(GOBBLER_MATH_OPERATOR);
        list.add(GOBBLER_HEXADECIMAL);
        list.add(GOBBLER_NUMBER);
        list.add(GOBBLER_WORD);
        list.add(GOBBLER_NON_WHITESPACE);
        list.add(GOBBLER_DISCARD);
        return list;
    }

    public static Tokenizer createTokenizer() {
        return new Tokenizer(createParts());
    }
}
