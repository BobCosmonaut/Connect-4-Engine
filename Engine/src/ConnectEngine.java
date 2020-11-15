/**
 * Author: Caleb Hefty
 * September 2020
 */
import java.util.Scanner;

public class ConnectEngine {

    public static void main(String[] args) {
        Game game;
        Scanner scanner = new Scanner(System.in);

        System.out.println("###  Welcome to Connect Engine!  ###");
        System.out.print("Play as first, second, or Neither (F/S/N):");
        String answer = scanner.next();
        if (answer.toLowerCase().contains("f")) {
            game = new Game(true, false);
        } else if (answer.toLowerCase().contains("s")) {
            game = new Game(false, true);
        } else {
            game = new Game(false, false);
        }

        boolean validLevel = false;
        do {
            System.out.print("AI level (1 - 8): ");
            try {
                int level = Integer.parseInt(scanner.next());
                if (level > 8 || level < 1) {
                    throw new Exception();
                }
                validLevel = true;
                PositionCell.maxDepth = level;
            } catch (Exception e) {
                System.out.println("Invalid");
            }
        } while (!validLevel);

        while (!game.isWon) {
            game.getMove();
        }
    }

}
