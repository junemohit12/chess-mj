package com.cuebiq.chess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;

/**
 * Main class that implements api endpoints
 */


public class Launch {
    private static final Logger logger = LoggerFactory.getLogger(Game.class.getName());
    private static Gson gson = new Gson();
    private static Map<Integer, Game> gameList = new HashMap<Integer, Game>();
    public static void main(String[] args) {

        port(3725);

        get("/newGame", (request, response) -> {
            logger.info("New Game call made");

            int count = gameList.size() + 1;

            gameList.put(new Integer(count), new Game(count));

            logger.info("New Game Created with id:" + count);

            return (count);
        });

        get("/gameList", (request, response) -> {
            logger.info("Get Game List");

            return gameList.keySet();

        });

        get("/gameDetails/:gameId", (request, response) -> {
            try {
                Integer gameId = Integer.parseInt(request.params(":gameId"));
                Game game = gameList.get(gameId);
                if (game == null) {
                    return "Game not found";
                } else {
                    return game.getCurrentState();
                }
            } catch (NumberFormatException e) {
                return "Game ID should be a number";
            }
        });


        get("/removed/:gameId",(request,response) -> {
            logger.info("Request Call Made");

            try {
                Integer gameId = Integer.parseInt(request.params(":gameId"));
            Game game = gameList.get(gameId);
            if (game == null) {
                return "Game not found";
            } else {
                return game.getRemovedPieces();
            }
        } catch (NumberFormatException e) {
            return "Game ID should be a number";
        }

        });


        post("/move", (request, response) -> {

            logger.info("Move Call Made");



            Type type = new TypeToken<Map<String, String>>() {
            }.getType();

            Map<String, String> moveMap = gson.fromJson(request.body(), type);
            String resp;
            try {
                logger.info("Request Object:" + moveMap);
                Color color = Color.valueOf(moveMap.get("color"));
                PieceType pieceType = PieceType.valueOf(moveMap.get("pieceType"));
                Integer gameId = new Integer(moveMap.get("gameId"));


                int xPos = Integer.parseInt(moveMap.get("xPos"));
                int yPos = Integer.parseInt(moveMap.get("yPos"));

                //xPos and yPos are valid only if within 0 and 7
                if (xPos < 0 | xPos > 7 | yPos < 0 | yPos > 7) {
                    resp = "Invalid xPos/yPos. xPos and yPos should be between 0 and 7";
                    logger.info("Invalid x and/or y co-ordinates");
                    return resp;
                }

                Game game = gameList.get(gameId);
                if (game == null) {
                    logger.info("Invalid Game");

                    resp = "Game not found";
                    return resp;
                }

                if (game.move(color, pieceType, new Location(xPos, yPos))) {
                    logger.info("Move Successful");

                    resp = "Move Successful";
                    return resp;
                } else {
                    logger.info("Move Unsuccessful");

                    resp = "Move not successful";
                    return resp;
                }

            } catch (NumberFormatException excep) {
                logger.error(excep.getMessage());
                resp = "gameId, xPos, yPos missing or invalid. gameId, xPos, yPos should be an integer ";
                return resp;
            } catch (IllegalArgumentException excep) {
                logger.error(excep.getMessage());

                resp = "Invalid of missing color/piece. Color should be WHITE or BLACK in Upper case. Piece should be one of following:" +
                        "";
                return resp;
            } catch (Exception excep) {
                logger.error(excep.getMessage());
                resp = excep.getMessage();
                return resp;
            }


        });


    }


}
