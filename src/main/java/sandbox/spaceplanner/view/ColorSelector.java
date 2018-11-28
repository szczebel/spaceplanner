package sandbox.spaceplanner.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

@Component
public class ColorSelector {

    @Autowired JComponent mainFrame;

    public Optional<Paint> chooseColor() {
        return Optional.ofNullable(JColorChooser.showDialog(mainFrame, "Pick a color", null));
    }
}
