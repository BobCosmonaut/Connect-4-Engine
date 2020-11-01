import java.util.Scanner;

public class Game {

    private Position currentPosition;

    boolean redsTurn;

    boolean redPlayer, yellowPlayer;

    Opponent redOpponent, yellowOpponent;

    public boolean isWon;
    Scanner scanner;

    private final String redPiece = "X", yellowPiece = "@", empty = "-", border = "#";

    public Game(boolean redPlayer, boolean yellowPlayer) {
        currentPosition = new Position();

        this.redPlayer = redPlayer;
        this.yellowPlayer = yellowPlayer;

        if (!redPlayer) {
            redOpponent = new Opponent(true);
        }
        if (!yellowPlayer) {
            yellowOpponent = new Opponent(false);
        }
        scanner = new Scanner(System.in);
        redsTurn = true;
    }

    /**
     * Draws the position on the console.
     */
    private void drawPosition() {
        printBorder();
        for (int i = DepricatedPosition.height - 1; i >= 0; i--) {
            for (int j = 0; j < DepricatedPosition.width; j++) {
                if (currentPosition.isRed(j, i)) {
                    System.out.print(redPiece + "  ");
                } else if (currentPosition.isYellow(j, i)) {
                    System.out.print(yellowPiece + "  ");
                } else {
                    System.out.print(empty + "  ");
                }
            }
            System.out.println();
        }
        printBorder();
        System.out.println();
    }


    /**
     * Prints one row of border characters.
     */
    private void printBorder() {
        for (int i = 0; i < DepricatedPosition.width * 3; i++) {
            System.out.print(border);
        }
        System.out.println();
    }

    /**
     * Gets the move from the player.
     */
    private void requestMove() {
        System.out.print("Enter the row to drop on: ");
        int row = Integer.parseInt(scanner.nextLine()) - 1;
        if (!currentPosition.rowIsFull(row)) {
            currentPosition.drop(redsTurn, row);
        }
    }

    /**
     * Gets the next move of the game, either from the player or the computer.
     */
    public void getMove() {
        if (redsTurn) {
            if (redPlayer) {
                requestMove();
            } else {
                currentPosition.drop(true, redOpponent.getMove(currentPosition));
                System.out.println("positions generated: " + PositionCell.cellsCreated);
            }
        } else {
            if (yellowPlayer) {
                requestMove();
            } else {
                currentPosition.drop(false, yellowOpponent.getMove(currentPosition));
                System.out.println("positions generated: " + PositionCell.cellsCreated);
            }
        }
        PositionCell.cellsCreated = 0;
        drawPosition();

        //TODO not very efficient
        if (currentPosition.getWinner() == Winner.Red) {
            isWon = true;
            System.out.println("Red won!");
        } else if (currentPosition.getWinner() == Winner.Yellow) {
            isWon = true;
            System.out.println("Yellow won!");
        }
        redsTurn = !redsTurn;
    }
}
