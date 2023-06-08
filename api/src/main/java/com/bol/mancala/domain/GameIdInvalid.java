package com.bol.mancala.domain;

public class GameIdInvalid extends MancalaException{
    private GameIdInvalid(String message) {
        super(message);
    }

    public static GameIdInvalid becauseValueIsNotAValidUUID(String value){
        return new GameIdInvalid(String.format("Game id provided is not a valid UUID: %s", value));
    }
}
