public class Opponent {

    private boolean red;

    public Opponent(boolean red) {
        this.red = red;
    }

    public int getMove(Position position) {
        System.out.println(red?"Red":"Yellow" + " is thinking.");
        return 1;


    }
}