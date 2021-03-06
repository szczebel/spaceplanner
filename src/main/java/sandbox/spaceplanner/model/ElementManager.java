package sandbox.spaceplanner.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Component
public class ElementManager {

    private final LinkedList<RenderableElement> elements = new LinkedList<>();

    public void forEach(Consumer<RenderableElement> consumer) {
        getElements().forEach(consumer);
    }

    private List<RenderableElement> getElements() {
        return elements;
    }

    public Optional<RenderableElement> findTopmostAt(float xInCm, float yInCm) {
        List<RenderableElement> candidates = getElements().stream()
                .filter(s -> s.contains(xInCm, yInCm))
                .collect(toList());
        if(candidates.size() == 0) return Optional.empty();
        else return Optional.of(candidates.get(candidates.size()-1));

    }

    public void addBox(float x, float y, int w, int h) {
        add(new RenderableElement.Box(x, y, w, h));
    }

    public void add(RenderableElement element) {
        elements.add(element);
        elementUpdated();
    }

    public void remove(RenderableElement shape) {
        elements.remove(shape);
        fireChanged();
    }

    public void replaceAllWith(List<RenderableElement> elements) {
        this.elements.clear();
        this.elements.addAll(elements);
        elementUpdated();
    }

    List<Runnable> observers = new ArrayList<>();
    public void whenChanged(Runnable observer) {
        observers.add(observer);
    }

    public void fireChanged() {
        observers.forEach(Runnable::run);
    }

    public void elementUpdated() {
        elements.sort(comparingInt(RenderableElement::getZ));
        fireChanged();
    }
}
