package com.cuebiq.chess;

import com.cuebiq.chess.pieces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Game implements the game logic.
 * pieceMap (HashMap) stores the state of the game (Location/Piece) combination
 * removedPieces stores the pieces that are removed
 */

public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class.getName());

    //pieceMap is public and not private to enable easier testing and test case creation. In production
    //it should be private
    public Map<Location, Piece> pieceMap = new HashMap<Location, Piece>();

    private ArrayList<Piece> removedPieces = new ArrayList<Piece>();
    private Status status;
    private final int gameId;
    private  Color lastMoveColor;

    public Game(int gameId) {
        status = Status.ONGOING;
        this.gameId = gameId;
        init();
    }


    /**
     * This function initializes the chessgame
     *
     */

    public void init() {
        logger.info("Initialize Game");

        pieceMap.put(new Location(0, 0), new Rook(Color.WHITE, PieceType.ROOK1));
        pieceMap.put(new Location(1, 0), new Knight(Color.WHITE, PieceType.KNIGHT1));
        pieceMap.put(new Location(2, 0), new Bishop(Color.WHITE, PieceType.BISHOP1));
        pieceMap.put(new Location(3, 0), new Queen(Color.WHITE, PieceType.QUEEN));
        pieceMap.put(new Location(4, 0), new King(Color.WHITE, PieceType.KING));
        pieceMap.put(new Location(5, 0), new Bishop(Color.WHITE, PieceType.BISHOP2));
        pieceMap.put(new Location(6, 0), new Knight(Color.WHITE, PieceType.KNIGHT2));
        pieceMap.put(new Location(7, 0), new Rook(Color.WHITE, PieceType.ROOK2));

        pieceMap.put(new Location(0, 1), new Pawn(Color.WHITE, PieceType.PAWN1));
        pieceMap.put(new Location(1, 1), new Pawn(Color.WHITE, PieceType.PAWN2));
        pieceMap.put(new Location(2, 1), new Pawn(Color.WHITE, PieceType.PAWN3));
        pieceMap.put(new Location(3, 1), new Pawn(Color.WHITE, PieceType.PAWN4));
        pieceMap.put(new Location(4, 1), new Pawn(Color.WHITE, PieceType.PAWN5));
        pieceMap.put(new Location(5, 1), new Pawn(Color.WHITE, PieceType.PAWN6));
        pieceMap.put(new Location(6, 1), new Pawn(Color.WHITE, PieceType.PAWN7));
        pieceMap.put(new Location(7, 1), new Pawn(Color.WHITE, PieceType.PAWN8));

        pieceMap.put(new Location(0, 7), new Rook(Color.BLACK, PieceType.ROOK1));
        pieceMap.put(new Location(1, 7), new Knight(Color.BLACK, PieceType.KNIGHT1));
        pieceMap.put(new Location(2, 7), new Bishop(Color.BLACK, PieceType.BISHOP1));
        pieceMap.put(new Location(3, 7), new King(Color.BLACK, PieceType.KING));
        pieceMap.put(new Location(4, 7), new Queen(Color.BLACK, PieceType.QUEEN));
        pieceMap.put(new Location(5, 7), new Bishop(Color.BLACK, PieceType.BISHOP2));
        pieceMap.put(new Location(6, 7), new Knight(Color.BLACK, PieceType.KNIGHT2));
        pieceMap.put(new Location(7, 7), new Rook(Color.BLACK, PieceType.ROOK2));

        pieceMap.put(new Location(0, 6), new Pawn(Color.BLACK, PieceType.PAWN1));
        pieceMap.put(new Location(1, 6), new Pawn(Color.BLACK, PieceType.PAWN2));
        pieceMap.put(new Location(2, 6), new Pawn(Color.BLACK, PieceType.PAWN3));
        pieceMap.put(new Location(3, 6), new Pawn(Color.BLACK, PieceType.PAWN4));
        pieceMap.put(new Location(4, 6), new Pawn(Color.BLACK, PieceType.PAWN5));
        pieceMap.put(new Location(5, 6), new Pawn(Color.BLACK, PieceType.PAWN6));
        pieceMap.put(new Location(6, 6), new Pawn(Color.BLACK, PieceType.PAWN7));
        pieceMap.put(new Location(7, 6), new Pawn(Color.BLACK, PieceType.PAWN8));

    }


    /**
     * This function compares color of the two pieces - at source location and target location
     *
     * @param currentPiece: color of the piece to move (BLACK/WHITE)
     * @param targetPiece:  Type of piece to move (PieceType Enum)
     * @return: boolean true indicating if color is different (or target is null). False otherwise
     */


    private boolean differentColor(Piece currentPiece, Piece targetPiece) {
        boolean isValid = true;
        if (targetPiece != null ) {

            isValid = !(currentPiece.getColor().equals(targetPiece.getColor()));

        }
        return isValid;
    }


    /**
     * This function compares color of the last move to current move
     *
     * @param color: color of the piece to move (BLACK/WHITE)
     *
     */

        public boolean moveColorDifferent(Color color)
        {
            logger.info("Inside move color different call");
            logger.info("Last move color:"  + lastMoveColor);
            if (lastMoveColor == null)
            {

                return true;
            }
            else{
                return !color.equals(lastMoveColor);

            }
        }







    /**
     * Returns true/false indicating if move was executed
     * The move will be executed if its valid
     * Valid Move criteria:
     *      1) Color different from previous moves (alternative white black moves)
     *      2) Target location should be blank or of different color
     *      3) Move is valid based on piece moved (BISHOP/KING/PAWN/QUEEN/ROOK/KNIGHT)
     *      4) Game is ongoing. IF game is already completed function exits.
     * If move is valid, then the move is executed.
     * If target is not null,
     * RemovePiece map (which stores removed pieces) is updated.
     * Key for target location is updated thereby removing the piece there
     * The game status updated if needed
     * For instance, if the king is removed, then the game status changes to Completed
     *
     * @param color:          color of the piece to move (BLACK/WHITE)
     * @param pieceType:      Type of piece to move (PieceType Enum)
     * @param targetLocation: Target location where piece should be moved to
     * @return: boolean true/false indicating if pawn move is valid
     */

    public boolean move(Color color, PieceType pieceType, Location targetLocation) throws Exception{

        logger.info("Move call initiated");
        logger.info(color.toString());
        logger.info(pieceType.toString());
        logger.info(targetLocation.toString());
        boolean isValid = true;



        if(moveColorDifferent(color))

        {
            if(getStatus() == Status.ONGOING)

        {
            Location sourceLocation = getCurrentLocation(color, pieceType);
            if (sourceLocation != null)
            {
                Piece currentPiece = pieceMap.get(sourceLocation);
                Piece targetPiece = pieceMap.get(targetLocation);
            /*
            If Both source and target have same color, then move is invalid and dont proceed further
             */
                isValid = differentColor(currentPiece, targetPiece);

                if (isValid) {

                    if (!currentPiece.isValidMove(this, sourceLocation, targetLocation)) {
                        System.out.println("Move Invalid");

                        isValid = false;
                    }
                }

                if (isValid) {
                    pieceMap.remove(sourceLocation);
                    pieceMap.put(targetLocation, currentPiece);

                    if (targetPiece != null) {
                        removedPieces.add(targetPiece);
                        if (targetPiece.getPieceType().equals(PieceType.KING)) {
                            status = Status.COMPLETED;
                        }

                    }

                    //Update last move color for next query
                    lastMoveColor = color;
                }
                return isValid;
            }

            else {
                //Source location is null.Piece is removed and invalid
                throw new Exception("Piece not found");
            }


            }
        else{
            throw new Exception("Game is finished");
        }

        }
        else{
            throw new Exception("Color of last move same as the current");
        }




            /*
            If both are not of same color (different color or null) then proceed
             */


    }


    /* For Unit testing Execute Move:

     */
    public void executeMoveTest(Location sourceLocation, Location targetLocation, Piece currentPiece, Piece targetPiece) {
        //executeMove(sourceLocation,targetLocation,currentPiece,targetPiece);
    }


    /**
     * Returns true/false indicating if pawn move is valid:
     * <p>
     * Valid Move:
     * a) One unit forward in y direction (0 to 7 for White and 7 to 0 for Black)
     * b) one unit diagonal if the other piece is of different color
     * </p>
     *
     * @param sourceLocation: Source Location of the piece type to move
     * @param targetLocation: Target Location of the piece type to move
     * @param color:          Color of piece type to move (WHITE/BLACK)
     * @return: boolean true/false indicating if pawn move is valid
     */
    public boolean isPawnMoveValid(Location sourceLocation, Location targetLocation, Color color) {

        int xCurrent = sourceLocation.getxPos();
        int yCurrent = sourceLocation.getyPos();
        int xTarget = targetLocation.getxPos();
        int yTarget = targetLocation.getyPos();

        Piece targetPiece = pieceMap.get(targetLocation);


        boolean isValid = false;

        if (color == Color.WHITE) {
            if (xCurrent == xTarget & yTarget == yCurrent + 1 & targetPiece == null) {
                isValid = true;
            }

            if (Math.abs(xCurrent - xTarget) == 1 & yTarget == yCurrent + 1 & targetPiece != null) {
                if (targetPiece.getColor() != color) {
                    isValid = true;
                }
            }
        }


        if (color == Color.BLACK) {
            if (xCurrent == xTarget & yTarget == yCurrent - 1 & targetPiece == null) {
                isValid = true;
            }

            if (Math.abs(xCurrent - xTarget) == 1 & yTarget == yCurrent - 1 & targetPiece != null) {
                if (targetPiece.getColor() != color) {
                    isValid = true;
                }
            }
        }


        return isValid;
    }


    /**
     * Returns true/false indicating cells between sourceLocation and targetLocation (excluding target) are empty:
     * The same program works for both linear and diagonal
     * If its linear ( either x or y is same for both source and target -> one of the or in while will be true)
     * If its diagonal ( Both x and y will move in tandem )
     * If current > target than it increments , else decremens
     * @param sourceLocation: Source Location of the piece type to move
     * @param targetLocation: Target Location of the piece type to move
     * @return: boolean true/false indicating if linear move is valid
     */
    private boolean isEmpty(Location sourceLocation, Location targetLocation)

    {
        int xCurrent = sourceLocation.getxPos();
        int yCurrent = sourceLocation.getyPos();
        int xTarget = targetLocation.getxPos();
        int yTarget = targetLocation.getyPos();
        boolean isValid = true;
        while ((Math.abs(xCurrent - xTarget) > 1 | Math.abs(yCurrent - yTarget) > 1) & isValid) {

            if (xTarget > xCurrent) {
                xCurrent = xCurrent + 1;
            } if(xCurrent > xTarget) {
                xCurrent = xCurrent - 1;
            }
            if (yTarget > yCurrent) {
                yCurrent = yCurrent + 1;

            } if(yCurrent > yTarget) {
                yCurrent = yCurrent - 1;
            }

            Location location = new Location(xCurrent, yCurrent);

            if (pieceMap.get(location) != null) {
                isValid = false;
            }

        }
        return isValid;
    }


    /**
     * Returns true/false indicating if linear move is valid:
     * <p>
     * Valid Linear Move:
     * a) Along x axis or y axis
     * b) All location points between source and target location (excluding target location) are null i.e. empty
     * </p>
     *
     * @param sourceLocation: Source Location of the piece type to move
     * @param targetLocation: Target Location of the piece type to move
     * @return: boolean true/false indicating if linear move is valid
     */
    public boolean isLinearValid(Location sourceLocation, Location targetLocation) {
        logger.info("Is Linear Valid Called");

        return (Utility.isLinearMove(sourceLocation, targetLocation) &
                isEmpty(sourceLocation,targetLocation));

    }







    /**
     * Returns true/false indicating if diagonal move is valid:
     * <p>
     * Valid Diagonal Move:
     * a) Diagonal: Absolute change in x = absolute change in y
     * b) All location points between source and target location (excluding target location) along diagonal
     * are null i.e. empty
     * </p>
     *
     * @param sourceLocation: Source Location of the piece type to move
     * @param targetLocation: Target Location of the piece type to move
     * @return: boolean true/false indicating if linear move is valid
     */
    public boolean isDiagonalValid(Location sourceLocation, Location targetLocation) {


        //Utility function to check if the move is diagonal i.e. change in x = change in y
        return (Utility.isDiagonalMove(sourceLocation, targetLocation) &
                isEmpty(sourceLocation,targetLocation));
    }


    /**
     * Returns current location of piece
     * <p>
     * Valid Diagonal Move:
     * a) Diagonal: Absolute change in x = absolute change in y
     * b) All location points between source and target location (excluding target location) along diagonal
     * are null i.e. empty
     * </p>
     *
     * @param color:     Color of the piece
     * @param pieceType: Type of piece
     * @return: boolean true/false indicating if linear move is valid
     */
    public Location getCurrentLocation(Color color, PieceType pieceType) {

        Location location = null;

        for (Map.Entry<Location, Piece> entrySet : pieceMap.entrySet()) {

            Piece piece = entrySet.getValue();
            if (piece.getColor() == color & piece.getPieceType() == pieceType) {

                location = entrySet.getKey();
                break;
            }


        }
        return location;

    }


    /**
     * Returns current placement of the game
     *
     * @return: Map with current placement
     */
    public Map<Location, Piece> getCurrentState() {
        return pieceMap;
    }


    /**
     * Returns current state of the game
     *
     * @return: Current state (Finished/Ongoing)
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns removed pieces
     *
     * @return: Arraylist with pieces removed from the game
     */
    public ArrayList<Piece> getRemovedPieces() {
        return removedPieces;
    }

}
