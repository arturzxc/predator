package predator.ui;

import predator.core.Util;
import predator.features.Sense;

import javax.swing.*;

public class SensePnl extends JPanel {

    public SensePnl(Sense sense) {
        JSlider slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(300);
        slider.setValue((int) Util.convertHammerUnitsToMeters(sense.maxDistance));
        slider.addChangeListener(e -> {
            JSlider sourceSlider = (JSlider) e.getSource();
            int newValue = sourceSlider.getValue();
            sense.maxDistance = Util.convertMetersToHammerUnits(newValue);
        });
        add(new JLabel("Max distance in meters"));
        add(slider);
    }

}
