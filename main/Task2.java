import java.util.Arrays;
import java.util.function.IntPredicate;

public class Task2 {
    private static int sumMatching(int[] numbers, IntPredicate predicate) {
        return Arrays.stream(numbers)
                .filter(predicate)
                .sum();
    }

    private static void printSum(String label, int[] numbers, IntPredicate predicate) {
        int sum = sumMatching(numbers, predicate);
        System.out.printf("Sum of numbers %s: %d%n", label, sum);
    }

    public static void run() {
        System.out.println("Task 2:");

        int[] numbers = {-5, -1, 0, 1, 2, 3, 5, 10, 15};
        int target = 5;
        int a = 3;
        int b = 8;

        IntPredicate equalToTarget = n -> n == target;
        IntPredicate outsideRange = n -> n < a || n > b;
        IntPredicate positive = n -> n > 0;
        IntPredicate negative = n -> n < 0;

        printSum("== " + target, numbers, equalToTarget);
        printSum("outside [" + a + "; " + b + "]", numbers, outsideRange);
        printSum("positive (>0)", numbers, positive);
        printSum("negative (<0)", numbers, negative);
        System.out.println();
    }
}
