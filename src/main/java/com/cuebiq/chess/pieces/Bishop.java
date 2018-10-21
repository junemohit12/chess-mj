package com.cuebiq.chess.pieces;

import com.cuebiq.chess.*;


public class Bishop extends Piece {

    public Bishop(Color color, PieceType pieceType) {

        super(color, pieceType);

    }


    public boolean isValidMove(Game game, Location sourceLocation, Location targetLocation) {
        return game.isDiagonalValid(sourceLocation, targetLocation);
    }
}
