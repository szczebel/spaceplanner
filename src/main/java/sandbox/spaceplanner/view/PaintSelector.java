package sandbox.spaceplanner.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

@Component
public class PaintSelector {

    @Autowired JComponent mainFrame;

    public Optional<Paint> choosePaint() {
        return Optional.ofNullable(JColorChooser.showDialog(mainFrame, "Pick a fill", null));
    }
}
