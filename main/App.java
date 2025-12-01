package main;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        demoMatrix();
        demoBank();
    }

    private static void demoMatrix() {
        Matrix<Double> matrixA = Matrix.randomDoubleMatrix(3, 3, 0, 10);
        Matrix<Double> matrixB = Matrix.randomDoubleMatrix(3, 3, 0, 10);

        System.out.println("Matrix A:");
        System.out.println(matrixA);

        System.out.println("Matrix B:");
        System.out.println(matrixB);

        Matrix<Double> sum = matrixA.add(matrixB);
        Matrix<Double> difference = matrixA.subtract(matrixB);

        System.out.println("A + B:");
        System.out.println(sum);

        System.out.println("A - B:");
        System.out.println(difference);

        System.out.println("Row maxima of A: " + matrixA.maxElementsPerRow());
        System.out.println("Main diagonal average of A: " + matrixA.mainDiagonalAverage());
    }

    private static void demoBank() {
        Map<Integer, Integer> initialLoad = new HashMap<>();
        initialLoad.put(200, 10);
        initialLoad.put(100, 10);
        initialLoad.put(50, 20);
        initialLoad.put(20, 50);

        try {
            Bank bank = Bank.initializeNetwork(3, initialLoad, 20, 40);
            System.out.println("Total: " + bank.totalCashInNetwork());

            ATM atm = bank.getAtms().get(0);
            Map<Integer, Integer> deposit = new HashMap<>();
            deposit.put(500, 1);
            deposit.put(10, 5);
            atm.deposit(deposit);

            System.out.println("ATM snapshot after deposit: " + atm.snapshot());
            System.out.println("Dispensing 380 from ATM: " + atm.withdraw(380));
            System.out.println("Total: " + bank.totalCashInNetwork());
        } catch (Exception e) {
            System.err.println("Bank error: " + e.getMessage());
        }
    }
}
