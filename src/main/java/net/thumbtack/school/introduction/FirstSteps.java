package net.thumbtack.school.introduction;

import java.util.Arrays;

public class FirstSteps {
    public int sum(int x, int y) {
        return x + y;
    }

    public int mul(int x, int y) {
        return x * y;
    }

    public int div(int x, int y) {
        return x / y;
    }

    public int mod(int x, int y) {
        return x % y;
    }

    public boolean isEqual(int x, int y) {
        return x == y;
    }

    public boolean isGreater(int x, int y) {
        return x > y;
    }

    public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y) {
        // REVU лишние скобки
        return x <= xRight && x >= xLeft && y <= yBottom && y >= yTop;
    }

    public int sum(int[] array) {
        int sum = 0;
        for (int count : array) {
            sum += count;
        }
        return sum;
    }

    public int min(int[] array) {
        int result = Integer.MAX_VALUE;
        for (int count : array) {
            if (count < result)
                result = count;
        }
        return result;
    }

    public int mul(int[] array) {
        int result = 1;
        if (array.length == 0) return 0;
        for (int count : array) {
            result *= count;
        }
        return result;
    }


    public int max(int[] array) {
        int result = Integer.MIN_VALUE;
        for (int count : array) {
            if (count > result)
                result = count;
        }
        return result;
    }

    public double average(int[] array) {
        if (array.length == 0) return 0;
        double sum = sum(array);
        return sum / array.length;
    }

    public boolean isSortedDescendant(int[] array) {
        if (array.length < 2) return true;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] <= array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public void cube(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] *= array[i] * array[i];
        }
    }

    public boolean find(int[] array, int value) {
        for (int count : array) {
            if (count == value) return true;
        }
        return false;
    }

    public void reverse(int[] array) {
        for (int i = 0; i != array.length / 2; i++) {
            int num = array[array.length - i - 1];
            array[array.length - i - 1] = array[i];
            array[i] = num;
        }
    }

    public boolean isPalindrome(int[] array) {
        for (int i = 0; i != array.length / 2; i++) {
            if (array[array.length - i - 1] != array[i])
                return false;
        }
        return true;
    }

    public int sum(int[][] matrix) {
        int sum = 0;
        for (int[] counter : matrix)
            sum += sum(counter);
        return sum;
    }

    public int max(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int[] counter : matrix)
            if (max(counter) > max)
                max = max(counter);
        return max;
    }

    public int diagonalMax(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length && matrix[i].length != 0; i++)
            if (matrix[i][i] > max)
                max = matrix[i][i];

        return max;
    }

    public boolean isSortedDescendant(int[][] matrix) {
        for (int[] counter : matrix)
            if (!isSortedDescendant(counter)) return false;
        return true;
    }


}



