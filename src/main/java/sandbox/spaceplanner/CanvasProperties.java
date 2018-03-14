package sandbox.spaceplanner;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
class CanvasProperties {

    SpinnerNumberModel scaleSpinnerModel = new SpinnerNumberModel(
            Float.valueOf(2f),
            Float.valueOf(0.5f),
            Float.valueOf(10.0f),
            Float.valueOf(0.1f));

    SpinnerNumberModel gridSpinnerModel = new SpinnerNumberModel(60, 1, 1000, 1);

    ButtonModel gridPainted = new JToggleButton.ToggleButtonModel();

    int getGridSpacingInCm() {
        return gridSpinnerModel.getNumber().intValue();
    }

    Float getPixelsPerCm() {
        return scaleSpinnerModel.getNumber().floatValue();
    }

    void whenChanged(Runnable action) {
        scaleSpinnerModel.addChangeListener(e -> action.run());
        gridSpinnerModel.addChangeListener(e -> action.run());
        gridPainted.addChangeListener(e -> action.run());
    }
}
