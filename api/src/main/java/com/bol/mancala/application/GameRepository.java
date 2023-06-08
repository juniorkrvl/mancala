package com.bol.mancala.application;

import com.bol.mancala.domain.Game;
import com.bol.mancala.domain.GameId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface GameRepository {
    void save(Game game);
    void update(Game game);
    Optional<Game> findById(GameId id);
    void delete(GameId id);
}
