package com.cuebiq.chess.pieces;

import com.cuebiq.chess.*;

public class Rook extends Piece {

    public Rook(Color color, PieceType pieceType) {
        super(color, pieceType);

    }


    public boolean isValidMove(Game game, Location sourceLocaion, Location targetLocation) {
        return game.isLinearValid(sourceLocaion, targetLocation);
    }

}
