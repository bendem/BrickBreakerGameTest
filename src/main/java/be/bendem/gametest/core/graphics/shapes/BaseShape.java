package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Drawable;

/**
 * @author bendem
 */
public abstract class BaseShape implements Drawable {

    protected boolean filled;

    protected BaseShape(boolean filled) {
        this.filled = filled;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

}
