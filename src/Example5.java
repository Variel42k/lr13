import java.util.Arrays;

public class Example5 {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 10, 9};
        int processors = Runtime.getRuntime().availableProcessors();
        int chunkSize = array.length / processors;
        final int[] max = {Integer.MIN_VALUE};

        Thread[] threads = new Thread[processors];
        for (int i = 0; i < processors; i++) {
            final int start = i * chunkSize;
            final int end = (i == processors - 1) ? array.length : (i + 1) * chunkSize;
            threads[i] = new Thread(() -> {
                int localMax = Integer.MIN_VALUE;
                for (int j = start; j < end; j++) {
                    if (array[j] > localMax) {
                        localMax = array[j];
                    }
                }
                synchronized (Example5.class) {
                    if (localMax > max[0]) {
                        max[0] = localMax;
                    }
                }
            });
            threads[i].start();
        }

        Arrays.stream(threads).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Максимальный элемент в массиве: " + max[0]);
    }
}
