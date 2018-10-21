package com.cuebiq.chess.pieces;

import com.cuebiq.chess.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Knight extends Piece {
    private static final Logger logger = LoggerFactory.getLogger(Game.class.getName());


    public Knight(Color color, PieceType pieceType) {
        super(color, pieceType);

    }


    @Override
    public boolean isValidMove(Game game, Location sourceLocaion, Location targetLocation) {
        System.out.println("Validating Knight Move");

        boolean isValid = true;
        int xCurrent = sourceLocaion.getxPos();
        int yCurrent = sourceLocaion.getyPos();
        int xTarget = targetLocation.getxPos();
        int yTarget = targetLocation.getyPos();

        if (!(((Math.abs(xCurrent - xTarget) == 2 & Math.abs(yCurrent - yTarget) == 1)
                | (Math.abs(yCurrent - yTarget) == 2 & Math.abs(xCurrent - xTarget) == 1)))) {
            isValid = false;
        }


        return isValid;
    }

}
