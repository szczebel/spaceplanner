package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElementCreator {

    @Autowired private ElementManager elementManager;
    @Autowired private Canvas canvas;

    void addFridge(float x, float y) {
        addAndRepaint(new RenderableElement.Icon(x, y, 60, 60, "/img/fridge.jpg"));
    }

    void addWallV(float x, float y, int length) {
        addAndRepaint(new RenderableElement.SolidBox(x, y, 10, length));
    }

    void addWallH(float x, float y, int length) {
        addAndRepaint(new RenderableElement.SolidBox(x, y, length, 10));
    }


    void addCabinet(float x, float y, int w, int h) {
        addAndRepaint(new RenderableElement.Texture(x, y, w, h, "/img/wood.png"));
    }

    private void addAndRepaint(RenderableElement element) {
        elementManager.add(element);
        canvas.repaint();//todo: canvas should observe elementManager, listen to shape added/deleted and repaint accordingly
    }

}
