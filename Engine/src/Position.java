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
            red[x] |= 0x01 << y;
        } else {
            yellow[x] |= 0x01 << y;
        }
    }

    public boolean rowIsFull(int row) {
        return !isEmpty(row, height - 1);
    }

    private boolean isEmpty(int x, int y) {
        return ((red[x] & 0x1 << y) == 0) && ((yellow[x] & 0x1 << y) == 0);
    }

    public boolean isRed(int x, int y) {
        return (red[x] & 0x01 << y) > 0;
    }

    public boolean isYellow(int x, int y) {
        return (yellow[x] & 0x01 << y) > 0;
    }

    public byte isWinner() {
        if (yellowIsWinner()) {
            return -1;
        } else if (redIsWinner()) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean yellowIsWinner() {
        //horizontal
        for (int i = 0; i < width - 4; i++) {
            for (int j = 0; j < height; j++) {
                if ((yellow[i] & yellow[i + 1] & yellow[i + 2] & yellow[i + 3]) > 0) {
                    return true;
                }
            }
        }

        //vertical

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 4; j++) {
                if (yellow[i] << j >> (height - 1 - 4) == 0x0f) {
                    return true;
                }
            }
        }


        //up right

        for (int i = 0; i < width - 4; i++) {
            for (int j = 0; j < height - 4; j++) {
                if (isYellow(i, j) && isYellow(i + 1, j + 1) && isYellow(i + 2, j + 2) && isYellow(i + 3, j + 3)) {
                    return true;
                }
            }
        }

        for (int i = width - 1; i > 3; i--) {
            for (int j = 0; j < height - 4; j++) {
                if (isYellow(i, j) && isYellow(i - 1, j + 1) && isYellow(i - 2, j + 2) && isYellow(i - 3, j + 3)) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean redIsWinner() {
        //horizontal
        for (int i = 0; i < width - 4; i++) {
            for (int j = 0; j < height; j++) {
                if ((red[i] & red[i + 1] & red[i + 2] & red[i + 3]) > 0) {
                    return true;
                }
            }
        }
        //vertical

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 4; j++) {
                if (red[i] << j >> (height - 1 - 4) == 0x0f) {
                    return true;
                }
            }
        }
        for (int i = 0; i < width - 4; i++) {
            for (int j = 0; j < height - 4; j++) {
                if (isRed(i, j) && isRed(i + 1, j + 1) && isRed(i + 2, j + 2) && isRed(i + 3, j + 3)) {
                    return true;
                }
            }
        }

        for (int i = width - 1; i > 3; i--) {
            for (int j = 0; j < height - 4; j++) {
                if (isRed(i, j) && isRed(i - 1, j + 1) && isRed(i - 2, j + 2) && isRed(i - 3, j + 3)) {
                    return true;
                }
            }
        }


        return false;
    }
}