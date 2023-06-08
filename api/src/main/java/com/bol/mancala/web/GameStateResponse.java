package com.bol.mancala.web;

import com.bol.mancala.domain.Game;

public class GameStateResponse {

    private final String gameId;
    private final int currentPlayerId;
    private final int[] pits;
    private final int firstPlayerMancalaPosition;
    private final int secondPlayerMancalaPosition;
    private final boolean isGameOver;

    private GameStateResponse (
        String gameId,
        int currentPlayerId,
        int[] pits,
        int firstPlayerMancalaPosition,
        int secondPlayerMancalaPosition,
        boolean isGameOver
    ) {
        this.gameId = gameId;
        this.currentPlayerId = currentPlayerId;
        this.pits = pits;
        this.firstPlayerMancalaPosition = firstPlayerMancalaPosition;
        this.secondPlayerMancalaPosition = secondPlayerMancalaPosition;
        this.isGameOver = isGameOver;
    }

    public static GameStateResponse fromGame(Game game) {
        return new GameStateResponse(
            game.id().toString(),
            game.currentPlayer().id(),
            game.board().pitsToArray(),
            game.firstPlayer().mancalaPosition(),
            game.secondPlayer().mancalaPosition(),
            game.board().isGameOver()
        );
    }

    public String getGameId() {
        return gameId;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public int[] getPits() {
        return pits;
    }

    public int getFirstPlayerMancalaPosition() {
        return firstPlayerMancalaPosition;
    }

    public int getSecondPlayerMancalaPosition() {
        return secondPlayerMancalaPosition;
    }

    public boolean isGameOver() {
        return isGameOver;
    }


}