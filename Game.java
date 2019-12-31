class Game{
    private int height; // Height of the board
    private int width; // Width of the board
    private int mines; // Number of mines
    private boolean firstClick;
    private int[][] mineBoard; // Mine Board - contains mine location
    private char[][] gameBoard; // Game Board - contains Player View

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getMines() {return mines;}
    public boolean isFirstClick() {return firstClick;}
    public int[][] getMineBoard() {return mineBoard;}
    public char[][] getGameBoard() {return gameBoard;}

    /**
     * Constructor
     * @param h Game board height
     * @param w Game board width
     * @param m Number of mines
     */
    public Game(int h, int w, int m) {
        height = h;
        width = w;
        mines = m;
        firstClick = true;
        createGameBoard();
    }

    
    /**
     * Initializes the Mine Board
     * @param initX the initial X click
     * @param initY the initial Y click
     */
    public void createMineBoard(int initX, int initY) {
        int x, y;
        int[][] mb = new int[height][width];
        for (int i = mines; i > 0; i--) {
            y = (int) (Math.random() * height);
            x = (int) (Math.random() * width);
            if (mb[y][x] == 1 || (Math.abs(x - initX) <= 1 && Math.abs(y - initY) <= 1)) {
                i++;
            }
            else {
                mb[y][x] = 1;
            }
        }
        mineBoard = mb;
    }

    /**
     * Initializes the Game Board
     */
    public void createGameBoard() {
        int h = getHeight();
        int w = getWidth();
        char[][] gb = new char[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                gb[i][j] = '*';
            }
        }
        gameBoard = gb;
    }

    /**
     * Clicks a square, returns true if the square is a mine. Updates game board.
     * @return      true if the square is a mine.
     */
    public boolean clickSquare(int x, int y) {
        if (firstClick) {
            firstClick = false;
            createMineBoard(x, y);
            clickSquare(x, y);
        }
        else if (mineBoard[y][x] == 1) {
            gameBoard[y][x] = 'X';
            return true;
        }
        else {
            int num = numMines(x, y);
            gameBoard[y][x] = (char) (num + '0');
            if (num == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if ( (y + i >= 0) && (y + i < height)
                            && (x + j >= 0)
                            && !(i == 0 && j == 0)
                            && (x + j < getWidth())
                            && (gameBoard[y + i][x + j] == '*')) {
                                clickSquare(x + j, y + i);
                            }
                    }
                }
            }
        }
        return false;
    }

    public void flagSquare(int x, int y) {
        if (gameBoard[y][x] == 'F') {
            gameBoard[y][x] = '*';
        }
        else if (gameBoard[y][x] == '*') {
            gameBoard[y][x] = 'F';
        }
    }

     /**
     * returns the number of mines surrounding a given position
     * on the board. Prints an error if the given position is a mine.
     * @return      the number of mines around a position
     */
    private int numMines(int x, int y) {
        int count = 0;
        if (mineBoard[y][x] == 1) {
            System.out.println("This is a mine");
        }
        else {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if ( (y + i >= 0)
                        && (y + i < getHeight())
                        && (x + j >= 0)
                        && (x + j < getWidth())
                        && (mineBoard[y + i][x + j] == 1)) {
                            count++;
                        }
                }
            }
        }
        return count;
    }

    /**
     * Checks if the player has won
     * @return      true if the player has won
     */
    public boolean checkWon() {
        int count = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (gameBoard[i][j] == '*') {
                    count++;
                }
            }
        }
        if (count == getMines()) {
            return true;
        }
        return false;
    }

    /**
     * Prints the given Board
     * @see      the Mine Board, which contains only 0's and 1's
     */
    private void printBoard(int[][] board) {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
    }

    private void printBoard(char[][] board) {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
        }
    }
}
