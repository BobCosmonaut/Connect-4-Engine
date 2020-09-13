public class Position {
    private boolean[][] yellow;
    private boolean[][] red;

    public static final int width = 7, height = 6;

    public Position() {
        yellow = new boolean[width][height];
        red = new boolean[width][height];
    }

    public void drop(boolean forRed, int row) {
        for (int i = 0; i < height; i++) {
            if (isEmpty(row, i)) {
                if (forRed) {
                    red[row][i] = true;
                } else {
                    yellow[row][i] = true;
                }
                break;
            }
        }
    }

    public boolean rowIsFull(int row) {
        return !isEmpty(row, height - 1);
    }

    private boolean isEmpty(int x, int y) {
        return !yellow[x][y] && !red[x][y];
    }

    public boolean isRed(int x, int y) {
        return red[x][y];
    }

    public boolean isYellow(int x, int y) {
        return yellow[x][y];
    }
}
