package sandbox.spaceplanner;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public interface MutableShape extends Shape {

    void setLocation(double x, double y);

    class Rect extends Rectangle2D.Double implements MutableShape {

        Rect(float x, float y, int w, int h) {
            super(x, y, w, h);
        }

        @Override
        public void setLocation(double x, double y) {
            super.setFrame(x, y, getWidth(), getHeight());
        }
    }
}
