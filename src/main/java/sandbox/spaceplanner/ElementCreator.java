package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElementCreator {

    @Autowired private ElementManager elementManager;

    void addFridge(float x, float y) {
        add(new RenderableElement.Icon(x, y, 60, 60, "/img/fridge.jpg"));
    }

    void addWallV(float x, float y, int length) {
        add(new RenderableElement.SolidBox(x, y, 10, length));
    }

    void addWallH(float x, float y, int length) {
        add(new RenderableElement.SolidBox(x, y, length, 10));
    }

    void addOutline(float x, float y, int w, int h) {
        add(new RenderableElement.Outline(x, y, w, h));
    }

    void addCabinet(float x, float y, int w, int h) {
        add(new RenderableElement.Texture(x, y, w, h, "/img/wood.png"));
    }

    private void add(RenderableElement element) {
        elementManager.add(element);
    }

}
