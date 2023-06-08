package com.bol.mancala.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BoardUnitTest {

    @Test
    void shouldCreateBoardWithCorrectPitConfiguration() throws CannotCreateBoard {
        int totalNumberPits = 14;
        int stonesPerPit = 6;

        Board board = new Board(totalNumberPits, stonesPerPit);

        PitRange playerRange = PitRange.forFirstPlayer(totalNumberPits);
        PitRange opponentRange = PitRange.forSecondPlayer(totalNumberPits);

        // Make sure first and second player pit ranges are correct
        assertEquals(board.firstPlayerPitRange().start(), playerRange.start());
        assertEquals(board.firstPlayerPitRange().end(), playerRange.end());
        assertEquals(board.secondPlayerPitRange().start(), opponentRange.start());
        assertEquals(board.secondPlayerPitRange().end(), opponentRange.end());

        // Game just began
        assertFalse(board.isGameOver());

        // Pit amount is correct
        assertEquals(totalNumberPits, board.pitsToArray().length);

        // Assert board pits contain the correct number of stones
        int[] expectedPits = new int[totalNumberPits];
        for (int i = 0; i < totalNumberPits; i++) {
            if (i == playerRange.mancalaPosition() || i == opponentRange.mancalaPosition()) {
                continue;
            }
            expectedPits[i] = stonesPerPit;
        }

        assertArrayEquals(expectedPits, board.pitsToArray());
    }

    @Test
    void shouldReturnGameIsOverWhenEitherPlayersHaveNoStones() throws CannotCreateBoard, CannotMakeMove {
        int totalNumberPits = 6;
        int stonesPerPit = 1;

        Board board = new Board(totalNumberPits, stonesPerPit);
        Player player = new Player(1, board.firstPlayerPitRange());
        Player opponent = new Player(2, board.secondPlayerPitRange());

        // Empty player's pits
        board.play(new Move(0, player, opponent));
        board.play(new Move(1, player, opponent));


        // Assert game is over
        assertArrayEquals(board.pitsToArray(), new int[]{0,0,1,0,0,3});
        assertTrue(board.isGameOver());

        // Assert final score is correct
        assertEquals(1, player.score());
        assertEquals(3, opponent.score());
    }

    @Test
    void shouldNotAllowMoveIfGameIsOver() throws CannotCreateBoard, CannotMakeMove {
        int totalNumberPits = 6;
        int stonesPerPit = 1;

        Board board = new Board(totalNumberPits, stonesPerPit);
        Player player = new Player(1, board.firstPlayerPitRange());
        Player opponent = new Player(2, board.secondPlayerPitRange());

        // Empty player's pits
        board.play(new Move(0, player, opponent));
        board.play(new Move(1, player, opponent));

        // Assert the next move cannot be performed because game is over
        assertThrows(CannotMakeMove.class, ()-> board.play(new Move(7, opponent, player)));
    }

    @Test
    void shouldCaptureStonesWhenLastStoneLandsInPlayerEmptyPit() throws CannotCreateBoard, CannotMakeMove {
        int totalNumberPits = 6;
        int stonesPerPit = 3;

        Board board = new Board(totalNumberPits, stonesPerPit);
        Player player = new Player(1, board.firstPlayerPitRange());
        Player opponent = new Player(2, board.secondPlayerPitRange());

        /*
         * Simulate moves that will make the player
         * capture the opponent stones
         */
        board.play(new Move(0, player, opponent));
        Player nextTurn = board.play(new Move(1, player, opponent));

        // Assert pit configuration after the moves
        assertArrayEquals(board.pitsToArray(), new int[]{0,0,7,0,0,5});

        // Make sure the player has the same score as its mancala pit
        assertEquals(7, player.score());

        // Assert next turn is for the opponent
        assertSame(nextTurn, opponent);
    }

    @Test
    void shouldReturnPlayerAgainForNextTurnWhenLastStoneLandsInMancala() throws CannotCreateBoard, CannotMakeMove {
        int totalNumberPits = 6;
        int stonesPerPit = 2;

        Board board = new Board(totalNumberPits, stonesPerPit);
        Player player = new Player(1, board.firstPlayerPitRange());
        Player opponent = new Player(2, board.secondPlayerPitRange());

        /*
         * Simulate moves that will make the player
         * capture the opponent stones
         */
        Player nextTurn = board.play(new Move(0, player, opponent));

        // Assert next turn is still for the player
        assertSame(nextTurn, player);
    }

    @Test
    void shouldReturnOpponentForNextTurnWhenLastStoneDoesNotLandInPlayerMancala() throws CannotCreateBoard, CannotMakeMove {
        int totalNumberPits = 6;
        int stonesPerPit = 2;

        Board board = new Board(totalNumberPits, stonesPerPit);
        Player player = new Player(1, board.firstPlayerPitRange());
        Player opponent = new Player(2, board.secondPlayerPitRange());

        /*
         * Simulate moves that will make the player
         * capture the opponent stones
         */
        Player nextTurn = board.play(new Move(1, player, opponent));

        // Assert next turn is still for the opponent
        assertSame(nextTurn, opponent);
    }

    @Test
    void shouldThrowWhenTotalAmountOfPitsIsNotEven(){
        int totalNumberPits = 15;
        int stonesPerPit = 6;

        CannotCreateBoard exception = assertThrows(
            CannotCreateBoard.class,
            ()-> new Board(totalNumberPits, stonesPerPit)
        );

        assertEquals(
            String.format(
                "Can not create board because the total amount of pits should be even. The number given was %d",
                totalNumberPits
            ),
            exception.getMessage()
        );
    }


}
