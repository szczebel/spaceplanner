package sandbox.spaceplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sandbox.spaceplanner.model.CanvasProperties;
import sandbox.spaceplanner.model.ElementManager;
import sandbox.spaceplanner.model.Fill;
import sandbox.spaceplanner.model.RenderableElement;
import sandbox.spaceplanner.view.Canvas;
import sandbox.spaceplanner.view.ColorSelector;
import sandbox.spaceplanner.view.FillSelector;
import swingutils.layout.LayoutBuilders;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.BiConsumer;

import static javax.swing.JOptionPane.*;
import static swingutils.components.ComponentFactory.action;

@Component
public class CanvasRightClick extends MouseAdapter {

    @Autowired ElementManager elementManager;
    @Autowired CanvasProperties canvasProperties;
    @Autowired Canvas canvas;
    @Autowired ColorSelector colorSelector;
    @Autowired FillSelector fillSelector;

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
        popupMenu.add(submenu("New",
                action("60x60", () -> elementManager.addBox(xInCm, yInCm, 60, 60)),
                action("60x30", () -> elementManager.addBox(xInCm, yInCm, 60, 30)),
                action("30x30", () -> elementManager.addBox(xInCm, yInCm, 30, 30)),
                action("45x45", () -> elementManager.addBox(xInCm, yInCm, 45, 45)),
                action("45x100", () -> elementManager.addBox(xInCm, yInCm, 45, 100)),
                action("45x150", () -> elementManager.addBox(xInCm, yInCm, 45, 150)),
                action("45x200", () -> elementManager.addBox(xInCm, yInCm, 45, 200)),
                action("90x30", () -> elementManager.addBox(xInCm, yInCm, 90, 30)),
                action("90x25", () -> elementManager.addBox(xInCm, yInCm, 90, 25)),
                action("90x20", () -> elementManager.addBox(xInCm, yInCm, 90, 20)),
                action("Other...", () -> newBox((w, h) -> elementManager.addBox(xInCm, yInCm, w.intValue(), h.intValue())))
        ));
        elementManager
                .findTopmostAt(xInCm, yInCm)
                .ifPresent(selection -> addSelectionMenu(selection, popupMenu, xInCm, yInCm));
        popupMenu.show(canvas, e.getX(), e.getY());
    }

    private void addSelectionMenu(RenderableElement selection, JPopupMenu popupMenu, float xInCm, float yInCm) {
        final ElementActions actions = new ElementActions(elementManager, selection, xInCm, yInCm);
        popupMenu.add(submenu("Selection",
                action("Copy", actions::copySelection),
                action("Delete", actions::deleteSelection),
                action("Change color fill", actions::changeColorFillOfSelection),
                action("Change image", actions::changeImageFillOfSelection),
                action("Change outline", actions::changeOutlineOfSelection),
                action("To front", actions::toFront),
                action("To back", actions::toBack)
        ));

    }

    private JMenu submenu(String name, Action... actions) {
        JMenu menu = new JMenu(name);
        Arrays.stream(actions).forEach(menu::add);
        return menu;
    }

    private void newBox(BiConsumer<Number, Number> creator) {
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

    class ElementActions {
        final ElementManager elementManager;
        final RenderableElement selection;
        final float xInCm;
        final float yInCm;

        ElementActions(ElementManager elementManager, RenderableElement selection, float xInCm, float yInCm) {
            this.elementManager = elementManager;
            this.selection = selection;
            this.xInCm = xInCm;
            this.yInCm = yInCm;
        }

        void copySelection() {
            RenderableElement copy = selection.copy();
            copy.setLocation(xInCm, yInCm);
            elementManager.add(copy);
        }

        void deleteSelection() {
            elementManager.remove(selection);
        }

        void changeColorFillOfSelection() {
            fillSelector.chooseColorFill().ifPresent(this::applyFillToSelection);
        }

        void changeImageFillOfSelection() {
            fillSelector.chooseImageFill().ifPresent(this::applyFillToSelection);
        }

        private void applyFillToSelection(Fill fill) {
            selection.setFill(fill);
            elementManager.elementUpdated();
        }

        void changeOutlineOfSelection() {
            colorSelector.chooseColor().ifPresent(selection::setOutline);
            elementManager.elementUpdated();
        }

        void toFront() {
            selection.setZ(selection.getZ() + 1);
            elementManager.elementUpdated();
        }

        void toBack() {
            selection.setZ(selection.getZ() - 1);
            elementManager.elementUpdated();
        }
    }
}
