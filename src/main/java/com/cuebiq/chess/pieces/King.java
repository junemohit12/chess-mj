package com.cuebiq.chess.pieces;

import com.cuebiq.chess.*;


public class King extends Piece {

    public King(Color color, PieceType pieceType) {
        super(color, pieceType);
    }


    public boolean isValidMove(Game game, Location sourceLocation, Location targetLocation) {
        return Utility.isOneUnitMove(sourceLocation, targetLocation);
    }
}
