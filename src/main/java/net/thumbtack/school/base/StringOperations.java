package net.thumbtack.school.base;

import java.util.Arrays;

public class StringOperations {
    public static int getSummaryLength(String[] strings) {
        int result = 0;
        for (String temp : strings) {
            result = result + temp.length();
        }
        return result;
    }

    public static String getFirstAndLastLetterString(String string) {
        return "" + string.charAt(0) + string.charAt(string.length() - 1);

    }

    public static boolean isSameCharAtPosition(String string1, String string2, int index) {
        // REVU и здесь и далее
        return (string1.charAt(index) == string2.charAt(index));
    }

    public static boolean isSameFirstCharPosition(String string1, String string2, char character) {
        return string1.indexOf(character, 0) == string2.indexOf(character, 0);
    }

    public static boolean isSameLastCharPosition(String string1, String string2, char character) {
        return string1.lastIndexOf(character, string1.length() - 1) == string2.lastIndexOf(character, string2.length() - 1);
    }

    public static boolean isSameFirstStringPosition(String string1, String string2, String str) {
        return string1.indexOf(str, 0) == string2.indexOf(str, 0);
    }

    public static boolean isSameLastStringPosition(String string1, String string2, String str) {
        return string1.lastIndexOf(str, string1.length() - 1)
                ==
                string2.lastIndexOf(str, string2.length() - 1);
    }

    public static boolean isEqual(String string1, String string2) {
        return string1.equals(string2);
    }

    public static boolean isEqualIgnoreCase(String string1, String string2) {
        return string1.equalsIgnoreCase(string2);
    }

    public static boolean isLess(String string1, String string2) {
        return string1.compareTo(string2) < 0;
    }

    public static boolean isLessIgnoreCase(String string1, String string2) {
        return string1.compareToIgnoreCase(string2) < 0;
    }

    public static String concat(String string1, String string2) {
        return string1 + string2;
    }

    public static boolean isSamePrefix(String string1, String string2, String prefix) {
        return (string1.indexOf(prefix) == 0 && string2.indexOf(prefix) == 0);
    }

    public static boolean isSameSuffix(String string1, String string2, String suffix) {
        return (string1.endsWith(suffix)
                &&
                string2.endsWith(suffix));
    }

    public static String getCommonPrefix(String string1, String string2) {
        int m = Math.min(string1.length(), string2.length());
        String res;
        if (string1.length() < string2.length()) res = string1;
        else res = string2;
        for (int i = 0; i < m; i++) {
            if (string1.charAt(i) != string2.charAt(i)) return string1.substring(0, i);
        }
        return res;
    }

    public static String reverse(String string) {
        return new StringBuilder(string).reverse().toString();
    }


    public static boolean isPalindrome(String string) {
        StringBuilder result = new StringBuilder();
        char[] plain = string.toCharArray();
        for (int i = plain.length - 1; i >= 0; i--) {
            result.append(plain[i]);
        }
        return (result.toString()).equals(string);

    }

    public static boolean isPalindromeIgnoreCase(String string) {
        String cl = string.toLowerCase();
        StringBuilder result = new StringBuilder();
        char[] plain = cl.toCharArray();
        for (int i = plain.length - 1; i >= 0; i--) {
            result.append(plain[i]);
        }
        return (result.toString()).equals(cl);
    }

    public static String getLongestPalindromeIgnoreCase(String[] strings) {

        String result = "";
        for (String counter : strings) {
            if (StringOperations.isPalindromeIgnoreCase(counter) &&
                    counter.length() > result.length()) {
                result = counter;
            }
        }
        return result;
    }

    public static boolean hasSameSubstring(String string1, String string2, int index, int length) {
        int end = index + length;
        if (end > string1.length() || end > string2.length()) return false;
        return string1.substring(index, index + length).equals(string2.substring(index, index + length));
    }

    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1,
                                                        char replaceByInStr1, String string2,
                                                        char replaceInStr2, char replaceByInStr2) {
        return string1.replace(replaceInStr1, replaceByInStr1).
                equals(string2.replace(replaceInStr2, replaceByInStr2));
    }

    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1,
                                                     String replaceByInStr1, String string2,
                                                     String replaceInStr2, String replaceByInStr2) {
        return string1.replaceAll(replaceInStr1, replaceByInStr1).
                equals(string2.replaceAll(replaceInStr2, replaceByInStr2));
    }

    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string) {
        return StringOperations.isPalindromeIgnoreCase(string.replace(" ", ""));
    }

    public static boolean isEqualAfterTrimming(String string1, String string2) {
        return string1.trim().equals(string2.trim());
    }

    public static String makeCsvStringFromInts(int[] array) {
        StringBuilder result = new StringBuilder(Arrays.toString(array));
        result.deleteCharAt(0);
        result.deleteCharAt(result.length() - 1);
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == ' ') result.deleteCharAt(i);
        }
        return result.toString();

    }

    public static String makeCsvStringFromDoubles(double[] array) {
        StringBuilder result = new StringBuilder();
        if (array.length != 0) {
            result.append(String.format("%.2f", array[0]));
        }
        for (int i = 1; i < array.length; i++) {
            result.append(",");
            result.append(String.format("%.2f", array[i]));

        }
        return result.toString();
    }

    public static StringBuilder makeCsvStringBuilderFromInts(int[] array) {
        return new StringBuilder(StringOperations.makeCsvStringFromInts(array));
    }

    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array) {
        return new StringBuilder(StringOperations.makeCsvStringFromDoubles(array));
    }

    public static StringBuilder removeCharacters(String string, int[] positions) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = positions.length - 1; i != -1; i--) {
            sb.deleteCharAt(positions[i]);
        }
        return sb;
    }

    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = positions.length - 1; i != -1; i--) {
            sb.insert(positions[i], characters[i]);
        }
        return sb;
    }
}


