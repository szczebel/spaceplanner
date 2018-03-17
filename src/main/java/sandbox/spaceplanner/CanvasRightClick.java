package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swingutils.layout.LayoutBuilders;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static javax.swing.JOptionPane.*;
import static swingutils.components.ComponentFactory.action;

@Component
public class CanvasRightClick extends MouseAdapter {

    @Autowired
    private ElementManager elementManager;
    @Autowired
    private ElementCreator elementCreator;
    @Autowired
    private CanvasProperties canvasProperties;
    @Autowired
    private Canvas canvas;

    @PostConstruct
    void install() {
        canvas.addMouseListener(this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) showPopup(e);
    }

    private void showPopup(MouseEvent e) {
        float xInCm = e.getX() / canvasProperties.getPixelsPerCm();
        float yInCm = e.getY() / canvasProperties.getPixelsPerCm();

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(submenu("Add wall",
                action("Vertical wall", () -> createWall(length -> elementCreator.addWallV(xInCm, yInCm, length.intValue()))),
                action("Horizontal wall", () -> createWall(length -> elementCreator.addWallH(xInCm, yInCm, length.intValue())))
        ));
        popupMenu.add(submenu("Add furniture",
                action("Fridge", () -> elementCreator.addFridge(xInCm, yInCm)),
                action("Cabinet 60x60", () -> elementCreator.addCabinet(xInCm, yInCm, 60, 60)),
                action("Cabinet 45x60", () -> elementCreator.addCabinet(xInCm, yInCm, 45, 60))
        ));
        popupMenu.add(submenu("Add outline",
                action("45x45", () -> elementCreator.addOutline(xInCm, yInCm, 45, 45)),
                action("45x100", () -> elementCreator.addOutline(xInCm, yInCm, 45, 100)),
                action("45x150", () -> elementCreator.addOutline(xInCm, yInCm, 45, 150)),
                action("45x200", () -> elementCreator.addOutline(xInCm, yInCm, 45, 200)),
                action("90x30", () -> elementCreator.addOutline(xInCm, yInCm, 90, 30)),
                action("90x25", () -> elementCreator.addOutline(xInCm, yInCm, 90, 25)),
                action("90x20", () -> elementCreator.addOutline(xInCm, yInCm, 90, 20)),
                action("Any...", () -> createOutline((w,h) -> elementCreator.addOutline(xInCm, yInCm, w.intValue(), h.intValue())))
        ));
        addSelectionMenu(popupMenu, xInCm, yInCm);
        popupMenu.show(canvas, e.getX(), e.getY());
    }

    private void addSelectionMenu(JPopupMenu popupMenu, float xInCm, float yInCm) {
        elementManager
                .findTopmostAt(xInCm, yInCm)
                .ifPresent(element -> {
                    popupMenu.addSeparator();
                    popupMenu.add(action("Copy", () -> {
                        RenderableElement copy = element.copy();
                        copy.setLocation(xInCm, yInCm);
                        elementManager.add(copy);
                    }));
                    popupMenu.add(action("Delete", () -> {
                        elementManager.remove(element);
                    }));
                });
    }

    private JMenu submenu(String name, Action... actions) {
        JMenu menu = new JMenu(name);
        Arrays.stream(actions).forEach(menu::add);
        return menu;
    }

    private void createWall(Consumer<Number> creator) {
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(300, 1, 1000, 10);
        if (OK_OPTION == showConfirmDialog(canvas.getParent(), new JSpinner(spinnerModel), "Wall length in cm?", OK_CANCEL_OPTION)) {
            creator.accept(spinnerModel.getNumber());
        }
    }

    private void createOutline(BiConsumer<Number, Number> creator) {
        SpinnerNumberModel w = new SpinnerNumberModel(300, 1, 1000, 10);
        SpinnerNumberModel h = new SpinnerNumberModel(300, 1, 1000, 10);
        if (OK_OPTION == showConfirmDialog(
                canvas.getParent(),
                LayoutBuilders.vBox(4,
                        new JSpinner(w),
                        new JSpinner(h)
                ),
                "Sizes in cm?", OK_CANCEL_OPTION)) {
            creator.accept(w.getNumber(), h.getNumber());
        }
    }
}
