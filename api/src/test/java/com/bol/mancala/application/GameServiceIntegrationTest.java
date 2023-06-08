package com.bol.mancala.application;

import com.bol.mancala.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceIntegrationTest {

    private GameService gameService;
    private GameServiceAssertions gameAssertions;

    private GameRepository gameRepository;

    @BeforeEach
    void setup() {
        gameRepository = new InMemoryGameRepository();
        gameService = new GameService(gameRepository);
        gameAssertions = new GameServiceAssertions();
    }

    @Test
    void shouldReturnValidGameAfterStart() throws CannotCreateBoard {
        Game game = gameService.startNewGame();

        // Assert game object is correct
        assertNotNull(game);
        assertInstanceOf(Game.class, game);
        assertInstanceOf(GameId.class, game.id());
        assertInstanceOf(Board.class, game.board());
        assertInstanceOf(Player.class, game.currentPlayer());
        assertInstanceOf(Player.class, game.opponent());
    }

    @Test
    void shouldSaveGameInRepository() throws CannotCreateBoard {
        Game game = gameService.startNewGame();

        // Check if game was saved correctly
        Optional<Game> gameFromRepository = gameRepository.findById(game.id());

        assertTrue(gameFromRepository.isPresent());
        gameAssertions.assertNotSameInstance(game, gameFromRepository.get());
        gameAssertions.assertIdAndPitRangeAreTheSame(game, gameFromRepository.get());
        gameAssertions.assertPlayerScoresAreTheSame(game, gameFromRepository.get());
        gameAssertions.assertBoardStateIsSame(game, gameFromRepository.get());
    }

    @Test
    void shouldUpdateGameStateAfterAMove() throws CannotCreateBoard, GameNotFound, CannotMakeMove {
        Game game = gameService.startNewGame();

        Game updatedGame = gameService.makeMove(game.id(), 0, 1);

        assertNotNull(updatedGame);
        gameAssertions.assertNotSameInstance(game, updatedGame);
        gameAssertions.assertIdAndPitRangeAreTheSame(game, updatedGame);
        gameAssertions.assertBoardStateIsNotTheSame(game, updatedGame);
        gameAssertions.assertPlayerScoresAreNotTheSame(game.firstPlayer(),updatedGame.firstPlayer());
    }

    @Test
    void shouldRetrieveGameFromId() throws CannotCreateBoard, GameNotFound {
        Game game = gameService.startNewGame();
        Game retrievedGame = gameService.getGame(game.id());

        gameAssertions.assertNotSameInstance(game, retrievedGame);
        gameAssertions.assertPlayerScoresAreTheSame(game, retrievedGame);
        gameAssertions.assertBoardStateIsSame(game, retrievedGame);
        gameAssertions.assertIdAndPitRangeAreTheSame(game, retrievedGame);
    }

    @Test
    void shouldDeleteGameFromId() throws CannotCreateBoard {
        Game game = gameService.startNewGame();

        gameService.deleteGame(game.id());

        assertThrows(GameNotFound.class, ()-> gameService.getGame(game.id()));
    }

    @Test
    void shouldThrowGameNotFoundIfGameDoesNotExist() {
        assertThrows(
            GameNotFound.class,
            ()-> gameService.getGame(
                GameId.fromString(UUID.randomUUID().toString())
            )
        );
    }

}