package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.Consumer;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static swingutils.components.ComponentFactory.action;

@Component
public class CanvasRightClick extends MouseAdapter {

    @Autowired
    private ShapesManager shapesManager;
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
        if(e.isPopupTrigger()) showPopup(e);
    }

    private void showPopup(MouseEvent e) {
        float xInCm = e.getX() / canvasProperties.getPixelsPerCm();
        float yInCm = e.getY() / canvasProperties.getPixelsPerCm();

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(submenu("Walls",
                action("Vertical wall", () -> createWall(length -> createBox(xInCm, yInCm, 10, length.intValue()))),
                action("Horizontal wall", () -> createWall(length -> createBox(xInCm, yInCm, length.intValue(), 10)))
        ));
        popupMenu.add(action("Create 60x60", () -> create60x60(xInCm, yInCm)));
        sddSelectionMenu(popupMenu, xInCm, yInCm);
        popupMenu.show(canvas, e.getX(), e.getY());
    }

    private void sddSelectionMenu(JPopupMenu popupMenu, float xInCm, float yInCm) {
        shapesManager.findTopmostAt(xInCm, yInCm).ifPresent(s -> {
            popupMenu.addSeparator();
            popupMenu.add(action("Delete", () -> {
                shapesManager.removeShape(s);
                canvas.repaint();//todo: canvas should observe shapesManager, listen to shape added/deleted and repaint accordingly
            }));
        });
    }

    private JMenu submenu(String name, Action... actions) {
        JMenu menu = new JMenu(name);
        Arrays.stream(actions).forEach(menu::add);
        return menu;
    }

    //todo: move some/all of the below to a 'controller'

    private void createWall(Consumer<Number> creator) {
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(300, 1, 1000, 10);
        if(OK_OPTION == showConfirmDialog(canvas.getParent(), new JSpinner(spinnerModel), "Wall length in cm?", OK_CANCEL_OPTION)) {
            creator.accept(spinnerModel.getNumber());
        }
    }

    private void create60x60(float x, float y) {
        createBox(x, y, 60, 60);
    }

    private void createBox(float x, float y, int w, int h) {
        shapesManager.addShape(new MutableShape.Rect(x, y, w, h));
        canvas.repaint();//todo: canvas should observe shapesManager, listen to shape added/deleted and repaint accordingly
    }

}
