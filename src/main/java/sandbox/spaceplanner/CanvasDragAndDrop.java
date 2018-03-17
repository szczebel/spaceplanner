package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

@Component
public class CanvasDragAndDrop extends MouseAdapter {

    @Autowired
    private ElementManager elementManager;
    @Autowired
    private CanvasProperties canvasProperties;
    @Autowired
    private Canvas canvas;

    @PostConstruct
    void install() {
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<RenderableElement> draggedShape = Optional.empty();
    private double xOffset;
    private double yOffset;


    @Override
    public void mousePressed(MouseEvent e) {
        float xInCm = e.getX() / canvasProperties.getPixelsPerCm();
        float yInCm = e.getY() / canvasProperties.getPixelsPerCm();
        draggedShape = elementManager.findTopmostAt(xInCm, yInCm);
        if(draggedShape.isPresent()) {
            xOffset = xInCm - draggedShape.get().getBounds2D().getX();
            yOffset = yInCm - draggedShape.get().getBounds2D().getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        draggedShape = Optional.empty();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        draggedShape.ifPresent(shape -> {
            float xInCm = e.getX() / canvasProperties.getPixelsPerCm();
            float yInCm = e.getY() / canvasProperties.getPixelsPerCm();

            //todo: snap to grid on or off
            double newX = xInCm - xOffset;
            double newY = yInCm - yOffset;
            //if snap to grid {
            newX -= newX%canvasProperties.getGridSpacingInCm();
            newY -= newY%canvasProperties.getGridSpacingInCm();

            shape.setLocation(newX, newY);
            canvas.repaint();//todo this should not be needed
        });
    }

}
