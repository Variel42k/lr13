import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Example6 {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5};
        ForkJoinPool pool = new ForkJoinPool();
        int sum = pool.invoke(new SumTask(array));
        System.out.println("Сумма элементов в массиве: " + sum);
    }
}

class SumTask extends java.util.concurrent.RecursiveTask<Integer> {
    private static final int THRESHOLD = 1000;
    private final int[] array;
    private final int start;
    private final int end;

    public SumTask(int[] array) {
        this(array, 0, array.length);
    }

    private SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            return Arrays.stream(array, start, end).sum();
        } else {
            int mid = start + (end - start) / 2;
            SumTask left = new SumTask(array, start, mid);
            SumTask right = new SumTask(array, mid, end);
            left.fork();
            int rightResult = right.compute();
            int leftResult = left.join();
            return leftResult + rightResult;
        }
    }
}
