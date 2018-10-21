package com.cuebiq.chess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class - Piece.
 * All specific pieces (Bishop/King....) extend the class
 * For the simple current use case piece was not needed. I could have used Enum + case statement to execute through game
 * Used this class to illustrate that if different game types were to exist, each class could implement their own version of
 * valid move
 */


public abstract class Piece {
    private Color color;
    private PieceType pieceType;
    private static final Logger logger = LoggerFactory.getLogger(Game.class.getName());

    public Piece(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
        logger.info(pieceType + " with color "+ color + " Created");

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", pieceType=" + pieceType +
                '}';
    }

    public abstract boolean isValidMove(Game game, Location sourceLocation, Location targetLocation);

}
