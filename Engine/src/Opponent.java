import java.util.concurrent.CountDownLatch;

public class Opponent {

    private boolean red;
    private boolean multithreading = true;

    public Opponent(boolean red) {
        this.red = red;
    }

    /**
     * @param position The position to move from
     * @return The row to drop the checker on.
     */
    public int getMove(Position position) {
        System.out.println(red ? "Red is thinking." : "Yellow is thinking.");

        PositionCell[] subPositions = new PositionCell[Position.WIDTH];

        for (int i = 0; i < Position.WIDTH; i++) {
            if (!position.rowIsFull(i)) {
                subPositions[i] = (new PositionCell(new Position(position, i, red), !red, (byte) 1));
            }
        }

        if (!multithreading) {

            //Sets the best outcome to a worst case scenario...
            int bestOutcome = red ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            int bestDrop = -1;

            for (int i = 0; i < Position.WIDTH; i++) {
                if (subPositions[i] != null) {
                    int testOutcome = subPositions[i].getBestOutcome();
                    if ((testOutcome >= bestOutcome && red) || (testOutcome <= bestOutcome && !red)) {
                        bestOutcome = testOutcome;
                        bestDrop = i;
                    }
                }
            }
            return bestDrop;
        } else {
            bestMultithreadedOutcome = red ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            //Avoids dropping on a full column
            int numberOfMoves = 0;
            for (int i = 0; i < Position.WIDTH; i++) {
                if (!position.rowIsFull(i)) {
                    numberOfMoves++;
                }
            }
            latch = new CountDownLatch(numberOfMoves);

            for (int i = 0; i < Position.WIDTH; i++) {
                if (!position.rowIsFull(i)) {
                    Seeker seeker = new Seeker(i, subPositions[i], this);

                    Thread thread = new Thread(seeker);
                    thread.start();
                }
            }

            try {
                latch.await();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return bestMultithreadIndex;
        }
    }

    CountDownLatch latch;

    private int bestMultithreadedOutcome;
    private int bestMultithreadIndex;

    public synchronized void challengeBestOutcome(int dropIndex, int testOutcome) {
        if ((testOutcome >= bestMultithreadedOutcome && red) || (testOutcome <= bestMultithreadedOutcome && !red)) {
            bestMultithreadedOutcome = testOutcome;
            bestMultithreadIndex = dropIndex;
        }

        latch.countDown();
    }
}