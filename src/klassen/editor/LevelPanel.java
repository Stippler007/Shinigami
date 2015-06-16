/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
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
    private State state;

    private int padding = 10;
    private double scale = 1;
    private double translationX = 0;
    private double translationY = 0;
    private int mouseX;
    private int mouseY;
    private int mapX = -1;
    private int mapY = -1;

    public LevelPanel() {
        Listener l = new Listener();

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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

            if(currentGO == null) {
                //g2d.setColor(new Color(255, 255, 255, 64));
                g2d.drawRect(mouseX * 25 + padding, mouseY  * 25 + padding, 25, 25);
                return;
            }
            
            GameObjects[][] go = GO.getGOs(currentGO);
            if (mouseX >= 0 || mouseY >= 0 || mouseX < width - go.length || mouseY < height - go[0].length) {
                if (mapX > -1 && mapY > -1) {
                    System.out.println("Draw " + mapX + " " + mapY);

                    int x = mouseX;
                    int y = mouseY;

                    if (mapX > x) {
                        int tmp = mapX;
                        mapX = x;
                        x = tmp;
                    }
                    if (mapY > y) {
                        int tmp = mapY;
                        mapY = y;
                        y = tmp;
                    }

                    for (int i = mapX; i <= x; i = i + go.length) {
                        for (int j = mapY; j <= y; j = j + go[0].length) {
                            //g2d.drawImage(makeTransparent(go[0][0].getLook()), i * 25 + padding, j * 25 + padding, null);
                            for (int k = 0; k < go.length; k++) {
                                for (int l = 0; l < go[k].length; l++) {
                                    g2d.drawImage(makeTransparent(go[k][l].getLook()), (k + i) * 25 + padding, (l + j) * 25 + padding, null);
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < go.length; i++) {
                    for (int j = 0; j < go[i].length; j++) {
                        g2d.drawImage(makeTransparent(go[i][j].getLook()), (mouseX + i) * 25 + padding, (mouseY + j) * 25 + padding, null);
                    }
                }
            }
        }
    }

    private BufferedImage makeTransparent(BufferedImage img) {
        BufferedImage tmp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = tmp.createGraphics();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.drawImage(img, 0, 0, null);

        return tmp;
    }
    
    enum State {
        GAMEOBJECT, NPC, MINION, CONFIG
    }

    class Listener implements MouseListener, MouseWheelListener, MouseMotionListener {

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

        private void showConfig(int x, int y) {
            DlgConfigGO dlg = new DlgConfigGO(null, map[x][y]);
            dlg.setVisible(true);

            if (dlg.isReady()) {
                map[x][y] = dlg.getGO();
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = getMapX(e);
            int y = getMapY(e);

            System.out.println(e.getWhen() + " " + e.getClickCount() + " " + e.getButton());
            
            if(map == null || x < 0 || y < 0 || x > width || y > height) {
                return;
            }
            
            if (e.getClickCount() == 1) {
                System.out.println(x + " " + y + " " + e.getButton());

                if(currentGO == null) {
                    showConfig(x, y);
                    return;
                }
                
                GameObjects[][] go = GO.getGOs(currentGO);

                if (x > width - go.length || y > height - go[0].length) {
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
                
            } else if (e.getClickCount() == 2) {
                showConfig(x, y);
            }
            
            LevelPanel.this.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!e.isControlDown()) {
                translationX = translationX + e.getX() - x0;
                translationY = translationY + e.getY() - y0;
                updatePosition(e);
            }
            mouseX = getMapX(e);
            mouseY = getMapY(e);
            LevelPanel.this.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            updatePosition(e);
            mouseX = getMapX(e);
            mouseY = getMapY(e);
            System.out.println(mouseX + " " + mouseY);
            LevelPanel.this.repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = getMapX(e);
            mouseY = getMapY(e);

            if(map == null || mouseX < 0 || mouseY < 0 || mouseX > width || mouseY > height) {
                return;
            }
            
            if (e.isControlDown()) {
                System.out.println("set");
                mapX = this.getMapX(e);
                mapY = this.getMapY(e);
            } else {
                mapX = -1;
                mapY = -1;
            }
            
            LevelPanel.this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isControlDown() && mapX > -1 && mapY > -1) {
                int x = this.getMapX(e);
                int y = this.getMapY(e);
                GameObjects[][] go = GO.getGOs(currentGO);
                
                if(x < 0 || y < 0 || x > width || y > height) {
                    mapX = -1;
                    mapY = -1;
                    return;
                }
                
                if (mapX > x) {
                    int tmp = mapX;
                    mapX = x;
                    x = tmp;
                }
                if (mapY > y) {
                    int tmp = mapY;
                    mapY = y;
                    y = tmp;
                }
                
                for (int i = mapX; i <= x; i = i + go.length) {
                    for (int j = mapY; j <= y; j = j + go[0].length) {
                        for (int k = 0; k < go.length; k++) {
                            for (int l = 0; l < go[k].length; l++) {
                                map[i+k][j+l] = go[k][l];
                            }
                        }
                    }
                }
            }
            
            mapX = -1;
            mapY = -1;
            
            LevelPanel.this.repaint();
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
