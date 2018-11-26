package sandbox.spaceplanner.model;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public interface RenderableElement {

    void render(Graphics2D g);

    void setLocation(double x, double y);

    boolean contains(double x, double y);

    Rectangle2D getBounds2D();

    RenderableElement copy();

    void setFill(Paint fill);
    void setOutline(Paint outline);

    class Renderer {

        static void render(Shape shape, Paint fill, Paint outline, Graphics2D g) {
            if(fill != null) {
                g.setPaint(fill);
                g.fill(shape);
            }
            if(outline != null) {
                g.setPaint(outline);
                g.draw(shape);
            }
        }
    }

    class Box extends Rectangle2D.Double implements RenderableElement {
        private Paint fill = Color.white;
        private Paint outline = Color.black;

        Box(float x, float y, int w, int h) {
            super(x, y, w, h);
        }

        @Override
        public void render(Graphics2D g) {
            Renderer.render(this, fill, outline ,g);
        }

        @Override
        public void setLocation(double x, double y) {
            super.setFrame(x, y, getWidth(), getHeight());
        }

        @Override
        public RenderableElement copy() {
            return (RenderableElement) clone();
        }

        @Override
        public void setFill(Paint fill) {
            this.fill = fill;
        }

        @Override
        public void setOutline(Paint outline) {
            this.outline = outline;
        }
    }
}
