public class Example4 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new MyRunnable(i));
            thread.start();
        }
    }

    static class MyRunnable implements Runnable {
        private final int number;

        public MyRunnable(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println("Поток " + number + " запущен");
        }
    }
}
