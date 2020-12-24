/**
 * This class
 */
public class Seeker implements Runnable {

    private int dropIndex;
    private final PositionCell position;
    private Opponent parent;

    public Seeker(int dropIndex, PositionCell position, Opponent parent) {
        this.dropIndex = dropIndex;
        this.parent = parent;
        this.position = position;
    }

    @Override
    public void run() {
        parent.challengeBestOutcome(dropIndex, position.getBestOutcome());
    }
}
