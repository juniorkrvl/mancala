package com.bol.mancala.web;

public record MoveRequest(String gameId, int pit, int playerId) {}
