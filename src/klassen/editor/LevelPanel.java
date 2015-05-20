/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import klassen.karte.GameObjects;

/**
 *
 * @author Julian
 */
class LevelPanel extends JPanel {

    private int width = 0;
    private int height = 0;
    int brightness;
    GameObjects[][] map;
    private GO currentGO = GO.WAND;
    private GO defaultGO = GO.WAND;

    private int padding = 10;
    private double scale = 1;
    private double translationX = 0;
    private double translationY = 0;
    private int mouseX;
    private int mouseY;

    public LevelPanel() {
        Listener l = new Listener();

        this.addKeyListener(l);
        this.addMouseListener(l);
        this.addMouseMotionListener(l);
        this.addMouseWheelListener(l);
    }

    public GameObjects[][] getMap() {
        return map;
    }

    public void setMap(GameObjects[][] map) {
        this.map = map;
    }

    public void setBrightness(int b) {
        brightness = b;
    }

    public void setCurrentGameObject(GO go) {
        currentGO = go;
    }

    public void resetMap(int width, int height, GO go) {
        this.width = width;
        this.height = height;
        map = new GameObjects[height][width];
        defaultGO = go;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = GO.getGOs(go)[0][0];
            }
        }
        System.out.println(map);
        System.out.println(this.width);
        System.out.println(this.height);

        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.clearRect(0, 0, this.getWidth(), this.getHeight());

        if (map != null) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.translate(translationX, translationY);
            g2d.scale(scale, scale);

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    g2d.drawImage(map[i][j].getLook(), padding + i * 25, padding + j * 25, null);
                }
            }
            GameObjects[][] go = GO.getGOs(currentGO);
            if (mouseX >= 0 || mouseY >= 0 || mouseX < width - go.length || mouseY < height - go[0].length) {
                for (int i = 0; i < go.length; i++) {
                    for (int j = 0; j < go[i].length; j++) {
                        g2d.drawImage(go[i][j].getLook(), (mouseX + i) * 25 + padding, (mouseY + j) * 25 + padding, null);
                    }

                }
            }
        }
    }

    class Listener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

        private boolean controlDown = false;

        private int x0 = 0;
        private int y0 = 0;

        public void updatePosition(MouseEvent e) {
            x0 = e.getX();
            y0 = e.getY();
        }

        public int getMapX(MouseEvent e) {
            return (int) ((e.getX() - padding - translationX) / (25 * scale));
        }

        public int getMapY(MouseEvent e) {
            return (int) ((e.getY() - padding - translationY) / (25 * scale));
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            controlDown = e.isControlDown();
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = getMapX(e);
            int y = getMapY(e);
            System.out.println(x + " " + y + " " + e.getButton());

            GameObjects[][] go = GO.getGOs(currentGO);

            if (x < 0 || y < 0 || x > width - go.length || y > height - go[0].length) {
                return;
            }

            if (e.getButton() == 1) {
                //map[x][y] = GO.getMapping(lp).get(currentGO)[0][0];
                for (int k = 0; k < go.length; k++) {
                    for (int l = 0; l < go[k].length; l++) {
                        map[x + k][y + l] = go[k][l];
                    }
                }
            } else if (e.getButton() == 3) {
                map[x][y] = GO.getGOs(defaultGO)[0][0];
            }

            LevelPanel.this.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            translationX = translationX + e.getX() - x0;
            translationY = translationY + e.getY() - y0;
            updatePosition(e);
            LevelPanel.this.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            updatePosition(e);
            mouseX = (int) ((e.getX() - padding - translationX) / (25 * scale));
            mouseY = (int) ((e.getY() - padding - translationY) / (25 * scale));
            System.out.println(mouseX + " " + mouseY);
            LevelPanel.this.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                if (e.getUnitsToScroll() > 0) {
                    scale = scale * 1.1;
                } else {
                    scale = scale * 0.9;
                }
                LevelPanel.this.repaint();
            }
        }
    }
}
