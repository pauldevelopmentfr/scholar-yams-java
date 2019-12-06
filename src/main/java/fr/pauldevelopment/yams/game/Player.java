package fr.pauldevelopment.yams.game;

public class Player {

    private boolean computer = false;
    private boolean gridFinished;
    private int id;
    private String name;

    /**
     * Human constructor
     *
     * @param name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Get the player id
     *
     * @return the player id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the player name
     *
     * @return the player name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Is the player a computer
     *
     * @return the computer
     */
    public boolean isComputer() {
        return this.computer;
    }

    /**
     * Is grid finished
     *
     * @return true if yes, false if not
     */
    public boolean isGridFinished() {
        return this.gridFinished;
    }

    /**
     * Set if the player is a computer
     *
     * @param isComputer
     */
    public void setComputer(boolean isComputer) {
        this.computer = isComputer;
    }

    /**
     * Set if grid is finished
     *
     * @param isGridFinished
     */
    public void setGridFinished(boolean isGridFinished) {
        this.gridFinished = isGridFinished;
    }

    /**
     * Set the player id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
}
