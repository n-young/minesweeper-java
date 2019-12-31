import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class Main extends Application {
    static final int MINE_SIZE = 40;
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        //Initialize Game
        int height = 16;
        int width = 30;
        int mines = 99;
        if (height * width < mines - 9) {
            throw new IllegalArgumentException("Too many mines for given board size!");
        }
        Game game = new Game(height, width, mines);

        buildWindow(primaryStage, game);  
    }

    private void buildWindow(Stage primaryStage, Game game) {
        //Menu Bar 
        Label minesRemaining = new Label("Mines Left: " + game.numRemaining());
        Label timeRemaining = new Label("0");
        Button smiley = new Button(":)");
        smiley.setOnAction(e -> resetGame(primaryStage, game));
        BorderPane menuBar = new BorderPane();
        menuBar.setLeft(minesRemaining);
        menuBar.setCenter(smiley);
        menuBar.setRight(timeRemaining);

        //Mine Board
        GridPane mineBoard = buildBoard(primaryStage, game.getHeight(), game.getWidth(), game);
        
        //Putting it together
        BorderPane main = new BorderPane();
        main.setTop(menuBar);
        main.setCenter(mineBoard);

        //Display
        int sceneWidth = game.getWidth() * MINE_SIZE;
        int sceneHeight = game.getHeight() * MINE_SIZE + 30;
        Scene scene = new Scene(main, sceneWidth, sceneHeight);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();      
    }

    private GridPane buildBoard(Stage primaryStage, int height, int width, Game game) {
        GridPane mineBoard = new GridPane();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Mine tempMine = new Mine(j, i, MINE_SIZE, game.getGameBoard()[i][j]);
                tempMine.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        boolean lost = game.clickSquare(tempMine.getXPos(), tempMine.getYPos());
                        buildWindow(primaryStage, game);
                        if (lost) {
                            lostGame(primaryStage, game);
                        }
                        else if (game.checkWon()) {
                            wonGame(primaryStage, game);
                        }
                    }
                    else if (e.getButton() == MouseButton.SECONDARY) {
                        game.flagSquare(tempMine.getXPos(), tempMine.getYPos());
                        
                        buildWindow(primaryStage, game);
                    }
                });
                mineBoard.add(tempMine, j, i, 1, 1);
            }
        }
        return mineBoard;
    }

    /*
    OLD BUILD BOARD
    private GridPane buildBoard(Stage primaryStage, int height, int width, Game game) {
        GridPane mineBoard = new GridPane();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Mine tempMine = new Mine(j, i, "" + game.getGameBoard()[i][j]);
                tempMine.setOnAction(e -> {
                    boolean lost = game.clickSquare(tempMine.getX(), tempMine.getY());
                    buildWindow(primaryStage, game);
                    if (lost) {
                        lostGame(primaryStage, game);
                    }
                    else if (game.checkWon()) {
                        wonGame(primaryStage, game);
                    }
                });
                mineBoard.add(tempMine, j, i, 1, 1);
            }
        }
        return mineBoard;
    }
    */

    private void resetGame(Stage primaryStage, Game game) {
        game = new Game(game.getHeight(), game.getWidth(), game.getMines());
        buildWindow(primaryStage, game);
    }

    private void lostGame(Stage primaryStage, Game game) {
        System.out.println("You lose!");
        resetGame(primaryStage, game);
    }

    private void wonGame(Stage primaryStage, Game game) {
        System.out.println("You win!");
        resetGame(primaryStage, game);
    }
}