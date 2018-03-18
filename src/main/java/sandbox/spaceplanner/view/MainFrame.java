package sandbox.spaceplanner.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sandbox.spaceplanner.controller.FilePersister;
import sandbox.spaceplanner.model.CanvasProperties;
import swingutils.frame.RichFrame;
import swingutils.spring.application.SwingEntryPoint;

import javax.swing.*;
import java.awt.*;

import static swingutils.components.ComponentFactory.*;
import static swingutils.layout.LayoutBuilders.flowLayout;

@Component
public class MainFrame extends RichFrame implements SwingEntryPoint {

    @Autowired
    sandbox.spaceplanner.view.Canvas canvas;
    @Autowired
    CanvasProperties canvasProperties;
    @Autowired
    FilePersister filePersister;

    @Override
    public void startInEdt() {
        setTitle("Space planner");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(toolbar(), BorderLayout.NORTH);
        add(inScrollPane(canvas));
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);

    }

    private JComponent toolbar() {
        return flowLayout(
                button("Load", filePersister::load),
                button("Save", filePersister::save),
                label("Scale (pixels per cm):"),
                new JSpinner(canvasProperties.scaleSpinnerModel()),
                label("Grid size (cm):"),
                new JSpinner(canvasProperties.gridSpinnerModel()),
                checkBox("Grid on/off", canvasProperties.gridPainted())
        );
    }

    private JCheckBox checkBox(String text, ButtonModel model) {
        JCheckBox gridPainted = new JCheckBox(text);
        gridPainted.setModel(model);
        return gridPainted;
    }
}
