package sandbox.spaceplanner.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElementCreator {

    @Autowired private ElementManager elementManager;

    public void addFridge(float x, float y) {
        RenderableElement.Box f = new RenderableElement.Box(x, y, 60, 60);
//        f.setFill();//todo: find a Paint for this image: "/img/fridge.jpg"
        add(f);
    }

    public void addWallV(float x, float y, int length) {
        add(new RenderableElement.Box(x, y, 10, length));
    }

    public void addWallH(float x, float y, int length) {
        add(new RenderableElement.Box(x, y, length, 10));
    }

    public void addOutline(float x, float y, int w, int h) {
        add(new RenderableElement.Box(x, y, w, h));
    }

    public void addCabinet(float x, float y, int w, int h) {
//        add(new RenderableElement.Texture(x, y, w, h, "/img/wood.png"));
        //todo: see addFridge
    }

    private void add(RenderableElement element) {
        elementManager.add(element);
    }

}
