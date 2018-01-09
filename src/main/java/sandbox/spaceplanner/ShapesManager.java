package sandbox.spaceplanner;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
class ShapesManager {

    private final List<MutableShape> shapes = Arrays.asList(new MutableShape.Rect(60, 120), new MutableShape.Rect(120, 60));

    void forEach(Consumer<Shape> consumer) {
        getShapes().forEach(consumer::accept);
    }

    private Collection<MutableShape> getShapes() {
        return shapes;
    }

    Optional<MutableShape> findTopmostAt(float xInCm, float yInCm) {
        return getShapes().stream().filter(s -> s.contains(xInCm, yInCm)).findFirst();
    }
}
