public class Position {
    private byte[] red;
    private byte[] yellow;
    public static final int width = 7, height = 6;

    public Position() {

        yellow = new byte[width];
        red = new byte[width];
    }

    public void drop(boolean forRed, int row) {
        for (int i = 0; i < height; i++) {
            if (isEmpty(row, i)) {
                place(forRed, row, i);
                break;
            }
        }
    }

    private void place(boolean forRed, int x, int y) {
        if (forRed) {
            red[x] |= 0x1 << y;
        } else {
            yellow[x] |= 0x1 << y;
        }
    }

    public boolean rowIsFull(int row) {
        return !isEmpty(row, height - 1);
    }

    private boolean isEmpty(int x, int y) {
        return (red[x] << y == 0) && (yellow[x] << y == 0);
    }

    public boolean isRed(int x, int y) {
        return (red[x] & 0x01 << y) > 0;
    }

    public boolean isYellow(int x, int y) {
        return (yellow[x] & 0x01 << y) > 0;
    }
}
