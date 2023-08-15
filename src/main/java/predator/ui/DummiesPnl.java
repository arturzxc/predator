package predator.ui;

import predator.entities.DummyList;
import predator.entities.Player;

import javax.swing.*;
import java.awt.*;

public class DummiesPnl extends JPanel {
    private final DummyList dummyList;
    private final JLabel[][] cells;
    private final JButton[] headers;
    private final int TOTAL_ROWS = 60;

    public DummiesPnl(DummyList dummyList) {
        this.dummyList = dummyList;

        //headers
        Font monospacedFont = new Font(Font.MONOSPACED, Font.PLAIN, 11);
        headers = new JButton[]{
                new JButton("INDEX"),
                new JButton("AIMED_AT"),
                new JButton("BASE"),
                new JButton("NAME"),
                new JButton("ORIGIN_X"),
                new JButton("ORIGIN_Y"),
                new JButton("ORIGIN_Z"),
                new JButton("TEAM_NO"),
                new JButton("SHIELDS_MAX"),
                new JButton("GLW_ENA"),
                new JButton("GLW_T_WALL"),
                new JButton("VISIBLE"),
                new JButton("DSTNC_FROM_LP"),
                new JButton("VI_ANG_PITCH"),
                new JButton("VI_ANG_YAW"),
                new JButton("DESIRED_PITCH"),
                new JButton("DESIRED_YAW")
        };
        for (JButton header : headers) {
            header.setFont(monospacedFont);
            add(header);
        }

        //create grid of labels
        cells = new JLabel[TOTAL_ROWS][headers.length];
        for (int i = 0; i < TOTAL_ROWS; i++) {
            for (int j = 0; j < headers.length; j++) {
                JLabel cell = new JLabel("-", SwingConstants.CENTER);
                cell.setFont(monospacedFont);
                cell.setForeground(Color.white);
                cells[i][j] = cell;
                add(cell);
            }
        }

        //setup this panel
        setBackground(Color.black);
        setLayout(new GridLayout(0, headers.length));
    }

    public void update() {
        //repopulate cells
        int currIndex = 0;
        for (Player p : dummyList.getDummies()) {
            int col = 0;

            cells[currIndex][col++].setText(parse(p.index));
            cells[currIndex][col++].setText(parse(p.aimedAt));
            cells[currIndex][col++].setText(parse(p.base != null ? p.base.toString().replace("native@", "") : ""));
            cells[currIndex][col++].setText(parse(parse(p.entityType)));
            if (p.localOrigin != null) {
                cells[currIndex][col++].setText(formatFloat(p.localOrigin.x));
                cells[currIndex][col++].setText(formatFloat(p.localOrigin.y));
                cells[currIndex][col++].setText(formatFloat(p.localOrigin.z));
            }
            cells[currIndex][col++].setText(parse(p.teamNumber));
            cells[currIndex][col++].setText(parse(p.shieldHealthMax));
            cells[currIndex][col++].setText(parse(p.glowEnable));
            cells[currIndex][col++].setText(parse(p.glowThroughWall));
            cells[currIndex][col++].setText(parse(p.visible));
            cells[currIndex][col++].setText(formatDouble(p.distanceToLocalPlayer));
            if (p.viewAngles != null) {
                cells[currIndex][col++].setText(parse(p.viewAngles.x));
                cells[currIndex][col++].setText(parse(p.viewAngles.y));
            }
            cells[currIndex][col++].setText(formatDouble(p.desiredPitch));
            cells[currIndex][col].setText(formatDouble(p.desiredYaw));

            currIndex++;
        }

        //clear all remaining rows
        for (int i = dummyList.getDummies().size(); i < TOTAL_ROWS; i++) {
            for (int j = 0; j < headers.length; j++) {
                cells[i][j].setText("");
            }
        }
    }

    private String parse(Object o) {
        if (o == null) return " — ";
        return String.valueOf(o);
    }

    private String formatDouble(Double o) {
        if (o == null) return " — ";
        return String.format("%12.4f", o);
    }

    private String formatFloat(Float o) {
        if (o == null) return " — ";
        return String.format("%12.4f", o);
    }

}
