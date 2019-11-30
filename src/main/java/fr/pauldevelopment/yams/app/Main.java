package fr.pauldevelopment.yams.app;

import java.util.ArrayList;
import java.util.List;

import fr.pauldevelopment.yams.game.Human;
import fr.pauldevelopment.yams.game.Player;

public class Main {

    public static void main(String[] args) {
        Engine engine = Engine.getInstance();

        List<Player> players = new ArrayList<>();
        Human player = new Human("Player 1");
        players.add(player);

        engine.initGame(players);
        engine.start(player);
    }
}
