package fr.pauldevelopment.yams.app;

import fr.pauldevelopment.yams.exceptions.TooMuchPlayersException;

public class Main {

    public static void main(String[] args) {
        Engine engine = Engine.getInstance();

        engine.start();
        engine.initGame();

        try {
            engine.run();
        } catch (TooMuchPlayersException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
