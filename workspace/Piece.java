import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Piece {



    protected boolean color;
    protected BufferedImage img;
    protected String piece;
    protected String thisColor;

    public Piece(boolean color, String img_file) {
        this.color = color;
        try {
            if (this.img == null) {
                this.img = ImageIO.read(getClass().getResource(img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    
    }

    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    // to be overriden in each subclass
    public ArrayList<Square> getLegalMoves(Board b, Square currentSquare) {
        return null;
    }

    // make sure to override this!
    public String toString() {
        if (color)
            return "white";
        else
            return "black";
    }

    // to be implemented by each subclass
    public ArrayList<Square> getControlledSquares(Square[][] board, Square currentSquare) {
        return null;
    }

    public boolean isWhite(){
        if(this.color == true){
            return true;
        }
        else{
            return false;
        }
    }

    public Square[] getControlledSquares(Board board, Square fromMoveSquare) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getControlledSquares'");
    }

}
