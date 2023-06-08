package com.bol.mancala.application;

import com.bol.mancala.domain.*;
import org.springframework.stereotype.Service;

/***
 * The GameService orchestrates the creation and
 * persistence of game matches
 */
@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        System.out.println("GameRepo inside GameService: " + gameRepository);
    }

    public Game startNewGame() throws CannotCreateBoard {
        Game game = Game.start();
        gameRepository.save(game);
        return game;
    }

    public Game makeMove(GameId gameId, int pitId, int playerId) throws GameNotFound, CannotMakeMove {
        Game game = getGame(gameId);
        game.makeMove(pitId, playerId);
        gameRepository.update(game);
        return game;
    }

    public Game getGame(GameId gameId) throws GameNotFound {
        return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFound(gameId));
    }

    public void deleteGame(GameId gameId) {
        gameRepository.delete(gameId);
    }
}