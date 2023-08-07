package predator.ui;

import predator.features.Sense;

import javax.swing.*;

public class SensePnl extends JPanel {

    public SensePnl(Sense sense) {
        JSlider slider = new JSlider();
        slider.setMinimum(50);
        slider.setMaximum(500);
        slider.setValue(sense.getMaxDistanceInMeters());
        slider.addChangeListener(e -> {
            JSlider sourceSlider = (JSlider) e.getSource();
            int newValue = sourceSlider.getValue();
            sense.setMaxDistanceInMeters(newValue);
        });
        add(new JLabel("Max Distance Visible (meters)"));
        add(slider);
    }

}
