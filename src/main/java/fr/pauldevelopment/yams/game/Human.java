package fr.pauldevelopment.yams.game;

public class Human implements Player {

    private boolean gridFinished;
    private int id;
    private String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isGridFinished() {
        return this.gridFinished;
    }

    @Override
    public void setGridFinished(boolean isGridFinished) {
        this.gridFinished = isGridFinished;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
