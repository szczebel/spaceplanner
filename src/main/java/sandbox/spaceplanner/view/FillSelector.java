package sandbox.spaceplanner.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sandbox.spaceplanner.model.Fill;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

@Component
public class FillSelector {

    private final JFileChooser jFileChooser = new JFileChooser();
    @Autowired JComponent mainFrame;

    public Optional<Fill> chooseColorFill() {
        Color selectedColor = JColorChooser.showDialog(mainFrame, "Pick a fill", null);
        return Optional.ofNullable(selectedColor != null ? Fill.withPaint(selectedColor) : null);
    }

    public Optional<Fill> chooseImageFill() {
        if(JFileChooser.APPROVE_OPTION == jFileChooser.showOpenDialog(mainFrame)){
            return Optional.of(Fill.withImage(jFileChooser.getSelectedFile().getAbsolutePath()));
        }
        else return Optional.empty();
    }
}
