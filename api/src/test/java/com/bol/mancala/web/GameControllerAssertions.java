package com.bol.mancala.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameControllerAssertions {

    public void assertHttpStatus(MvcResult result, HttpStatus expectedStatus){
        int status = result.getResponse().getStatus();
        assertEquals(expectedStatus.value(), status, "Incorrect http status response");
    }

    public void assertResultDataIsAValidGameState(MvcResult result) throws Exception {
        Map<String, Object> response = parseResponse(result);

        // New game state
        int[] pits = new int[] {6,6,6,6,6,6,0,6,6,6,6,6,6,0};
        boolean gameOver = false;
        int currentPlayerId = 1;
        int firstPlayerMancalaPosition = 6;
        int secondPlayerMancalaPosition = 13;


        // Returned data
        @SuppressWarnings("unchecked")
        ArrayList<Integer> pitsArray = (ArrayList<Integer>) response.get("pits");

        // Assertions
        assertEquals(gameOver, response.get("gameOver"));
        assertArrayEquals(pits, pitsArray.stream().mapToInt(i->i).toArray());
        assertEquals(currentPlayerId, response.get("currentPlayerId"));
        assertEquals(firstPlayerMancalaPosition, response.get("firstPlayerMancalaPosition"));
        assertEquals(secondPlayerMancalaPosition, response.get("secondPlayerMancalaPosition"));
        assertEquals(36, response.get("gameId").toString().length());
    }

    public void assertResultDataIsAValidFirstMove(MvcResult result) throws Exception {
        Map<String, Object> response = parseResponse(result);

        // New game state
        int[] pits = new int[] {0,7,7,7,7,7,1,6,6,6,6,6,6,0};
        boolean gameOver = false;
        int currentPlayerId = 1;
        int firstPlayerMancalaPosition = 6;
        int secondPlayerMancalaPosition = 13;


        // Returned data
        @SuppressWarnings("unchecked")
        ArrayList<Integer> pitsArray = (ArrayList<Integer>) response.get("pits");

        // Assertions
        assertEquals(gameOver, response.get("gameOver"));
        assertArrayEquals(pits, pitsArray.stream().mapToInt(i->i).toArray());
        assertEquals(currentPlayerId, response.get("currentPlayerId"));
        assertEquals(firstPlayerMancalaPosition, response.get("firstPlayerMancalaPosition"));
        assertEquals(secondPlayerMancalaPosition, response.get("secondPlayerMancalaPosition"));
        assertEquals(36, response.get("gameId").toString().length());
    }

    public void assertErrorMessage(
            MvcResult result,
            int status,
            String message,
            String description
    ) throws Exception {
        Map<String, Object> response = parseResponse(result);

        // Assert error is not null
        assertNotNull(response.get("error"));

        // Assert error properties
        @SuppressWarnings("unchecked")
        Map<String, Object> error = (Map<String, Object>) response.get("error");

        Object errorMessage = error.get("message");
        Object errorDescription = error.get("description");
        Object errorStatus = error.get("status");

        assertEquals(errorMessage, message);
        assertEquals(errorDescription, description);
        assertEquals(errorStatus, status);
    }

    public Map<String,Object> parseResponse(MvcResult result) throws Exception {
        String contentAsString = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(contentAsString, new TypeReference<>() {
        });
    }

}
