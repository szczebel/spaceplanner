package sandbox.spaceplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swingutils.frame.RichFrame;
import swingutils.spring.application.SwingEntryPoint;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static swingutils.components.ComponentFactory.inScrollPane;
import static swingutils.components.ComponentFactory.label;
import static swingutils.components.ComponentFactory.withBorder;
import static swingutils.layout.LayoutBuilders.flowLayout;
import static swingutils.layout.LayoutBuilders.wrapInPanel;

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
//        add(withBorder(wrapInPanel(canvas), createEmptyBorder(50, 50, 50, 50)));
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
