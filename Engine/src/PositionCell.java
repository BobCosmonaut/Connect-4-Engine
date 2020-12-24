import java.util.ArrayList;

public class PositionCell {
    Position position;
    ArrayList<PositionCell> subPositions;
    byte depth;
    static int maxDepth, cellsCreated;
    boolean redsMove;
    int score;

    public PositionCell(Position position, boolean redsMove, byte depth) {
        cellsCreated++;

        this.position = position;
        this.depth = depth;
        this.redsMove = redsMove;

        subPositions = new ArrayList<PositionCell>(Position.WIDTH);

        score = position.score();

        if (depth != maxDepth && position.winner == Winner.None) {
            generateSubpositions();
        }
    }

    /**
     * Generates all subpositions for this position.
     */
    private void generateSubpositions() {
        for (int i = 0; i < Position.WIDTH; i++) {
            if (!position.rowIsFull(i)) {
                subPositions.add(new PositionCell(new Position(this.position, i, redsMove), !redsMove, (byte) (depth + 1)));
            }
        }
    }

    /**
     *
     * @return The row to drop on that will get the best outcome for the player who moves after this position.
     */
    public int getBestOutcome() {
        if (subPositions.size() == 0) {
            return score;
        }

        int bestOutcome = subPositions.get(0).getBestOutcome();

        for (int i = 1; i < subPositions.size(); i++) {
            int testOutcome = subPositions.get(i).getBestOutcome();
            if ((testOutcome <= bestOutcome && !redsMove) || ( testOutcome >= bestOutcome && redsMove)) {
                bestOutcome = testOutcome;
            }
        }
        return bestOutcome;
    }
}
