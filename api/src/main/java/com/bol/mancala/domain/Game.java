package com.bol.mancala.domain;


/**
 * Represents a game match.
 * <p>
 * Each game is identified by a unique ID and involves two players.
 * The players and the board are created every time a new game starts.
 * The game class keeps track of who should be the next player to play
 * </p>
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

    /***
     * Creates a new instance of a game based on a given state
     * @param id the game identifier
     * @param board the board state
     * @param firstPlayer the first player
     * @param secondPlayer the second player
     * @param currentPlayer the current player
     */
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

    /***
     * Starts a new game
     * @return a new game instance
     * @throws CannotCreateBoard if the total number of pits is not even
     */
    public static Game start() throws CannotCreateBoard {
        return new Game();
    }

    /***
     * Makes a move for the current player
     * @param pit is the current player's selected pit
     * @param playerId the current player id
     * @throws CannotMakeMove if the game is over or if it is not the player's turn
     */
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

    /***
     * Represent the opponent player
     * @return the opposite of the current player
     */
    public Player opponent() {
        return this.currentPlayer == this.firstPlayer ? this.secondPlayer : this.firstPlayer;
    }

    /***
     * The game identifier
     * @return the game identifier
     */
    public GameId id() {
        return id;
    }

    /***
     * Board
     * @return the board for the current match
     */
    public Board board() {
        return board;
    }

    /***
     * Current player
     * @return the player that should play next
     */
    public Player currentPlayer() {
        return currentPlayer;
    }

    /***
     * First player
     * @return the first player
     */
    public Player firstPlayer() {
        return firstPlayer;
    }

    /***
     * Second player
     * @return the second player
     */
    public Player secondPlayer() {
        return secondPlayer;
    }

    /***
     * Checks whether it is the player's given id turn
     * @param id id of the player to check
     * @return true if the given id is the same as the current player
     */
    private boolean isPlayerTurn(int id) {
        return id == currentPlayer.id();
    }
}
