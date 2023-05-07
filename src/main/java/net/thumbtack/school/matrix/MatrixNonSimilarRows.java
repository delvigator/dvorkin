package net.thumbtack.school.matrix;

import java.util.*;

public class MatrixNonSimilarRows {
    private int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }

    public Set<int[]> getNonSimilarRows() {
        // REVU не нужен
        Set<Set<Integer>> set = new HashSet<>();
        Map<Set<Integer>, int[]> matrixMap = new HashMap<>();
        for (int[] counter : matrix) {
            Set<Integer> row = new HashSet<>();
            for (int elem : counter)
                row.add(elem);
            matrixMap.putIfAbsent(row, counter);
        }

        return new HashSet<>(matrixMap.values());
    }
}

