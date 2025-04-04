
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

//you will need to implement two functions in this file.


public class HankB extends Piece {
  
  public HankB(boolean isWhite, String img_file) {
    super(isWhite, img_file);
    piece = "HankB";
    thisColor = "black";  
  }



  
  // TO BE IMPLEMENTED!
  // return a list of every square that is "controlled" by this piece. A square is
  // controlled
  // if the piece capture into it legally.

  // Pre Con: Board is a 2D array that exists, Start is a valid square on Board,
  // getRow and getCol must return places on the board
  // Post Con: New arraylist occ created for storing the controled squares and is
  // returned at the end,
  public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    ArrayList<Square> occ = new ArrayList<>();
    if (start.getRow() - 1 >= 0) {
      for (int i = 0; i < 3; i++) {
        occ.add(board[start.getCol()][start.getRow() - i]);
        for (int j = 0; i < 3; j++) {
          occ.add(board[start.getCol() - j][start.getRow()]);
      }
    }

    if (start.getRow() + 1 >= 8) {
      for (int i = 0; i < 3; i++) {
        occ.add((board[start.getCol()][start.getRow() + i]));
        for (int j = 0; i < 3; j++) {
          occ.add(board[start.getCol() + j][start.getRow()]);
      }
      }
    }
  }
    return occ;
  
   
  }
  // TO BE IMPLEMENTED!
  // implement the move function here
  // it's up to you how the piece moves, but at the very least the rules should be
  // logical and it should never move off the board!
  // returns an arraylist of squares which are legal to move to
  // please note that your piece must have some sort of logic. Just being able to
  // move to every square on the board is not
  // going to score any points.

  // Pre Con: Board is valid, Start valid square in b startRow and startCol valid places,
  // Post Con: New ArrayList called moves created, adds squares to move 1 around
  // the start row and start col, returns moves

  public ArrayList<Square> getLegalMoves(Board b, Square start) {
    ArrayList<Square> moves = new ArrayList();

    int startRow = start.getRow();
    int startCol = start.getCol();

    Square[][] arr = b.getSquareArray();

    if (startRow - 1 >= 0 && (!arr[startRow - 1][startCol].isOccupied())
        || startRow - 1 >= 0 && (arr[startRow - 1][startCol].isOccupied())) {
      moves.add(arr[startRow - 1][startCol]);
    }

    if (startCol - 1 >= 0 && (!arr[startCol - 1][startRow].isOccupied())
        || startCol - 1 >= 0 && (arr[startCol - 1][startRow].isOccupied())) {
      moves.add(arr[startRow][startCol - 1]);
    }

    if (startRow + 1 < 8 && (!arr[startRow + 1][startCol].isOccupied())
        || startRow + 1 < 8 && (arr[startRow + 1][startCol].isOccupied())) {
      moves.add(arr[startRow + 1][startCol]);
    }

    if (startCol + 1 < 8 && (!arr[startCol + 1][startRow].isOccupied())
        || startCol + 1 < 8 && (arr[startCol + 1][startRow].isOccupied())) {
      moves.add(arr[startRow][startCol + 1]);
    }

    return moves;
  }

  public String toString(){
    return "A" + thisColor + " " + piece;
  }
}