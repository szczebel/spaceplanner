package sandbox.spaceplanner.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public interface Fill extends Serializable {

    static Fill withPaint(Paint paint) {
        return new PaintFill(paint);
    }

    static Fill withImage(String image) {
        return new StretchedImageFill(image);
    }

    void render(Graphics2D g, Shape shape);

    class PaintFill implements Fill {

        final Paint paint;

        public PaintFill(Paint paint) {
            this.paint = paint;
        }

        @Override
        public void render(Graphics2D g, Shape shape) {
            g.setPaint(paint);
            g.fill(shape);
        }
    }

    class StretchedImageFill implements Fill {
        final String imageFile;
        transient Image image;//todo: provide json marshalling, so this class can stop caring about reading images

        public StretchedImageFill(String imageFile) {
            this.imageFile = imageFile;
        }

        @Override
        public void render(Graphics2D g, Shape shape) {
            if(image==null) image = readImage();
            Rectangle bounds = shape.getBounds();
            g.drawImage(image, bounds.x, bounds.y, bounds.width, bounds.height, null);
        }

        private BufferedImage readImage() {
            try {
                return ImageIO.read(new File(imageFile));
            } catch (IOException e) {
                e.printStackTrace();
                return new BufferedImage(1,1,BufferedImage.TYPE_4BYTE_ABGR);//todo: provide a 'broken link' image
            }
        }
    }
}
