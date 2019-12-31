import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class Mine extends StackPane {
    private int xPos, yPos;

    public Mine(int x, int y, int w, char st) {
        super();
        Rectangle mineBox = new Rectangle(w, w);
        Text text = new Text("" + st);
        this.xPos = x;
        this.yPos = y;
        if (st == '*') {
            mineBox.setFill(Color.SLATEGRAY);
        }
        else if (st == 'F') {
            mineBox.setFill(Color.RED);
        }
        else {
            mineBox.setFill(Color.SILVER);
        }
        mineBox.setStroke(Color.BLACK);
        mineBox.setStrokeWidth(0.5);
        getChildren().addAll(mineBox, text);
    }

    public int getXPos() {return xPos;}
    public int getYPos() {return yPos;}
}