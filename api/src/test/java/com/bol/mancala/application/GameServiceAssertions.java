package com.bol.mancala.application;

import com.bol.mancala.domain.Game;
import com.bol.mancala.domain.Player;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameServiceAssertions {
    public void assertNotSameInstance(Game expected, Game actual) {
        assertEquals(expected.id().toString(), actual.id().toString());
        assertNotSame(expected.id(), actual.id());
        assertNotSame(expected.firstPlayer(), actual.firstPlayer());
        assertNotSame(expected.secondPlayer(), actual.secondPlayer());
        assertNotSame(expected.currentPlayer(), actual.currentPlayer());
        assertNotSame(expected.board(), actual.board());
    }

    public void assertIdAndPitRangeAreTheSame(Game expected, Game actual) {
        // ID
        assertEquals(expected.id().toString(), actual.id().toString());

        // Pit Ranges
        assertEquals(expected.firstPlayer().pitRange().start(), actual.firstPlayer().pitRange().start());
        assertEquals(expected.firstPlayer().pitRange().end(), actual.firstPlayer().pitRange().end());
        assertEquals(expected.secondPlayer().pitRange().start(), actual.secondPlayer().pitRange().start());
        assertEquals(expected.secondPlayer().pitRange().end(), actual.secondPlayer().pitRange().end());
    }

    public void assertPlayerScoresAreTheSame(Game expected, Game actual) {
        assertEquals(expected.firstPlayer().id(), actual.firstPlayer().id());
        assertEquals(expected.secondPlayer().id(), actual.secondPlayer().id());
        assertEquals(expected.firstPlayer().score(), actual.firstPlayer().score());
        assertEquals(expected.secondPlayer().score(), actual.secondPlayer().score());
    }

    public void assertPlayerScoresAreNotTheSame(Player expected, Player actual) {
        assertEquals(expected.id(), actual.id());
        assertNotEquals(expected.score(), actual.score());
    }

    public void assertBoardStateIsSame(Game expected, Game actual) {
        assertArrayEquals(expected.board().pitsToArray(), actual.board().pitsToArray());
    }

    public void assertBoardStateIsNotTheSame(Game expected, Game actual) {
        assertFalse(Arrays.equals(expected.board().pitsToArray(), actual.board().pitsToArray()));
    }
}
