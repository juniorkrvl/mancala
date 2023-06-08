package com.bol.mancala.domain;

public class GameNotFound extends MancalaException{
    public GameNotFound(GameId gameId) {
        super(String.format("Game with id %s not found", gameId.toString()));
    }
}
