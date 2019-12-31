import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        //Initialize Game
        int height = 16;
        int width = 30;
        int mines = 99;
        Game game = new Game(height, width, mines);

        buildWindow(primaryStage, game);  
    }

    private void buildWindow(Stage primaryStage, Game game) {
        //Menu Bar 
        Label minesRemaining = new Label("10");
        Label timeRemaining = new Label("100");
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
        Scene scene = new Scene(main, 1000, 500);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();      
    }

    private GridPane buildBoard(Stage primaryStage, int height, int width, Game game) {
        GridPane mineBoard = new GridPane();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Mine tempMine = new Mine(j, i, "" + game.getGameBoard()[i][j]);
                tempMine.setOnAction(e -> {
                    game.clickSquare(tempMine.getX(), tempMine.getY());
                    buildWindow(primaryStage, game);
                });
                mineBoard.add(tempMine, j, i, 1, 1);
            }
        }
        return mineBoard;
    }

    private void resetGame(Stage primaryStage, Game game) {
        game = new Game(game.getHeight(), game.getWidth(), game.getMines());
        buildWindow(primaryStage, game);
    }
}