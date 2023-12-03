package at.klimo.aoc.util;

public class If {

    private final boolean test;

    public If(boolean test) {
        this.test = test;
    }

    public void then(Runnable statement) {
        if (test) {
            statement.run();
        }
    }
}
