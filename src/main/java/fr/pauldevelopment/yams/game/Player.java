package fr.pauldevelopment.yams.game;

public interface Player {

    /**
     * Get the player id
     *
     * @return the player id
     */
    public int getId();

    /**
     * Get the player name
     *
     * @return the player name
     */
    public String getName();

    /**
     * Is grid finished
     */
    public boolean isGridFinished();

    /**
     * Set if grid is finished
     *
     * @param isGridFinished
     */
    public void setGridFinished(boolean isGridFinished);

    /**
     * Set the player id
     *
     * @param id
     */
    public void setId(int i);

}
