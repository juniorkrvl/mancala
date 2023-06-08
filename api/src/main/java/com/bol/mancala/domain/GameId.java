package com.bol.mancala.domain;

import java.util.Objects;
import java.util.UUID;

public class GameId {

    private final UUID value;

    private GameId(UUID value) {
        this.value = value;
    }

    public static GameId fromString(String gameId) throws GameIdInvalid {
        try {
            UUID id = UUID.fromString(gameId);
            return new GameId(id);
        } catch (IllegalArgumentException e) {
            throw GameIdInvalid.becauseValueIsNotAValidUUID(gameId);
        }
    }

    public static GameId generate(){
        return new GameId(UUID.randomUUID());
    }

    public UUID value(){
        return value;
    }

    public String toString(){
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameId gameId = (GameId) o;
        return value.equals(gameId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
