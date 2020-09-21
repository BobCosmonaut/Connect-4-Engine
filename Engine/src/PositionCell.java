import java.util.ArrayList;

public class PositionCell {
    Position parent;
    Position position;
    ArrayList<PositionCell> subPositions;
    byte depth;
    static int maxDepth = 7, cellsCreated;
    boolean redsMove;
    int score;

    public PositionCell(Position parent, Position position, boolean redsMove, byte depth) {
        this.parent = parent;
        this.position = position;
        this.depth = depth;
        this.redsMove = redsMove;

        subPositions = new ArrayList<PositionCell>(Position.width);

        cellsCreated++;

        score = position.isWinner();

        if (depth != maxDepth && score == 0) {
            generateSubpositions();
        }
    }

    public void generateSubpositions() {
        for (int i = 0; i < Position.width; i++) {
            if (!position.rowIsFull(i)) {
                subPositions.add(new PositionCell(position, new Position(this.position, i, redsMove), !redsMove, (byte) (depth + 1)));
            }
        }
    }

    public int getBestOutcome() {
        if (subPositions.size() == 0) {
            if(score != 0){
                System.out.println(score);
            }
            return score;
        }

        int bestOutcome = subPositions.get(0).getBestOutcome();

        if (subPositions.size() > 1) {
            for (int i = 1; i < subPositions.size(); i++) {
                int testOutcome = subPositions.get(i).getBestOutcome();
                if ((bestOutcome >= testOutcome && !redsMove) ||(bestOutcome <= testOutcome && redsMove)) {
                    bestOutcome = testOutcome;
                }
            }
        }
        return bestOutcome;
    }
}
