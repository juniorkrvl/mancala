package com.bol.mancala.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameIdUnitTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldGenerateValidGameId() {
        GameId gameId = GameId.generate();
        UUID id = gameId.value();

        assertInstanceOf(GameId.class, gameId);
        assertEquals(id.toString(), gameId.toString());
    }

    @Test
    void shouldCreateValidGameIdFromUUID() throws GameIdInvalid {
        GameId gameId = GameId.fromString("fed0b719-b88a-445f-898d-20a10446c0ee");
        UUID id = gameId.value();

        assertInstanceOf(GameId.class, gameId);
        assertEquals(id.toString(), gameId.toString());
    }

    @Test
    void shouldThrowInvalidGameId() {
        String notUuid = "not-a-uuid";
        GameIdInvalid exception = assertThrows(GameIdInvalid.class, () -> GameId.fromString(notUuid));
        assertEquals(String.format("Game id provided is not a valid UUID: %s", notUuid),exception.getMessage());
    }

}