package sandbox.spaceplanner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static swingutils.components.ComponentFactory.icon;

public interface RenderableElement {

    void render(Graphics2D g);

    void setLocation(double x, double y);

    boolean contains(double x, double y);

    Rectangle2D getBounds2D();

    RenderableElement copy();

    abstract class AbstractRenderable extends Rectangle2D.Double implements RenderableElement {


        AbstractRenderable(float x, float y, int w, int h) {
            super(x, y, w, h);
        }

        @Override
        public void setLocation(double x, double y) {
            super.setFrame(x, y, getWidth(), getHeight());
        }

        @Override
        public RenderableElement copy() {
            return (RenderableElement) clone();
        }
    }

    class SolidBox extends AbstractRenderable {

        SolidBox(float x, float y, int w, int h) {
            super(x, y, w, h);
        }

        @Override
        public void render(Graphics2D g) {
            g.setPaint(Color.green);
            g.fill(this);
//            g.setPaint(Color.red);
//            g.draw(this);
        }
    }

    class Outline extends AbstractRenderable {

        Outline(float x, float y, int w, int h) {
            super(x, y, w, h);
        }

        @Override
        public void render(Graphics2D g) {
            g.setPaint(Color.green);
            g.fill(this);
            g.setPaint(Color.black);
            g.draw(this);
        }
    }

    class Icon extends AbstractRenderable {

        final Image icon;

        Icon(float x, float y, int w, int h, String resourceName) {
            super(x, y, w, h);
            this.icon = icon(resourceName).getImage();
        }

        @Override
        public void render(Graphics2D g) {
            g.drawImage(icon, getBounds().x, getBounds().y, getBounds().width, getBounds().height, null);
        }
    }

    class Texture extends AbstractRenderable {

        final BufferedImage icon;

        Texture(float x, float y, int w, int h, String resourceName) {
            super(x, y, w, h);
            try {
                this.icon = ImageIO.read(getClass().getResource(resourceName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void render(Graphics2D g) {
            g.setPaint(new TexturePaint(icon, new Rectangle(0,0,158,111)));
            g.fill(this);
        }
    }

}
