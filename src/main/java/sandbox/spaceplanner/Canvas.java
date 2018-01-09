package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
class Canvas extends JPanel {

    @Autowired
    private ShapesManager shapesManager;
    @Autowired
    private CanvasProperties canvasProperties;

    @PostConstruct
    void init() {
        setPreferredSize(new Dimension(5000, 5000));
        setBackground(Color.black);
        canvasProperties.whenChanged(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGrid((Graphics2D) g.create(), canvasProperties.getGridSpacingInCm(), canvasProperties.getPixelsPerCm());
        paintShapes((Graphics2D) g.create(), canvasProperties.getPixelsPerCm());
    }

    private void paintShapes(Graphics2D g, Float pixelsPerCm) {
        g.scale(pixelsPerCm, pixelsPerCm);
        shapesManager.forEach(s -> {
            g.setPaint(Color.green);
            g.fill(s);
            g.setPaint(Color.red);
            g.draw(s);
        });
        g.dispose();
    }

    private void paintGrid(Graphics2D g, Float gridSpacingInCm, Float pixelsPerCm) {
        g.setColor(Color.RED);
        Stroke dashed = new BasicStroke(1,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL,
                0,
                new float[]{1, 9},
                0);
        g.setStroke(dashed);
        for (int x = 0; x < getWidth(); x += gridSpacingInCm * pixelsPerCm) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += gridSpacingInCm * pixelsPerCm) {
            g.drawLine(0, y, getWidth(), y);
        }
        g.dispose();
    }
}