package predator.ui;

import predator.core.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPnl extends JPanel {

    public SettingsPnl(Settings ss) {
        JTabbedPane mainContainer = new JTabbedPane();
        add(mainContainer);

        //Sense
        {
            // container
            String containerTitle = "Sense";
            JPanel container = new JPanel();
            container.setBorder(new EmptyBorder(20, 20, 20, 20));
            container.setLayout(new GridLayout(0, 2));
            mainContainer.addTab(containerTitle, container);

            //on/off
            {
                container.add(new JLabel("ON/OFF"));
                JCheckBox onOff = new JCheckBox();
                onOff.setBackground(new Color(1, 1, 1, 0));
                onOff.setSelected(ss.readBoolean(Settings.Key.SENSE_ON));
                onOff.addItemListener(e -> {
                    JCheckBox source = (JCheckBox) e.getSource();
                    boolean isChecked = source.isSelected();
                    ss.save(Settings.Key.SENSE_ON, String.valueOf(isChecked));
                });
                container.add(onOff);
            }

            //max distance
            {
                container.add(new JLabel("MAX DISTANCE IN METERS"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(1000);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_MAX_DISTANCE_METERS));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_MAX_DISTANCE_METERS, newValue);
                });
                container.add(slider);
            }

            //visible style preset
            {
                container.add(new JLabel("VISIBLE STYLE"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(124);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_PRESET_VISIBLE_STYLE));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_PRESET_VISIBLE_STYLE, newValue);
                });
                container.add(slider);
            }

            //invisible style preset
            {
                container.add(new JLabel("INVISIBLE STYLE"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(124);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_PRESET_INVISIBLE_STYLE));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_PRESET_INVISIBLE_STYLE, newValue);
                });
                container.add(slider);
            }

            //use custom colors?
            {
                container.add(new JLabel("USE CUSTOM COLORS"));
                JCheckBox onOffCustomColors = new JCheckBox();
                onOffCustomColors.setBackground(new Color(1, 1, 1, 0));
                onOffCustomColors.setSelected(ss.readBoolean(Settings.Key.SENSE_CUSTOM_COLOR_ON));
                onOffCustomColors.addItemListener(e -> {
                    JCheckBox source = (JCheckBox) e.getSource();
                    boolean isChecked = source.isSelected();
                    ss.save(Settings.Key.SENSE_CUSTOM_COLOR_ON, String.valueOf(isChecked));
                });
                container.add(onOffCustomColors);
            }

            //visible custom color red
            {
                container.add(new JLabel("VISIBLE CUSTOM COLOR RED"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(100);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_RED));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_RED, newValue);
                });
                container.add(slider);
            }

            //visible custom color green
            {
                container.add(new JLabel("VISIBLE CUSTOM COLOR GREEN"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(100);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_GREEN));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_GREEN, newValue);
                });
                container.add(slider);
            }

            //visible custom color blue
            {
                container.add(new JLabel("VISIBLE CUSTOM COLOR BLUE"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(100);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_BLUE));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_CUSTOM_COLOR_VISIBLE_BLUE, newValue);
                });
                container.add(slider);
            }


            //invisible custom color red
            {
                container.add(new JLabel("INVISIBLE CUSTOM COLOR RED"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(100);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_RED));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_RED, newValue);
                });
                container.add(slider);
            }

            //invisible custom color green
            {
                container.add(new JLabel("INVISIBLE CUSTOM COLOR GREEN"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(100);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_GREEN));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_GREEN, newValue);
                });
                container.add(slider);
            }

            //invisible custom color blue
            {
                container.add(new JLabel("INVISIBLE CUSTOM COLOR BLUE"));
                JSlider slider = new JSlider();
                slider.setMinimum(0);
                slider.setMaximum(100);
                slider.setValue(ss.readInteger(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_BLUE));
                slider.addChangeListener(e -> {
                    JSlider sourceSlider = (JSlider) e.getSource();
                    int newValue = sourceSlider.getValue();
                    ss.save(Settings.Key.SENSE_CUSTOM_COLOR_INVISIBLE_BLUE, newValue);
                });
                container.add(slider);
            }
        }

        //TRIGGERBOT
        {
            // container
            String containerTitle = "TRIGGER BOT";
            JPanel container = new JPanel();
            container.setBorder(new EmptyBorder(20, 20, 20, 20));
            container.setLayout(new GridLayout(0, 2));
            mainContainer.addTab(containerTitle, container);

            //on/off
            container.add(new JLabel("ON/OFF"));
            JCheckBox onOff = new JCheckBox();
            onOff.setSelected(ss.readBoolean(Settings.Key.TRIGGERBOT_ON));
            onOff.addItemListener(e -> {
                JCheckBox source = (JCheckBox) e.getSource();
                boolean isChecked = source.isSelected();
                ss.save(Settings.Key.TRIGGERBOT_ON, String.valueOf(isChecked));
            });
            container.add(onOff);
        }
    }

}
