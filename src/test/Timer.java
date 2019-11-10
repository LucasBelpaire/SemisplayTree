package test;

class Timer {
    private long    time0 = System.nanoTime(), time1 = time0;
    private boolean ended = false;
    void     start() { time0 = System.nanoTime(); ended = false; }
    void     end() { time1 = System.nanoTime(); ended = true; }
    double   delta() {
        if ( ! ended) { end(); }
        return 1e-9 * (time1 - time0); }
}
