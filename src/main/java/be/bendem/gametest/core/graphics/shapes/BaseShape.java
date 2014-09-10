package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.GraphicObject;

/**
 * @author bendem
 */
public abstract class BaseShape implements GraphicObject {

    protected boolean filled;
    protected boolean solid;

    protected BaseShape(boolean filled) {
        this(filled, true);
    }

    protected BaseShape(boolean filled, boolean solid) {
        this.filled = filled;
        this.solid = solid;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

}
