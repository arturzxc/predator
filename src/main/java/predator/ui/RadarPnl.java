package predator.ui;

import predator.core.*;
import predator.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RadarPnl extends JPanel {

    private final int DOT_SIZE = 10;
    private final int DOT_SIZE_HALF = DOT_SIZE / 2;
    private final Level level;
    private final LocalPlayer localPlayer;
    private final PlayerList playerList;
    private final DummyList dummyList;
    private List<Player> myFriendlyEntities;
    private List<Player> myEnemyEntities;
    private final Map<Integer, Color> myColorMap;

    public RadarPnl(Level level, LocalPlayer localPlayer, PlayerList playerList, DummyList dummyList) {
        this.level = level;
        this.localPlayer = localPlayer;
        this.playerList = playerList;
        this.dummyList = dummyList;
        this.myColorMap = getRandomColors();
        setBackground(Color.black);
    }

    private void filterPlayers() {
        myFriendlyEntities = playerList.getFriendlyPlayers();
        myEnemyEntities = playerList.getEnemyPlayers();
        myEnemyEntities.addAll(dummyList.getDummies());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (!level.playable) {
            g2.setColor(Color.white);
            String msg = "Waiting for a playable map";
            g2.drawString(msg, getWidthHalf() - 60, getHeightHalf());
            return;
        }
        if (localPlayer.base != null && localPlayer.dead != null && localPlayer.dead) {
            g2.setColor(Color.white);
            String msg = "DEAD NOOBS DON'T GET NO RADAR!";
            g2.drawString(msg, getWidthHalf() - 60, getHeightHalf());
            return;
        }
        filterPlayers();
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        Font originalFont = g.getFont();
        Font boldCapitalFont = originalFont.deriveFont(Font.BOLD);
        g.setFont(boldCapitalFont);
        drawCrossHairs(g2);
        AffineTransform initialTransform = g2.getTransform();
        rotateAroundLocalPLayer(g2);
        drawMapBackground(g2);
        translateToOnLocalPlayer(g2);
        drawEnemyPlayers(g2);
        drawFriendlyPlayers(g2);
        g2.setTransform(initialTransform);
        drawMapName(g2);
    }

    private void rotateAroundLocalPLayer(Graphics2D g2) {
        if (localPlayer == null) return;
        if (localPlayer.base == null) return;
        FloatVector2D diffFromCenter = getLocalPlayerCenterDiff();
        if (diffFromCenter == null) return;
        double rotationRadians = Math.toRadians(localPlayer.viewAngles.y - 90);
        g2.rotate(rotationRadians, getWidthHalf(), getHeightHalf());
    }

    private void translateToOnLocalPlayer(Graphics2D g2) {
        if (localPlayer == null) return;
        if (localPlayer.base == null) return;
        FloatVector2D diffFromCenter = getLocalPlayerCenterDiff();
        if (diffFromCenter == null) return;
        g2.translate(diffFromCenter.x, diffFromCenter.y);
    }

    void drawMapBackground(Graphics2D g2) {
        g2.setColor(new Color(81, 192, 2, 187));
        g2.fillRect((int) (getWidthHalf() - 10), 0, 20, 20);
        g2.fillRect((int) (getWidthHalf() - 10), getHeight() - 20, 20, 20);
        g2.setColor(Color.black);
        g2.drawString("N", getWidthHalf() - 5, 14);
        g2.drawString("S", getWidthHalf() - 3, getHeight() - 6);
    }

    void drawMapName(Graphics2D g2) {
        if (level.playable) {
            String name;
            if (level.isTrainingArea) name = level.name + " (TRAINING AREA)";
            else name = level.name;
            g2.setColor(Color.white);
            g2.drawString(name, 5, 20);
        } else {
            g2.setColor(Color.white);
            g2.drawString("<Not a playable map>", 5, 5);
        }
    }

    private void drawCrossHairs(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.drawLine((int) getWidthHalf(), 0, (int) getWidthHalf(), getHeight());
        g2.drawLine(0, (int) getHeightHalf(), getWidth(), (int) getHeightHalf());
    }

    private void drawFriendlyPlayers(Graphics2D g2) {
        for (Player player : myFriendlyEntities) {
            FloatVector3D originScaledTranslated = scaleAndTranslate(player.localOrigin);
            g2.setColor(Color.green);
            g2.fillOval((int) (originScaledTranslated.x - DOT_SIZE_HALF), (int) (originScaledTranslated.y - DOT_SIZE_HALF), DOT_SIZE, DOT_SIZE);
            g2.setColor(Color.black);
        }
    }

    private void drawEnemyPlayers(Graphics2D g2) {
        for (Player player : myEnemyEntities) {
            FloatVector3D originScaledTranslated = scaleAndTranslate(player.localOrigin);
            g2.setColor(myColorMap.get(player.teamNumber));
            //draw enemy
            g2.fillOval((int) (originScaledTranslated.x - DOT_SIZE_HALF), (int) (originScaledTranslated.y - DOT_SIZE_HALF), DOT_SIZE, DOT_SIZE);
            g2.setColor(Color.black);
            //draw enemy evo
            Color evoCol;
            if (player.shieldHealthMax == 125) evoCol = new Color(236, 10, 10);
            else if (player.shieldHealthMax == 100) evoCol = new Color(187, 10, 236);
            else if (player.shieldHealthMax == 75) evoCol = new Color(65, 191, 246);
            else if (player.shieldHealthMax == 50) evoCol = new Color(248, 253, 255);
            else evoCol = null;
            if (evoCol != null) {
                g2.setColor(evoCol);
                g2.drawOval((int) (originScaledTranslated.x - DOT_SIZE_HALF), (int) (originScaledTranslated.y - DOT_SIZE_HALF), DOT_SIZE, DOT_SIZE);
            }
        }
    }

    private FloatVector3D scaleAndTranslate(FloatVector3D v) {
        float x = v.x;
        float y = v.y;
        float SCALE = 35.0f;
        float xScaled = x / SCALE;
        float yScaled = y / SCALE;
        float xScaledTranslated = (getWidthHalf() + xScaled);
        float yScaledTranslated = (getHeightHalf() - yScaled);
        FloatVector3D v2 = new FloatVector3D();
        v2.x = xScaledTranslated;
        v2.y = yScaledTranslated;
        v2.z = 0f;
        return v2;
    }

    private float getWidthHalf() {
        return getWidth() / 2.0f;
    }

    private float getHeightHalf() {
        return getHeight() / 2.0f;
    }

    private FloatVector2D getCenter() {
        FloatVector2D v = new FloatVector2D();
        v.x = getWidthHalf();
        v.y = getHeightHalf();
        return v;
    }

    private FloatVector2D getLocalPlayerCenterDiff() {
        if (localPlayer == null) return null;
        if (localPlayer.base == null) return null;
        FloatVector3D orig = scaleAndTranslate(localPlayer.localOrigin);
        FloatVector2D orig2d = new FloatVector2D();
        orig2d.x = orig.x;
        orig2d.y = orig.y;
        return getCenter().subtract(orig2d);
    }

    private Map<Integer, Color> getRandomColors() {
        Map<Integer, Color> map = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++)
            map.put(i, new Color(255, random.nextInt(200), random.nextInt(30)));
        return map;
    }


}
