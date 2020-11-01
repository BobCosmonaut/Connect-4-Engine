import java.util.Scanner;

public class ConnectEngine {

    private static Game game;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("###  Welcome to Connect Engine!  ###");
        System.out.print("Play as first, second, or Neither (F/S/N):");

        if (scanner.next().contains("F")) {
            game = new Game(true, false);
        } else if (scanner.next().contains("S")) {
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
