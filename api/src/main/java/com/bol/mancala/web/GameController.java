package com.bol.mancala.web;

import com.bol.mancala.application.GameService;
import com.bol.mancala.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "https://mancala.jvnior.com", allowedHeaders = "*", methods = { RequestMethod.GET,
        RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameStateResponse> startGame() throws CannotCreateBoard {
        Game game = gameService.startNewGame();
        return ResponseEntity.ok(GameStateResponse.fromGame(game));
    }

    @PutMapping
    public ResponseEntity<GameStateResponse> makeMove(
            @RequestBody MoveRequest move) throws GameIdInvalid, GameNotFound, CannotMakeMove {
        Game game = gameService.makeMove(GameId.fromString(move.gameId()), move.pit(), move.playerId());
        return ResponseEntity.ok(GameStateResponse.fromGame(game));
    }

    @GetMapping("{gameId}")
    public ResponseEntity<GameStateResponse> getGameState(
            @PathVariable("gameId") String gameId) throws GameNotFound, GameIdInvalid {
        Game game = gameService.getGame(GameId.fromString(gameId));
        return ResponseEntity.ok(GameStateResponse.fromGame(game));
    }

    @DeleteMapping("{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable("gameId") String gameId) throws GameIdInvalid {
        gameService.deleteGame(GameId.fromString(gameId));
        return ResponseEntity.noContent().build();
    }
}