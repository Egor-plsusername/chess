
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

//you will need to implement two functions in this file.


public class Hank extends Piece {

    public Hank(boolean color, String img_file) {

        super(color, img_file);

        this.piece = "Hank";
    }

    @Override
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        Set<Square> controlledSet = new HashSet<>();
        int startRow = start.getRow();
        int startCol = start.getCol();
        boolean isWhite = this.getColor();

        int forwardRowDirection = isWhite ? -1 : 1;
        int forwardRowOffset = 2 * forwardRowDirection;
        int backwardRowOffset = -2 * forwardRowDirection;
        int[][] distantOffsets = {
            {forwardRowOffset,  0}, {forwardRowOffset,  1}, {forwardRowOffset, -1},
            {backwardRowOffset, 0}, {backwardRowOffset, 1}, {backwardRowOffset,-1}
        };

        for (int[] offset : distantOffsets) {
            int targetRow = startRow + offset[0];
            int targetCol = startCol + offset[1];
            if (targetRow >= 0 && targetRow < board.length && targetCol >= 0 && targetCol < board[0].length) {
                controlledSet.add(board[targetRow][targetCol]);
            }
        }

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {

                if (dRow == 0 && (dCol == 0 || dCol == -1 || dCol == 1)) {
                    continue;
                }

                int targetRow = startRow + dRow;
                int targetCol = startCol + dCol;

                if (targetRow >= 0 && targetRow < board.length && targetCol >= 0 && targetCol < board[0].length) {
                    controlledSet.add(board[targetRow][targetCol]);
                }
            }
        }

        return new ArrayList<>(controlledSet);
    }

    @Override
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        Square[][] board = b.getSquareArray();
        int startRow = start.getRow();
        int startCol = start.getCol();
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };
        for (int[] dir : directions) {
            int targetRow = startRow + dir[0];
            int targetCol = startCol + dir[1];
            if (targetRow >= 0 && targetRow < board.length && targetCol >= 0 && targetCol < board[0].length) {
                Square targetSquare = board[targetRow][targetCol];
                if (!targetSquare.isOccupied()) {
                    if (!legalMoves.contains(targetSquare)) {
                        legalMoves.add(targetSquare);
                    }
                }
            }
        }
        ArrayList<Square> controlledSquares = this.getControlledSquares(board, start); // Call existing method

        for (Square targetSquare : controlledSquares) {

            if (targetSquare.isOccupied()) {
                Piece occupyingPiece = targetSquare.getOccupyingPiece();
                if (occupyingPiece != null && occupyingPiece.getColor() != this.getColor()) {
                    if (!legalMoves.contains(targetSquare)) {
                        legalMoves.add(targetSquare);
                    }
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        String colorString;
 if (getColor()) {
            colorString = "white";
        } else {
            colorString = "black";
        }
        return colorString + " " + this.piece;
    }
}

