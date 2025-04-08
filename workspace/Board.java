           

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png"; 
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
    private static final String RESOURCES_HANK_PNG = "Hank.png";
    private static final String RESOURCES_HANKB_PNG = "HankB.png";

	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    private Square fromMoveSquare;
    private Piece previous;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    
   

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        //TO BE IMPLEMENTED FIRST
       
      //for (.....)  
//        	populate the board with squares here. Note that the board is composed of 64 squares alternating from 
//        	white to black.

//Pre Con: Board initialized, board.length iis not out of bounds
//Post Con: Creates a 2D array representing the game board, adds to the array board each square and it's colour through the use of a alternating boolean

    boolean count = true;
    for(int i = 0; i < board.length; i++){
      count = !count;
      for(int c = 0; c < board.length; c++){
          count = !count;
         board[i][c] = new Square(this, count, i, c); 
         this.add(board[i][c]);
}
}
        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	//set up the board such that the black pieces are on one side and the white pieces are on the other.
	//since we only have one kind of piece for now you need only set the same number of pieces on either side.
	//it's up to you how you wish to arrange your pieces.
    private void initializePieces() {
    	for (int col = 0; col < 8; col++) {
            board[6][col].put(new Pawn(true, RESOURCES_WPAWN_PNG)); // a2 to h2
        }
        for (int col = 0; col < 8; col++) {
            board[1][col].put(new Pawn(false, RESOURCES_BPAWN_PNG)); // a7 to h7
        }
    
        board[7][0].put(new Rook  (true, RESOURCES_WROOK_PNG));
        board[7][1].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][3].put(new Hank  (true, RESOURCES_HANK_PNG));
        board[7][4].put(new King  (true, RESOURCES_WKING_PNG));
        board[7][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][6].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][7].put(new Rook  (true, RESOURCES_WROOK_PNG));
        board[0][0].put(new Rook  (false, RESOURCES_BROOK_PNG));
        board[0][1].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][3].put(new Hank  (false, RESOURCES_HANKB_PNG));
        board[0][4].put(new King  (false, RESOURCES_BKING_PNG));
        board[0][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][6].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][7].put(new Rook  (false, RESOURCES_BROOK_PNG));
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

  

    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
        
    }

    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));


        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            boolean IsWhite = currPiece.getColor();
            fromMoveSquare = sq;
      if (!IsWhite && whiteTurn) { 
                 currPiece = null; 
                 fromMoveSquare = null;
                 return;
                }
            if (IsWhite && !whiteTurn) { 
                 currPiece = null; 
                 fromMoveSquare = null;
                 return;
            }
        }
        repaint();
    }


public boolean isInCheck(boolean kingColor) {

        for ( int row  = 0; row < (board.length) ; row++){

         for ( int col = 0 ; col < board[row].length  ; col++ ){

              Square s= board[row][col];
    
              if ( s.getOccupyingPiece() != null && kingColor != s.getOccupyingPiece().getColor()){

                  for (Square a  : s.getOccupyingPiece().getControlledSquares(board,s)){

                    if ( a.getOccupyingPiece() != null && a.getOccupyingPiece() instanceof King && kingColor == a.getOccupyingPiece().getColor() ) {
                        
                                 return true; 
                         }
                     }
                }
             }
         }
    
        return false; 
     }
       

    //TO BE IMPLEMENTED!
    //should move the piece to the desired location only if this is a legal move.
    //use the pieces "legal move" function to determine if this move is legal, then complete it by
    //moving the new piece to it's new board location. 
    //Pre Con: Board Initialized, MouseEvent is not null, getComponentAt is not null, getLegalMoves returns valid legal moves
    //Post Con: currPiece is moved to endSquare, currPiece set to null

    public void mouseReleased(MouseEvent e) {

        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        for ( Square[] row : board){

            for(Square s : row){
                s.setBorder(null);
            }
        }
        if ( currPiece!=null) {

            if ( currPiece.getLegalMoves(this ,fromMoveSquare).contains(endSquare) ) {

               endSquare.put(currPiece);
               fromMoveSquare.removePiece();
        
               if(isInCheck(whiteTurn)){
                 fromMoveSquare.put(currPiece);
                 endSquare.removePiece();
               }
               else{
               whiteTurn = !whiteTurn;
               }
            }
        }
        
       
        fromMoveSquare.setDisplay(true);
        currPiece = null;
        repaint();
    }
    



    @Override
    public void mouseDragged(MouseEvent e) {
     
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        if (currPiece != null && fromMoveSquare != null) { 
            Square[][] boardArray = this.getSquareArray();

            for (Square[] row : boardArray) {
                for (Square sq : row) {
                    if (sq != null) sq.setBorder(null);
                }
            }
       
             fromMoveSquare.setBorder(BorderFactory.createLineBorder(Color.BLUE));


      
            ArrayList<Square> legalMoves = currPiece.getLegalMoves(this, fromMoveSquare);
            if (legalMoves != null) {
                 for (Square s : legalMoves) {
                   if (s != null) s.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                }
            }


            ArrayList<Square> controlledSquaresList = currPiece.getControlledSquares(boardArray, fromMoveSquare);

            if (controlledSquaresList != null) {
                for (Square s : controlledSquaresList) {
                    if (s != null && s.getBorder() == null) {
                       s.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }
                 }
            }

        }
        repaint();
    }




    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

  


}