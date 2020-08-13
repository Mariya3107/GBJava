package lesson4;

public class Task1 {
    static volatile char c = 'A';
    static Object obj = new Object();

    static class Wait implements Runnable {
        private char currentLetter;
        private char nextLetter;

        public Wait(char currentLetter, char nextLetter) {
            this.currentLetter = currentLetter;
            this.nextLetter = nextLetter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (obj) {
                    try {
                        while (c != currentLetter) obj.wait();
                        System.out.print(currentLetter);
                        c = nextLetter;
                        obj.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Task1");
        new Thread(new Wait('A', 'B')).start();
        new Thread(new Wait('B', 'C')).start();
        new Thread(new Wait('C', 'A')).start();
    }
}

