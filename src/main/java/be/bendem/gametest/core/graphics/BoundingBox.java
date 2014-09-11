package be.bendem.gametest.core.graphics;

/**
 * @author bendem
 */
public class BoundingBox {

    private final Point corner;
    private final int width;
    private final int height;

    public BoundingBox(Point corner, int width, int height) {
        this.corner = corner;
        this.width = width;
        this.height = height;
    }

    public Point getCorner() {
        return corner;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean doIntersect(Intersectable o) {
        return doIntersect(o.getBoundingBox());
    }

    public boolean doIntersect(BoundingBox bb) {
        int x1, x2, w1, w2, y1, y2, h1, h2;

        x1 = corner.getA();
        y1 = corner.getB();
        x2 = bb.getCorner().getA();
        y2 = bb.getCorner().getB();
        w1 = width;
        h1 = height;
        w2 = bb.getWidth();
        h2 = bb.getHeight();

        return !(x1 + w1 < x2 || x2 + w2 < x1 || y1 + h1 < y2 || y2 + h2 < y1);
    }

}
