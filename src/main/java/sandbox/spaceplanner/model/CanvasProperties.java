package sandbox.spaceplanner.model;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CanvasProperties {

    SpinnerNumberModel scaleSpinnerModel = new SpinnerNumberModel(
            Float.valueOf(2f),
            Float.valueOf(0.5f),
            Float.valueOf(10.0f),
            Float.valueOf(0.1f));

    SpinnerNumberModel gridSpinnerModel = new SpinnerNumberModel(60, 1, 1000, 1);

    ButtonModel gridPainted = new JToggleButton.ToggleButtonModel();

    public int getGridSpacingInCm() {
        return gridSpinnerModel.getNumber().intValue();
    }

    public Float getPixelsPerCm() {
        return scaleSpinnerModel.getNumber().floatValue();
    }

    public void whenChanged(Runnable action) {
        scaleSpinnerModel.addChangeListener(e -> action.run());
        gridSpinnerModel.addChangeListener(e -> action.run());
        gridPainted.addChangeListener(e -> action.run());
    }

    public boolean isGridPainted() {
        return gridPainted.isSelected();
    }

    public SpinnerNumberModel scaleSpinnerModel() {
        return scaleSpinnerModel;
    }

    public SpinnerNumberModel gridSpinnerModel() {
        return gridSpinnerModel;
    }

    public ButtonModel gridPainted() {
        return gridPainted;
    }
}
