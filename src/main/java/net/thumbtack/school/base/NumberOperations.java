package net.thumbtack.school.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class NumberOperations {
    public static Integer find(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) return i;
        }
        return null;
    }

    public static Integer find(double[] array, double value, double eps) {
        for (int i = 0; i < array.length; i++) {
            if (Math.abs(array[i] - value) < 1E-6) return i;
        }
        return null;
    }

    public static Integer find(BigInteger[] array, BigInteger value) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], value)) return i;
        }
        return null;
    }

    public static BigDecimal calculateDensity(BigDecimal weight, BigDecimal volume,
                                              BigDecimal min, BigDecimal max) {
        BigDecimal result = weight.divide(volume);
        if (result.compareTo(min) > 0 && max.compareTo(result) > 0) return result;
        return null;
    }

    public static Double calculateDensity(double weight, double volume,
                                          double min, double max) {
        double result = weight / volume;
        if (result > min && result < max) return result;
        return null;
    }
}
