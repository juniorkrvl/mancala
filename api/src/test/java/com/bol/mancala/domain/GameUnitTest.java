package com.bol.mancala.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameUnitTest {

    private Game game;

    @BeforeEach
    public void setUp() throws CannotCreateBoard {
        game = Game.start();
    }

    @Test
    void shouldCreateGameWithCorrectInitialSetup() {
        // Assert Board
        assertNotNull(game.board());
        assertFalse(game.board().isGameOver());
        assertEquals(14, game.board().pitsToArray().length);

        // Assert GameId
        assertNotNull(game.id());
        assertInstanceOf(UUID.class, game.id().value());

        // Assert player configuration
        assertEquals(game.currentPlayer().id(), 1);
        assertEquals(game.opponent().id(), 2);
    }

    @Test
    void shouldMakeMoveSuccessfully() throws CannotMakeMove {
        int initialStonesInPit = game.board().pitsToArray()[1];

        game.makeMove(1, game.currentPlayer().id());

        int stonesAfterMove = game.board().pitsToArray()[1];

        assertTrue(stonesAfterMove < initialStonesInPit);
        assertEquals(0, stonesAfterMove);
        assertNotEquals(game.currentPlayer().id(), 1);
    }

    @Test
    public void shouldThrowIfInvalidPlayerTurn() {
        int initialStonesInPit = game.board().pitsToArray()[0];

        assertThrows(CannotMakeMove.class, () -> game.makeMove(0, game.opponent().id()));

        int stonesAfterInvalidMove = game.board().pitsToArray()[0];

        assertEquals(stonesAfterInvalidMove, initialStonesInPit);
        assertEquals(game.currentPlayer().id(), 1);
    }

}