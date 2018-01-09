package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swingutils.frame.RichFrame;
import swingutils.spring.application.SwingEntryPoint;

import javax.swing.*;
import java.awt.*;

import static swingutils.components.ComponentFactory.inScrollPane;
import static swingutils.components.ComponentFactory.label;
import static swingutils.layout.LayoutBuilders.flowLayout;

@Component
public class MainFrame extends RichFrame implements SwingEntryPoint {

    @Autowired
    private Canvas canvas;
    @Autowired
    private CanvasProperties canvasProperties;

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
                label("Scale (pixels per cm):"),
                new JSpinner(canvasProperties.scaleSpinnerModel),
                label("Grid size (cm):"),
                new JSpinner(canvasProperties.gridSpinnerModel)
        );
    }
}
