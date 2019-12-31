import java.util.Scanner;

class Game{
    private int height; // Height of the board
    private int width; // Width of the board
    private int mines; // Number of mines
    private int[][] mineBoard; // Mine Board - contains mine location
    private char[][] gameBoard; // Game Board - contains Player View

    public Game(int h, int w, int m) {
        height = h;
        width = w;
        mines = m;
        createMineBoard();
        createGameBoard();
    }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getMines() {return mines;}
    public int[][] getMineBoard() {return mineBoard;}
    public char[][] getGameBoard() {return gameBoard;}

    /**
     * Begins game play
     * @see      the Game!
     */
    public void playCLI() {
        Scanner s = new Scanner(System.in);
        // mineBoard = createMineBoard();
        // gameBoard = createGameBoard();
        boolean lost = false;
        int x, y;
        while (!lost) {
            printGameBoard();
            System.out.println();
            // printMineBoard();
            // System.out.println();
            System.out.println("What is the x-value of the square you want to clear?");
            x = s.nextInt();
            System.out.println("What is the y-value of the square you want to clear?");
            y = s.nextInt();
            lost = clickSquare(x, y);
            if (checkWon()) {
                break;
            }
        }
        if (lost) {
            printGameBoard();
            System.out.println();
            // printMineBoard();
            // System.out.println();
            System.out.println("You Lose!");
        }
        else {
            printGameBoard();
            System.out.println();
            // printMineBoard();
            // System.out.println();
            System.out.println("You Win!");            
        }
        s.close();
    }
    
    /**
     * Initializes the Mine Board
     * @return      the initialized Mine Board, which contains only 0's and 1's
     */
    public void createMineBoard() {
        int h = getHeight();
        int w = getWidth();
        int m = getMines();
        int x, y;
        int[][] mb = new int[h][w];
        for (int i = m; i > 0; i--) {
            y = (int) (Math.random() * h);
            x = (int) (Math.random() * w);
            if (mb[y][x] == 1) {
                i++;
            }
            else {
                mb[y][x] = 1;
            }
        }
        mineBoard = mb;
    }

    /**
     * Prints the Mine Board
     * @see      the Mine Board, which contains only 0's and 1's
     */
    private void printMineBoard() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                System.out.print(mineBoard[i][j] + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Initializes the Game Board
     * @return      the initialized Game Board, which contains only '*'s
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
     * Prints the Mine Board
     * @see      the Game Board, which contains only '*'s and numbers from 0 - 8.
     */
    private void printGameBoard() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                System.out.print(gameBoard[i][j] + " | ");
            }
            System.out.println();
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
     * Clicks a square, returns true if the square is a mine. Updates game board.
     * @return      true if the square is a mine.
     * TO DO: Split up the clickSquare and updateBoard functions
     */
    public boolean clickSquare(int x, int y) {
        if (mineBoard[y][x] == 1) {
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
            return false;
        }
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

    // private void showGameBoard() {
    //     1 + 1;
    // }
}
