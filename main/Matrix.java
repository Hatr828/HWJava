package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Matrix<T extends Number & Comparable<T>> implements Comparable<Matrix<T>> {

    private final int rows;
    private final int cols;
    private final List<List<T>> data;

    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive");
        }
        this.rows = rows;
        this.cols = cols;
        this.data = new ArrayList<>(rows);
        for (int r = 0; r < rows; r++) {
            List<T> row = new ArrayList<>(Collections.nCopies(cols, null));
            data.add(row);
        }
    }

    public Matrix(List<List<T>> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        int rowLength = values.get(0).size();
        if (rowLength == 0) {
            throw new IllegalArgumentException("Matrix cannot have empty rows");
        }
        this.rows = values.size();
        this.cols = rowLength;
        this.data = new ArrayList<>(rows);
        for (List<T> sourceRow : values) {
            if (sourceRow.size() != rowLength) {
                throw new IllegalArgumentException("All rows must have the same length");
            }
            List<T> row = new ArrayList<>(cols);
            for (T value : sourceRow) {
                row.add(value);
            }
            data.add(row);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public T get(int row, int col) {
        validateIndex(row, col);
        T value = data.get(row).get(col);
        return value;
    }

    public void set(int row, int col, T value) {
        validateIndex(row, col);
        data.get(row).set(col, value);
    }

    public static Matrix<Double> randomDoubleMatrix(int rows, int cols, double minInclusive, double maxExclusive) {
        if (minInclusive >= maxExclusive) {
            throw new IllegalArgumentException("Minimum must be less than maximum");
        }
        Matrix<Double> matrix = new Matrix<>(rows, cols);
        Random rd = new Random();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
               double value = minInclusive + rd.nextDouble() * (maxExclusive - minInclusive);
               matrix.data.get(r).set(c, value);
            }
        }
        return matrix;
    }

    public Matrix<T> add(Matrix<T> other) {
        ensureSameSize(other);
        Matrix<T> result = new Matrix<>(rows, cols);
         for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                T a = this.data.get(r).get(c);
                T b = other.data.get(r).get(c);
                double val = a.doubleValue() + b.doubleValue();
                T value = (T) (Double) val;
                result.data.get(r).set(c, value);
            }
        }
        return result;
    }

    public Matrix<T> subtract(Matrix<T> other) {
        ensureSameSize(other);
        Matrix<T> result = new Matrix<>(rows, cols);
         for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                T a = this.data.get(r).get(c);
                T b = other.data.get(r).get(c);
                double val = a.doubleValue() - b.doubleValue();
                T value = (T) (Double) val;
                result.data.get(r).set(c, value);
            }
        }
        return result;
    }

    public List<T> maxElementsPerRow() {
        List<T> maxima = new ArrayList<>(rows);
        for (int r = 0; r < rows; r++) {
            List<T> row = data.get(r);
            maxima.add(Collections.max(row));
        }
        return maxima;
    }

    public double mainDiagonalAverage() {
        int diagonalLength = Math.min(rows, cols);
        double sum = 0;
        for (int i = 0; i < diagonalLength; i++) {
            sum += get(i, i).doubleValue();
        }
        return sum / diagonalLength;
    }

    private double totalSum() {
        double sum = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                sum += get(r, c).doubleValue();
            }
        }
        return sum;
    }

    @Override
    public int compareTo(Matrix<T> other) {
        Objects.requireNonNull(other, "Other matrix cannot be null");
        ensureSameSize(other);
        return Double.compare(totalSum(), other.totalSum());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (List<T> row : data) {
            builder.append(row).append(System.lineSeparator());
        }
        return builder.toString();
    }

    private void validateIndex(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Index out of bounds: [" + row + "," + col + "]");
        }
    }

    private void ensureSameSize(Matrix<?> other) {
        if (other == null) {
            throw new IllegalArgumentException("Matrix cannot be null");
        }
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions");
        }
    }
}
