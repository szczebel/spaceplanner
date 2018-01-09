package sandbox.spaceplanner;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public interface MutableShape extends Shape {

    void setLocation(double x, double y);

    class Rect extends Rectangle2D.Double implements MutableShape {

        Rect(int w, int h) {
            super(0, 0, w, h);
        }

        @Override
        public void setLocation(double x, double y) {
            super.setFrame(x, y, getWidth(), getHeight());
        }
    }
}
