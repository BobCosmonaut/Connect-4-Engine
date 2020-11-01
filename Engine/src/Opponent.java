public class Opponent {

    private boolean red;
    private boolean multithreading;

    public Opponent(boolean red) {
        this.red = red;
    }

    /**
     *
     * @param position The position to move from
     * @return The row to drop the checker on.
     */
    public int getMove(Position position) {
        System.out.println(red ? "Red" : "Yellow" + " is thinking.");

        PositionCell[] subPositions = new PositionCell[Position.width];

        if (!multithreading) {
            for (int i = 0; i < Position.width; i++) {
                if (!position.rowIsFull(i)) {
                    subPositions[i] = (new PositionCell(new Position(position, i, red), !red, (byte) 1));
                }
            }

            //Sets the best outcome to a worst case scenario...
            int bestOutcome = red? Integer.MIN_VALUE:Integer.MAX_VALUE;
            int bestDrop = -1;

            for (int i = 0; i < Position.width; i++) {
                if (subPositions[i] != null) {
                    int testOutcome = subPositions[i].getBestOutcome();
                    if ((testOutcome > bestOutcome && red) || (testOutcome < bestOutcome && !red)) {
                        bestOutcome = testOutcome;
                        bestDrop = i;
                    }
                }
            }
            return bestDrop;
        }
        System.out.println("Not running here!");
        return 1;
    }
}