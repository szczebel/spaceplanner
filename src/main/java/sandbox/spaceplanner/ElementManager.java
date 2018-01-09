package sandbox.spaceplanner;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
class ElementManager {

    private final LinkedList<RenderableElement> elements = new LinkedList<>();

    void forEach(Consumer<RenderableElement> consumer) {
        getElements().forEach(consumer);
    }

    void add(RenderableElement element) {
        elements.add(element);
    }

    private List<RenderableElement> getElements() {
        return elements;
    }

    Optional<RenderableElement> findTopmostAt(float xInCm, float yInCm) {
        //todo: find last
        return getElements().stream().filter(s -> s.contains(xInCm, yInCm)).findFirst();

    }

    void remove(RenderableElement shape) {
        elements.remove(shape);
    }
}
