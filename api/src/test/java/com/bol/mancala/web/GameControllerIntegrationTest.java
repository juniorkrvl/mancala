package com.bol.mancala.web;

import com.bol.mancala.application.GameRepository;
import com.bol.mancala.application.MancalaApplication;
import com.bol.mancala.domain.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = MancalaApplication.class)
@AutoConfigureMockMvc
class GameControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GameRepository gameRepository;

    private GameControllerAssertions assertions;

    @BeforeEach
    public void setup(){
        assertions = new GameControllerAssertions();
    }

    @Test
    void shouldStartANewGame() throws Exception {
        // Setup
        Mockito.doNothing().when(gameRepository).save(Mockito.any(Game.class));

        // Test
        MvcResult result = mockMvc.perform(
            post("/api/game").contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert game was saved in the persistence layer
        Mockito.verify(gameRepository, times(1)).save(Mockito.any(Game.class));

        // Assert http status
        assertions.assertHttpStatus(result, HttpStatus.OK);

        // Assert game was returned correctly
        assertions.assertResultDataIsAValidGameState(result);
    }

    @Test
    void shouldReturnAGameState() throws Exception {
        Game game = Game.start();
        Mockito.when(gameRepository.findById(game.id())).thenReturn(Optional.of(game));

        MvcResult retrievedGame = mockMvc.perform(
                get("/api/game/" + game.id().toString())
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert http status
        assertions.assertHttpStatus(retrievedGame, HttpStatus.OK);

        // Assert game state
        assertions.assertResultDataIsAValidGameState(retrievedGame);
    }

    @Test
    void shouldMakeAMove() throws Exception {
        Game game = Game.start();

        Mockito.when(gameRepository.findById(game.id())).thenReturn(Optional.of(game));

        MoveRequest request = new MoveRequest(game.id().toString(), 0, 1);
        String jsonBody = new ObjectMapper().writeValueAsString(request);

        MvcResult updatedGame = mockMvc.perform(
            put("/api/game")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert http status
        assertions.assertHttpStatus(updatedGame, HttpStatus.OK);

        // Assert first move is correct
        assertions.assertResultDataIsAValidFirstMove(updatedGame);
    }

    @Test
    void shouldDeleteAGame() throws Exception {
        Game game = Game.start();
        Mockito.doNothing().when(gameRepository).delete(game.id());

        MvcResult result = mockMvc.perform(
            delete("/api/game/" + game.id().toString())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert http status
        assertions.assertHttpStatus(result, HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnErrorGameNotFound() throws Exception {
        MvcResult retrievedGame = mockMvc.perform(
                get("/api/game/a2d2adb9-3bad-4ae1-a074-f601cd39deb1")
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert http status
        assertions.assertHttpStatus(retrievedGame, HttpStatus.NOT_FOUND);

        // Assert first move is correct
        assertions.assertErrorMessage(
            retrievedGame,
            404,
            "Game with id a2d2adb9-3bad-4ae1-a074-f601cd39deb1 not found",
            "Not Found"
        );
    }

    @Test
    void shouldReturnErrorNotPlayerTurn() throws Exception {
        Game game = Game.start();

        Mockito.when(gameRepository.findById(game.id())).thenReturn(Optional.of(game));

        MoveRequest request = new MoveRequest(game.id().toString(), 0, 2);
        String jsonBody = new ObjectMapper().writeValueAsString(request);

        MvcResult result = mockMvc.perform(
                put("/api/game")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert http status
        assertions.assertHttpStatus(result, HttpStatus.BAD_REQUEST);

        // Assert first move is correct
        assertions.assertErrorMessage(
            result,
            400,
            "Oops! It's not your turn to play. It is the player 1 turn.",
            "Bad Request"
        );
    }

    @Test
    void shouldReturnErrorPitDoesNotBelongToPlayer() throws Exception {
        Game game = Game.start();

        Mockito.when(gameRepository.findById(game.id())).thenReturn(Optional.of(game));

        MoveRequest request = new MoveRequest(game.id().toString(), 8, 1);
        String jsonBody = new ObjectMapper().writeValueAsString(request);

        MvcResult result = mockMvc.perform(
                put("/api/game")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert http status
        assertions.assertHttpStatus(result, HttpStatus.BAD_REQUEST);

        // Assert first move is correct
        assertions.assertErrorMessage(
            result,
            400,
            "Can not make move because selected pit does not belong to player. The pit range for player is between 0 and 5",
            "Bad Request"
        );
    }

    @Test
    void shouldReturnErrorInvalidGameId() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/api/game/invalid_id" )
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        // Assert http status
        assertions.assertHttpStatus(result, HttpStatus.BAD_REQUEST);

        // Assert first move is correct
        assertions.assertErrorMessage(
            result,
            400,
            "Game id provided is not a valid UUID: invalid_id",
            "Bad Request"
        );
    }
}