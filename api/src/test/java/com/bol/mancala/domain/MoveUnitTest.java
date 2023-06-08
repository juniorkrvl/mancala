package com.bol.mancala.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveUnitTest {

    private Player player;
    private Player opponent;

    @BeforeEach
    void setup(){
        int totalPits = 14;
        player = new Player(1, PitRange.forFirstPlayer(totalPits));
        opponent = new Player(2, PitRange.forSecondPlayer(totalPits));
    }

    @Test
    void shouldCreateValidMove() throws CannotMakeMove {
        Move move = new Move(0, player, opponent);

        assertEquals(0, move.startingPit());
        assertSame(player, move.player());
        assertSame(opponent, move.opponent());
    }

    @Test
    void shouldThrowIfPlayerDoesNotOwnThePit() {
        CannotMakeMove exception = assertThrows(CannotMakeMove.class, ()-> new Move(8, player, opponent));
        assertEquals(
            String.format(
                "Can not make move because selected pit does not belong to player. The pit range for player is between %d and %d",
                player.pitRange().start(),
                player.pitRange().end()
            ),
            exception.getMessage()
        );
    }

    @Test
    void shouldThrowIfPlayerChoseAMancalaPit() {
        CannotMakeMove exception = assertThrows(CannotMakeMove.class, ()-> new Move(6, player, opponent));
        assertEquals(
            String.format(
                "Can not make move because selected pit is a mancala. The pit range for player is between %d and %d",
                player.pitRange().start(),
                player.pitRange().end()
            ),
            exception.getMessage()
        );

    }
}