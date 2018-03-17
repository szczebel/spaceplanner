package sandbox.spaceplanner;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    private List<RenderableElement> getElements() {
        return elements;
    }

    Optional<RenderableElement> findTopmostAt(float xInCm, float yInCm) {
        //todo: find last
        return getElements().stream().filter(s -> s.contains(xInCm, yInCm)).findFirst();

    }

    void add(RenderableElement element) {
        elements.add(element);
        fireChanged();
    }

    void remove(RenderableElement shape) {
        elements.remove(shape);
        fireChanged();
    }

    public void replaceAllWith(List<RenderableElement> elements) {
        this.elements.clear();
        this.elements.addAll(elements);
        fireChanged();
    }

    List<Runnable> observers = new ArrayList<>();
    public void whenChanged(Runnable observer) {
        observers.add(observer);
    }

    void fireChanged() {
        observers.forEach(Runnable::run);
    }
}
