package be.bendem.gametest.core.graphics;

/**
 * @author bendem
 */
public interface GraphicObject extends Drawable, Translatable, Intersectable {

    /**
     * Defines wether the object is solid or not (used in the collision checks).
     *
     * @return true if other object should collide with this one, false
     *         otherwise
     */
    public boolean isSolid();

}
