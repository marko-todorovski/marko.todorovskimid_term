class CounterThread extends Thread {
    private final Counter counter;
    private final int increments;

    public CounterThread(Counter counter, int increments) {
        this.counter = counter;
        this.increments = increments;
    }

    @Override
    public void run() {
        for (int i = 0; i < increments; i++) {
            counter.increment();
        }
    }
}
//a