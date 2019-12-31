import javafx.scene.control.Button;

public class Mine_OLD extends Button {
    private int xPos, yPos;

    public Mine_OLD(int x, int y, String st) {
        super(st);
        this.xPos = x;
        this.yPos = y;
    }

    public int getX() {return xPos;}
    public int getY() {return yPos;}
}