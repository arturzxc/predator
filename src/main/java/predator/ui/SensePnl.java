package predator.ui;

import predator.core.Settings;
import predator.enums.GlowEnableStyle;
import predator.enums.GlowModeBodyStyle;
import predator.enums.GlowModeBorderStyle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SensePnl extends JPanel {

    private final Settings settings;
    private final JPanel presetSection;
    private final JPanel customSection;

    public SensePnl(Settings settings) {
        this.settings = settings;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        {//top section
            JPanel topSection = new JPanel();
            topSection.setBorder(createBorder("General settings"));
            topSection.setLayout(new GridLayout(0, 2));
            topSection.setMaximumSize(new Dimension(2000, 200));
            add(topSection);
            createONOFFToggle(topSection);
            createMaxDistanceSlider(topSection);
            createUseCustomStylesToggle(topSection);
        }

        {//preset styles section
            presetSection = new JPanel();
            presetSection.setBorder(createBorder("Preset settings"));
            presetSection.setLayout(new GridLayout(0, 2));
            presetSection.setMaximumSize(new Dimension(2000, 200));
            add(presetSection);
            createPresetSelects(presetSection);
        }

        { //custom styles section
            customSection = new JPanel();
            customSection.setBorder(createBorder("Custom settings"));
            customSection.setLayout(new GridLayout(0, 1));
            add(customSection);
            createCustomVisibleStyles(customSection);
            createCustomInVisibleStyles(customSection);
        }

        //show one of the preset or custom panels
        if (settings.readBoolean(Settings.Key.SENSE_CUSTOM_COLOR_ON)) {
            customSection.setVisible(true);
            presetSection.setVisible(false);
        } else {
            customSection.setVisible(false);
            presetSection.setVisible(true);
        }
    }

    private void createONOFFToggle(JPanel container) {
        container.add(new JLabel("ON/OFF"));
        JCheckBox onOff = new JCheckBox();
        onOff.setBackground(new Color(1, 1, 1, 0));
        onOff.setSelected(settings.readBoolean(Settings.Key.SENSE_ON));
        onOff.addItemListener(e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            boolean isChecked = source.isSelected();
            settings.save(Settings.Key.SENSE_ON, String.valueOf(isChecked));
        });
        container.add(onOff);
    }

    private void createMaxDistanceSlider(JPanel container) {
        JLabel lbl = new JLabel();
        container.add(lbl);
        JSlider slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(1000);
        int val = settings.readInteger(Settings.Key.SENSE_MAX_DISTANCE_METERS);
        slider.setValue(val);
        lbl.setText("Max distance: " + val + "m");
        slider.addChangeListener(e -> {
            JSlider sourceSlider = (JSlider) e.getSource();
            int newVal = sourceSlider.getValue();
            settings.save(Settings.Key.SENSE_MAX_DISTANCE_METERS, newVal);
            lbl.setText("Max distance: " + newVal + "m");
        });
        container.add(slider);
    }

    private void createUseCustomStylesToggle(JPanel container) {
        container.add(new JLabel("Use custom styling"));
        JCheckBox onOffCustomColors = new JCheckBox();
        onOffCustomColors.setBackground(new Color(1, 1, 1, 0));
        onOffCustomColors.setSelected(settings.readBoolean(Settings.Key.SENSE_CUSTOM_COLOR_ON));
        onOffCustomColors.addItemListener(e -> {
            JCheckBox source = (JCheckBox) e.getSource();
            boolean isChecked = source.isSelected();
            settings.save(Settings.Key.SENSE_CUSTOM_COLOR_ON, String.valueOf(isChecked));
            if (isChecked) {
                customSection.setVisible(true);
                presetSection.setVisible(false);
            } else {
                customSection.setVisible(false);
                presetSection.setVisible(true);
            }
        });
        container.add(onOffCustomColors);
    }

    private void createPresetSelects(JPanel container) {
        {
            container.add(new JLabel("Enemy visible preset:"));
            JComboBox<GlowEnableStyle> cb = new JComboBox<>(GlowEnableStyle.values());
            GlowEnableStyle preset = GlowEnableStyle.findByValue(settings.readInteger(Settings.Key.SENSE_PRESET_VISIBLE_STYLE));
            cb.setSelectedItem(preset);
            cb.addActionListener(evt -> {
                GlowEnableStyle selectedOption = (GlowEnableStyle) cb.getSelectedItem();
                if (selectedOption == null) throw new RuntimeException("Invalid option");
                settings.save(Settings.Key.SENSE_PRESET_VISIBLE_STYLE, selectedOption.value);
            });
            container.add(cb);
        }
        {
            container.add(new JLabel("Enemy in-visible preset:"));
            JComboBox<GlowEnableStyle> cb = new JComboBox<>(GlowEnableStyle.values());
            GlowEnableStyle preset = GlowEnableStyle.findByValue(settings.readInteger(Settings.Key.SENSE_PRESET_INVISIBLE_STYLE));
            cb.setSelectedItem(preset);
            cb.addActionListener(evt -> {
                GlowEnableStyle selectedOption = (GlowEnableStyle) cb.getSelectedItem();
                if (selectedOption == null) throw new RuntimeException("Invalid option");
                settings.save(Settings.Key.SENSE_PRESET_INVISIBLE_STYLE, selectedOption.value);
            });
            container.add(cb);
        }
    }

    private void createCustomVisibleStyles(JPanel container) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 2));
        myPanel.setBorder(createBorder("Visible enemies"));
        container.add(myPanel);

        {//RED
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(0);
            slider.setMaximum(100);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_RED);
            slider.setValue(val);
            lbl.setText("Red " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_RED, newValue);
                lbl.setText("Red " + newValue);
            });
            myPanel.add(slider);
        }
        {//GREEN
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(0);
            slider.setMaximum(100);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_GREEN);
            slider.setValue(val);
            lbl.setText("Green " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_GREEN, newValue);
                lbl.setText("Green " + newValue);
            });
            myPanel.add(slider);
        }
        {//BLUE
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(0);
            slider.setMaximum(100);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_BLUE);
            slider.setValue(val);
            lbl.setText("Blue " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_BLUE, newValue);
                lbl.setText("Blue " + newValue);
            });
            myPanel.add(slider);
        }
        {//BODY STYLE
            myPanel.add(new JLabel("Body style"));
            JComboBox<GlowModeBodyStyle> cb = new JComboBox<>(GlowModeBodyStyle.values());
            GlowModeBodyStyle preset = GlowModeBodyStyle.findByValue(settings.readInteger(Settings.Key.SENSE_CUSTOM_MODE_VISIBLE_BODY_STYLE));
            cb.setSelectedItem(preset);
            cb.addActionListener(evt -> {
                GlowModeBodyStyle selectedOption = (GlowModeBodyStyle) cb.getSelectedItem();
                if (selectedOption == null) throw new RuntimeException("Invalid option");
                settings.save(Settings.Key.SENSE_CUSTOM_MODE_VISIBLE_BODY_STYLE, selectedOption.value);
            });
            myPanel.add(cb);
        }
        {//BORDER STYLE
            myPanel.add(new JLabel("Border style"));
            JComboBox<GlowModeBorderStyle> cb = new JComboBox<>(GlowModeBorderStyle.values());
            GlowModeBorderStyle preset = GlowModeBorderStyle.findByValue(settings.readInteger(Settings.Key.SENSE_CUSTOM_MODE_VISIBLE_BORDER_STYLE));
            cb.setSelectedItem(preset);
            cb.addActionListener(evt -> {
                GlowModeBorderStyle selectedOption = (GlowModeBorderStyle) cb.getSelectedItem();
                if (selectedOption == null) throw new RuntimeException("Invalid option");
                settings.save(Settings.Key.SENSE_CUSTOM_MODE_VISIBLE_BORDER_STYLE, selectedOption.value);
            });
            myPanel.add(cb);
        }
        {//BORDER WIDTH
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(32);
            slider.setMaximum(127);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_MODE_VISIBLE_BORDER_WIDTH);
            slider.setValue(val);
            lbl.setText("Border width " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_MODE_VISIBLE_BORDER_WIDTH, newValue);
                lbl.setText("Border width " + newValue);
            });
            myPanel.add(slider);
        }
    }

    private void createCustomInVisibleStyles(JPanel container) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 2));
        myPanel.setBorder(createBorder("InVisible enemies"));
        container.add(myPanel);

        {//RED
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(0);
            slider.setMaximum(100);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_RED);
            slider.setValue(val);
            lbl.setText("Red " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_RED, newValue);
                lbl.setText("Red " + newValue);
            });
            myPanel.add(slider);
        }
        {//GREEN
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(0);
            slider.setMaximum(100);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_GREEN);
            slider.setValue(val);
            lbl.setText("Green " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_GREEN, newValue);
                lbl.setText("Green " + newValue);
            });
            myPanel.add(slider);
        }
        {//BLUE
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(0);
            slider.setMaximum(100);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_BLUE);
            slider.setValue(val);
            lbl.setText("Blue " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_BLUE, newValue);
                lbl.setText("Blue " + newValue);
            });
            myPanel.add(slider);
        }
        {//BODY STYLE
            myPanel.add(new JLabel("Body style"));
            JComboBox<GlowModeBodyStyle> cb = new JComboBox<>(GlowModeBodyStyle.values());
            GlowModeBodyStyle preset = GlowModeBodyStyle.findByValue(settings.readInteger(Settings.Key.SENSE_CUSTOM_MODE_INVISIBLE_BODY_STYLE));
            cb.setSelectedItem(preset);
            cb.addActionListener(evt -> {
                GlowModeBodyStyle selectedOption = (GlowModeBodyStyle) cb.getSelectedItem();
                if (selectedOption == null) throw new RuntimeException("Invalid option");
                settings.save(Settings.Key.SENSE_CUSTOM_MODE_INVISIBLE_BODY_STYLE, selectedOption.value);
            });
            myPanel.add(cb);
        }
        {//BORDER STYLE
            myPanel.add(new JLabel("Border style"));
            JComboBox<GlowModeBorderStyle> cb = new JComboBox<>(GlowModeBorderStyle.values());
            GlowModeBorderStyle preset = GlowModeBorderStyle.findByValue(settings.readInteger(Settings.Key.SENSE_CUSTOM_MODE_INVISIBLE_BORDER_STYLE));
            cb.setSelectedItem(preset);
            cb.addActionListener(evt -> {
                GlowModeBorderStyle selectedOption = (GlowModeBorderStyle) cb.getSelectedItem();
                if (selectedOption == null) throw new RuntimeException("Invalid option");
                settings.save(Settings.Key.SENSE_CUSTOM_MODE_INVISIBLE_BORDER_STYLE, selectedOption.value);
            });
            myPanel.add(cb);
        }
        {//BORDER WIDTH
            JLabel lbl = new JLabel();
            myPanel.add(lbl);
            JSlider slider = new JSlider();
            slider.setMinimum(32);
            slider.setMaximum(127);
            int val = settings.readInteger(Settings.Key.SENSE_CUSTOM_MODE_INVISIBLE_BORDER_WIDTH);
            slider.setValue(val);
            lbl.setText("Border width " + val);
            slider.addChangeListener(e -> {
                JSlider sourceSlider = (JSlider) e.getSource();
                int newValue = sourceSlider.getValue();
                settings.save(Settings.Key.SENSE_CUSTOM_MODE_INVISIBLE_BORDER_WIDTH, newValue);
                lbl.setText("Border width " + newValue);
            });
            myPanel.add(slider);
        }
    }

    private Border createBorder(String title) {
        Border titledBorder = BorderFactory.createTitledBorder(title);
        Border emptyBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        return BorderFactory.createCompoundBorder(titledBorder, emptyBorder);
    }

}
