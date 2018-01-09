package sandbox.spaceplanner;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

@Component
class ShapesManager {

    private final LinkedList<MutableShape> shapes = new LinkedList<>();

    void forEach(Consumer<Shape> consumer) {
        getShapes().forEach(consumer::accept);
    }

    void addShape(MutableShape shape) {
        shapes.add(shape);
    }

    private List<MutableShape> getShapes() {
        return shapes;
    }

    Optional<MutableShape> findTopmostAt(float xInCm, float yInCm) {
        //todo: find last
        return getShapes().stream().filter(s -> s.contains(xInCm, yInCm)).findFirst();

    }
}
