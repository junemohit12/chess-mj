package com.cuebiq.chess;

public final class Utility {

    /**
     * Checks if move is diagonal or not
     */
    public static boolean isDiagonalMove(Location sourceLocation, Location targetLocation) {
        System.out.println("Calling is Diagonal Move from" + sourceLocation + " to " + targetLocation);
        boolean isValid = false;

        int xCurrent = sourceLocation.getxPos();
        int yCurrent = sourceLocation.getyPos();
        int xTarget = targetLocation.getxPos();
        int yTarget = targetLocation.getyPos();

        if (Math.abs(xCurrent - xTarget) == Math.abs(yCurrent - yTarget)) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Checks if move is linear or not
     */
    public static boolean isLinearMove(Location sourceLocation, Location targetLocation) {
        int xCurrent = sourceLocation.getxPos();
        int yCurrent = sourceLocation.getyPos();
        int xTarget = targetLocation.getxPos();
        int yTarget = targetLocation.getyPos();


        if ((xCurrent == xTarget) | (yCurrent == yTarget)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Checks if move is one unit or not
     */
    public static boolean isOneUnitMove(Location sourceLocation, Location targetLocation) {
        int xCurrent = sourceLocation.getxPos();
        int yCurrent = sourceLocation.getyPos();
        int xTarget = targetLocation.getxPos();
        int yTarget = targetLocation.getyPos();

        boolean isValid = false;
        if (Math.abs(xCurrent - xTarget) == 1 & (Math.abs(yCurrent - yTarget) == 1 | yCurrent == yTarget)) {
            isValid = true;
        } else {
            if (Math.abs(yCurrent - yTarget) == 1 & xCurrent == xTarget) {
                isValid = true;
            }

        }
        return isValid;
    }


}

