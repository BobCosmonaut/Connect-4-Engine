import javax.crypto.spec.PSource;
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

    private void drawPosition() {
        printBorder();
        for (int i = Position.height - 1; i >= 0; i--) {
            for (int j = 0; j < Position.width; j++) {
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

    private void printBorder() {
        for (int i = 0; i < Position.width * 3; i++) {
            System.out.print(border);
        }
        System.out.println();
    }

    private void requestMove() {
        System.out.print("Enter the row to drop on: ");
        int row = Integer.parseInt(scanner.nextLine())-1;
        if (!currentPosition.rowIsFull(row)) {
            currentPosition.drop(redsTurn, row);
        }
    }

    public void getMove() {
        if (redsTurn) {
            if (redPlayer) {
                requestMove();
            } else {
                redOpponent.getMove(currentPosition);
            }
        } else {
            if (yellowPlayer) {
                requestMove();
            } else {
                currentPosition.drop(false, yellowOpponent.getMove(currentPosition));
            }
        }
        drawPosition();

        redsTurn = !redsTurn;
    }
}
