package com.cuebiq.chess;

import org.junit.Test;
import com.cuebiq.chess.*;
import com.cuebiq.chess.pieces.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class GameTest {


    @Test
    public void isLinearValid() {

        Game game = new Game(0);

        game.pieceMap = new HashMap<>();
        game.pieceMap.put(new Location(4,5),new Rook(Color.WHITE,PieceType.ROOK1));
        game.pieceMap.put(new Location(3,5),new Rook(Color.BLACK,PieceType.PAWN1));


        boolean firstMove = game.isLinearValid(new Location(4,5),new Location(2,5));
        assertFalse(firstMove);

        boolean secondMove = game.isLinearValid(new Location(4,5),new Location(6,5));
        assertTrue(secondMove);

        boolean thirdMove = game.isLinearValid(new Location(4,5),new Location(1,6));
        assertFalse(thirdMove);


    }

    @Test
    public void isDiagonalValid() {


        Game game = new Game(0);

        game.pieceMap = new HashMap<>();
        game.pieceMap.put(new Location(4,5),new Rook(Color.WHITE,PieceType.ROOK1));
        game.pieceMap.put(new Location(2,3),new Rook(Color.BLACK,PieceType.PAWN1));


        boolean firstMove = game.isDiagonalValid(new Location(4,5),new Location(1,2));
        assertFalse(firstMove);

        boolean secondMove = game.isDiagonalValid(new Location(4,5),new Location(3,4));
        assertTrue(secondMove);

        boolean thirdMove = game.isDiagonalValid(new Location(4,5),new Location(1,1));
        assertFalse(thirdMove);
        boolean fourthMove = game.isDiagonalValid(new Location(4,5),new Location(3,6));

        assertTrue(fourthMove);

        boolean fifthMove = game.isDiagonalValid(new Location(4,5),new Location(2,7));

        assertTrue(fifthMove);

    }


}