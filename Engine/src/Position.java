public class Position {
    private byte[] red;
    private byte[] yellow;
    public static final int width = 7, height = 6;
    public Winner winner;

    /**
     * Currently only called by Game constructor
     */
    public Position() {
        winner = Winner.None;
        yellow = new byte[width];
        red = new byte[width];
    }

    /**
     * Creates a new Position by making a move on the parent position.
     *
     * @param parent
     * @param drop
     * @param dropColor true if the color of the dropped checker is red.
     */
    public Position(Position parent, int drop, boolean dropColor) {
        yellow = new byte[width];
        red = new byte[width];

        for (int i = 0; i < width; i++) {
            red[i] = parent.getRed()[i];
            yellow[i] = parent.getYellow()[i];
        }
        drop(dropColor, drop);

        winner = getWinner();
    }

    /**
     * Drops a checker into the grid.
     *
     * @param forRed
     * @param row
     */
    public void drop(boolean forRed, int row) {
        for (int i = 0; i < height; i++) {
            if (isEmpty(row, i)) {
                place(forRed, row, i);
                break;
            }
        }
    }

    /**
     * Places a checker at a specific point on the grid. Only to be used by drop().
     *
     * @param forRed
     * @param x
     * @param y
     */
    private void place(boolean forRed, int x, int y) {
        if (forRed) {
            red[x] |= 0x01 << y;
        } else {
            yellow[x] |= 0x01 << y;
        }
    }

    /**
     * Returns true if this row has been filled to the top.
     *
     * @param row
     * @return
     */
    public boolean rowIsFull(int row) {
        return !isEmpty(row, height - 1);
    }

    /**
     * Returns true if this point on the grid does not have a checker.
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isEmpty(int x, int y) {
        return ((red[x] & 0x1 << y) == 0) && ((yellow[x] & 0x1 << y) == 0);
    }

    public boolean isRed(int x, int y) {
        return (red[x] & 0x01 << y) > 0;
    }

    public boolean isYellow(int x, int y) {
        return (yellow[x] & 0x01 << y) > 0;
    }

    /**
     * @return The win state for this position.
     */
    public Winner getWinner() {
        //horizontal

        for (int i = 0; i < width - 3; i++) {
            if ((red[i] & red[i + 1] & red[i + 2] & red[i + 3]) > 0) {
                return Winner.Red;
            }

            if ((yellow[i] & yellow[i + 1] & yellow[i + 2] & yellow[i + 3]) > 0) {
                return Winner.Yellow;
            }
        }

        //vertical

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 4; j++) {
                if (((yellow[i] >> j) & 0x0f) == 0x0f) {
                    return Winner.Yellow;
                }
                if (((red[i] >> j) & 0x0f) == 0x0f) {
                    return Winner.Red;
                }
            }
        }


        //diagonal

        for (int i = 0; i < width - 4; i++) {
            for (int j = 0; j < height - 4; j++) {
                if (isYellow(i, j) && isYellow(i + 1, j + 1) && isYellow(i + 2, j + 2) && isYellow(i + 3, j + 3)) {
                    return Winner.Yellow;
                }

                if (isRed(i, j) && isRed(i + 1, j + 1) && isRed(i + 2, j + 2) && isRed(i + 3, j + 3)) {
                    return Winner.Red;
                }
            }
        }

        for (int i = width - 1; i > 3; i--) {
            for (int j = 0; j < height - 4; j++) {
                if (isYellow(i, j) && isYellow(i - 1, j + 1) && isYellow(i - 2, j + 2) && isYellow(i - 3, j + 3)) {
                    return Winner.Yellow;
                }
                if (isRed(i, j) && isRed(i - 1, j + 1) && isRed(i - 2, j + 2) && isRed(i - 3, j + 3)) {
                    return Winner.Red;
                }
            }
        }

        return Winner.None;
    }

    /**
     * @return The score for this position, positive scores are better for red, negative scores are better for yellow.
     */
    public int score() {
        if (winner == Winner.Red) {
            return Integer.MAX_VALUE;
        } else if (winner == Winner.Yellow) {
            return Integer.MIN_VALUE;
        } else {

            int score = 0;

            //Horizontal
            for (int i = 0; i < width - 4; i++) {
                for (int j = 0; j < height; j++) {

                    int redPotential = 0;
                    int yellowPotential = 0;
                    for (int k = 0; k < 4; k++) {
                        redPotential += (red[i + k] >> j) & 0x1;
                        yellowPotential += (yellow[i + k] >> j) & 0x1;
                        //TODO Could be more efficient by breaking out when needed.
                    }
                    // If both red and yellow have checkers here, this particular area cant result in a win for either, and has no "potential."
                    if (redPotential * yellowPotential == 0) {
                        if (redPotential > 0) {
                            if (redPotential == 4) {
                                System.out.println('!');
                            }
                            score += Math.pow(redPotential, 2);
                        } else {
                            //Yellow exculsivly has potential...
                            score -= Math.pow(redPotential, 2);
                        }

                    }
                }
            }
            //vertical

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height - 4; j++) {
                    int redPotential = (red[i] >> j) & 0x1;
                    int yellowPotential = (yellow[i] >> j) & 0x1;

                    if (redPotential * yellowPotential == 0) {
                        if (redPotential > 0) {
                            score += Math.pow(redPotential, 2);
                        } else {
                            //Yellow exculsivly has potential...
                            score -= Math.pow(redPotential, 2);
                        }
                    }
                }
            }

            //Diagonal upward...
            for (int i = 0; i < width - 4; i++) {
                for (int j = 0; j < height - 4; j++) {
                    int yellowPotential = 0;
                    int redPotential = 0;
                    for (int k = 0; k < 4; k++) {
                        if (isRed(i + k, j + k)) {
                            redPotential++;
                        }
                        if (isYellow(i + k, j + k)) {
                            yellowPotential++;
                        }

                        if (redPotential * yellowPotential != 0) {
                            if (redPotential > 0) {
                                score += Math.pow(redPotential, 2);
                            } else {
                                score += Math.pow(yellowPotential, 2);
                            }
                        }
                    }
                }
            }

            //Diagonal downward...
            for (int i = 0; i < width - 4; i++) {
                for (int j = height - 1; j > 4; j--) {
                    int yellowPotential = 0;
                    int redPotential = 0;
                    for (int k = 0; k < 4; k++) {
                        if (isRed(i + k, j - k)) {
                            redPotential++;
                        }
                        if (isYellow(i + k, j - k)) {
                            yellowPotential++;
                        }

                        if (redPotential * yellowPotential != 0) {
                            if (redPotential > 0) {
                                score += Math.pow(redPotential, 2);
                            } else {
                                score += Math.pow(yellowPotential, 2);
                            }
                        }
                    }
                }
            }
            return score;
        }
    }


    public byte[] getRed() {
        return red;
    }

    public byte[] getYellow() {
        return yellow;
    }
}