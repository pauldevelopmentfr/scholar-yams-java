package fr.pauldevelopment.yams.app;

import java.util.ArrayList;
import java.util.List;

import fr.pauldevelopment.yams.game.Human;
import fr.pauldevelopment.yams.game.Player;

public class Main {

    public static void main(String[] args) {
        Engine engine = Engine.getInstance();

        List<Player> players = new ArrayList<>();
        Human player1 = new Human("Player 1");
        Human player2 = new Human("Player 2");
        players.add(player1);
        players.add(player2);

        engine.initGame(players);
        engine.start(player1);
    }
}
