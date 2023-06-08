package com.bol.mancala.domain;

/**
 * Store game in the database and GameState belongs to the web layer
 */
public final class Game {

    private final GameId id;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Board board;
    private Player currentPlayer;

    private static final int INITIAL_STONES_PER_PIT = 6;
    private static final int TOTAL_AMOUNT_PITS = 14;

    private Game() throws CannotCreateBoard {
        id = GameId.generate();
        board = new Board(TOTAL_AMOUNT_PITS, INITIAL_STONES_PER_PIT);
        firstPlayer = new Player(1, board.firstPlayerPitRange());
        secondPlayer = new Player(2, board.secondPlayerPitRange());
        currentPlayer = this.firstPlayer;
    }

    public Game(
        GameId id,
        Board board,
        Player firstPlayer,
        Player secondPlayer,
        Player currentPlayer
    ) {
        this.id = id;
        this.board = board;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentPlayer = currentPlayer;
    }

    public static Game start() throws CannotCreateBoard {
        return new Game();
    }

    public void makeMove(int pit, int playerId) throws CannotMakeMove {

        if (board.isGameOver()) {
            throw CannotMakeMove.becauseGameIsOver();
        }

        if (!isPlayerTurn(playerId)) {
            throw CannotMakeMove.becauseItIsNotPlayerTurn(currentPlayer);
        }

        Move move = new Move(pit, this.currentPlayer, this.opponent());
        this.currentPlayer = this.board.play(move);
    }

    public Player opponent() {
        return this.currentPlayer == this.firstPlayer ? this.secondPlayer : this.firstPlayer;
    }

    public GameId id() {
        return id;
    }

    public Board board() {
        return board;
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    public Player firstPlayer() {
        return firstPlayer;
    }

    public Player secondPlayer() {
        return secondPlayer;
    }

    private boolean isPlayerTurn(int id) {
        return id == currentPlayer.id();
    }
}
