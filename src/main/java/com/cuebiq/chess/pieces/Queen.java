package com.cuebiq.chess.pieces;

import com.cuebiq.chess.*;

public class Queen extends Piece {

    public Queen(Color color, PieceType pieceType) {

        super(color, pieceType);

    }


    public boolean isValidMove(Game game, Location sourceLocaion, Location targetLocation) {

        return (game.isDiagonalValid(sourceLocaion, targetLocation)
                | game.isLinearValid(sourceLocaion, targetLocation));
    }


}
