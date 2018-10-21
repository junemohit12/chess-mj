package com.cuebiq.chess.pieces;

import com.cuebiq.chess.*;

public class Pawn extends Piece {

    public Pawn(Color color, PieceType pieceType) {

        super(color, pieceType);

    }


    @Override
    public boolean isValidMove(Game game, Location sourceLocaion, Location targetLocation) {
        return game.isPawnMoveValid(sourceLocaion, targetLocation, getColor());

    }
}
