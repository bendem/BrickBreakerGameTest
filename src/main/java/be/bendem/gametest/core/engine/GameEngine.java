package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;


/**
 * @author bendem
 */
public class GameEngine implements Killable {

    private final GameTest game;

    public GameEngine(GameTest game) {
        this.game = game;
    }

    @Override
    public void kill() {}

}
