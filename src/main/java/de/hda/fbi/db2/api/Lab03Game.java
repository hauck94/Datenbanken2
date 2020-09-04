package de.hda.fbi.db2.api;

import java.util.List;

/**
 * API Class for lab03
 * Created by l.koehler on 05.08.2019.
 */
public abstract class Lab03Game {

    public void init() {}

    /**
     * You can use the data from lab01, this variable will be automatically set.
     */
    protected Lab01Data lab01Data;

    /**
     * You can use the EntityManager or other stuff from lab02,
     * this variable will be automatically set.
     */
    protected Lab02EntityManager lab02EntityManager;

    /**
     * Setter for Lab01Data.
     *
     * @param lab01Data lab01Data
     */
    public void setLab01Data(Lab01Data lab01Data) {
        this.lab01Data = lab01Data;
    }

    /**
     * Setter fro Lab02EntityManager.
     *
     * @param lab02EntityManager lab02EntityManager
     */
    public void setLab02EntityManager(Lab02EntityManager lab02EntityManager) {
        this.lab02EntityManager = lab02EntityManager;
    }

    /**
     * This method should create a game. For this you have to do the following things:
     * - Ask for player
     * - Ask for categories
     * - Ask for maximum questions per category and choose questions
     * <p>
     * You do not have to play the game here. This should only create a game
     *
     * @return the game entity object. IMPORTANT: This has to be a entity.
     */
    public abstract Object createGame();

    /**
     * Here you have to play the game 'game'.
     *
     * @param game the game that should be played
     */
    public abstract void playGame(Object game);

    /**
     * This should create a game with the given arguments.
     * You do not have to read data from console.
     * The game should be create only with the given arguments.
     * You do not have to play the game here.
     *
     * @param playerName name of the player
     * @param questions  chosen questions
     * @return a game object
     */
    public abstract Object createGame(String playerName, List<Object> questions);

    /**
     * Simulate a game play. You do not have to read anything from console.
     *
     * @param game given game
     */
    public abstract void simulateGame(Object game);

    /**
     * This should return the appropriate player for the given game.
     *
     * @param game given game for returning player
     * @return player for given game
     */
    public abstract Object getPlayer(Object game);

    /**
     * Return the player entity with given name.
     *
     * @param name name of the player
     * @return the player entity
     */
    public abstract Object getPlayer(String name);

    /**
     * return the right answers of a played game.
     *
     * @param game given game
     * @return number of right answers
     */
    public abstract int getRightAnswers(Object game);

    /**
     * persist the game object in the database.
     * @param game given game
     */
    public abstract void persistGame(Object game);
}
