package com.bol.mancala.application;

import com.bol.mancala.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryGameRepository implements GameRepository {
    private final Map<UUID, Game> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Game game) {
        storage.put(game.id().value(), game);
    }

    @Override
    public Optional<Game> findById(GameId id) {
        Game game = storage.get(id.value());

        if (game == null) {
            return Optional.empty();
        }

        /*
         * In a real world scenario, we will store the data in a table
         * and will reconstruct the object from the data retrieved,
         * so here I am reconstructing new instances of all the objects
         * from the game, so we have a complete new instance
         */

        Board board = new Board(game.board().pitsToArray());

        PitRange firstPlayerPitRange = PitRange.forRange(
            game.firstPlayer().pitRange().start(),
            game.firstPlayer().pitRange().end()
        );

        Player firstPlayer = new Player(
            game.firstPlayer().id(),
            firstPlayerPitRange
        );

        PitRange secondPlayerPitRange = PitRange.forRange(
            game.secondPlayer().pitRange().start(),
            game.secondPlayer().pitRange().end()
        );

        Player secondPlayer = new Player(
            game.secondPlayer().id(),
            secondPlayerPitRange
        );

        Player currentPlayer = game.currentPlayer().id() == firstPlayer.id() ? firstPlayer : secondPlayer;


        try {
            return Optional.of(
                new Game(
                    GameId.fromString(game.id().toString()),
                    board,
                    firstPlayer,
                    secondPlayer,
                    currentPlayer
                )
            );
        } catch (GameIdInvalid e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(GameId id) {
        storage.remove(id.value());
    }

    @Override
    public void update(Game game) {
        storage.put(game.id().value(), game);
    }
}
