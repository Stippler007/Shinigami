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
import klassen.Level;
import klassen.karte.GameObjects;

/**
 *
 * @author Julian
 */
class LevelPanel extends JPanel {

    private int width = 0;
    private int height = 0;
    private GO currentGO = GO.WAND;
    private GO defaultGO = GO.WAND;
    
    private NPC currentNPC = NPC.SIGN;
    private Minion currentMinion = Minion.EVILGUARD;
    private Boss currentBoss;
    private State state;

    private Level level;

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

    public Level getLevel() {
        return level;
    }
    
    public void setLevel(Level l) {
        level = l;
        width = l.getMap().length;
        height = l.getMap()[0].length;
        this.repaint();
    }

    public void setCurrentGameObject(GO go) {
        currentGO = go;
    }

    public void setCurrentNPC(NPC currentNPC) {
        this.currentNPC = currentNPC;
    }

    public void setCurrentMinion(Minion currentMinion) {
        this.currentMinion = currentMinion;
    }

    public void setCurrentBoss(Boss currentBoss) {
        this.currentBoss = currentBoss;
    }
    
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void newLevel(String id, int width, int height, GO go, int brightness) {
        this.width = width;
        this.height = height;
        this.defaultGO = go;
        
        GameObjects[][] map = new GameObjects[width][height];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = GO.getGOs(go)[0][0];
                map[i][j].setBrightness(brightness);
            }
        }
        System.out.println(map[0][0]);
        this.level = new Level(id, map);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        if (level != null) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.translate(translationX, translationY);
            g2d.scale(scale, scale);

            for (int i = 0; i < level.getMap().length; i++) {
                for (int j = 0; j < level.getMap()[i].length; j++) {
                    g2d.drawImage(level.getMap()[i][j].getLook(), padding + i * 25, padding + j * 25, null);
                }
            }

            for (klassen.npc.NPC npc : level.getNpcs()) {
                g2d.drawImage(npc.getLook(), (int) npc.getX(), (int) npc.getY(), null);
            }
            
            for( klassen.minion.Minion minion : level.getMinions()) {
                g2d.drawImage(minion.getLook(), (int) minion.getX(), (int) minion.getY(), null);
            }
            
            if(level.getBoss() != null) {
                g2d.drawImage(level.getBoss().getLook(), (int) level.getBoss().getX(), (int) level.getBoss().getY(), null);
            }
            
            switch (state) {
                case CONFIG:
                    g2d.drawRect(mouseX * 25 + padding, mouseY * 25 + padding, 25, 25);
                    break;
                case GAMEOBJECT:
                    drawGOs(g2d);
                    break;
                case NPC:
                    g2d.drawImage(NPC.getNPCs(currentNPC).getLook(), mouseX * 25 + padding, mouseY * 25 + padding, null);
                    break;
                case MINION:
                    g2d.drawImage(Minion.getMinions(currentMinion).getLook(), mouseX * 25 + padding, mouseY  * 25 + padding, null);
                    break;
                case BOSS:
                    System.out.println(Boss.getBoss(currentBoss));
                    g2d.drawImage(Boss.getBoss(currentBoss).getLook(), mouseX * 25 + padding, mouseY * 25 + padding, null);
                    break;
                default:
                    break;
            }
        }
    }

    private void drawGOs(Graphics2D g2d) {
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

    private BufferedImage makeTransparent(BufferedImage img) {
        BufferedImage tmp = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = tmp.createGraphics();
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.drawImage(img, 0, 0, null);

        return tmp;
    }

    enum State {

        GAMEOBJECT, NPC, MINION, BOSS, CONFIG;
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

        private void showConfig() {
            System.out.println("config");
            for(int i = 0; i<level.getNpcs().size(); i++) {
                if((int) level.getNpcs().get(i).getX()/25 == mouseX && (int) level.getNpcs().get(i).getY()/25 == mouseY) {
                    DlgConfigNPC dlg = new DlgConfigNPC(null, level.getNpcs().get(i));
                    dlg.setVisible(true);
                    
                    if(dlg.isReady()) {
                        level.getNpcs().set(i, dlg.getNPC());
                    }
                    return;
                }
            }
            
            for (int i = 0; i < level.getMinions().size(); i++) {
                if((int) level.getMinions().get(i).getX()/25 == mouseX && (int) level.getMinions().get(i).getY()/25 == mouseY) {
                    DlgConfigMinion dlg = new DlgConfigMinion(null, level.getMinions().get(i));
                    dlg.setVisible(true);
                    
                    if(dlg.isReady()) {
                        level.getMinions().set(i, dlg.getMinion());
                    }
                    return;
                }
            }
            
            if(level.getBoss() != null)
            if((int) level.getBoss().getX()/25 == mouseX && (int) level.getBoss().getY()/25 == mouseY) {
                DlgConfigBoss dlg = new DlgConfigBoss(null, level.getBoss());
                dlg.setVisible(true);
                
                if(dlg.isReady()) {
                    level.setBoss(dlg.getBoss());
                }
                System.out.println("end");
                return;
            }
            System.out.println("config go "+mouseX+ " "+mouseY);
            DlgConfigGO dlg = new DlgConfigGO(null, level.getMap()[mouseX][mouseY]);
            dlg.setVisible(true);

            if (dlg.isReady()) {
                level.getMap()[mouseX][mouseY] = dlg.getGO();
            }
        }

        public boolean isOutOfMap(int x, int y) {
            System.out.println(level);
            System.out.println(mouseX + " "+mouseY);
            System.out.println(width+" "+height);
            return (level == null || x < 0 || y < 0 || x > width || y > height);
        }
        
        private boolean isOut() {
            return isOutOfMap(mouseX, mouseY);
        }

        private void setGO(MouseEvent e) {

            if (currentGO == null) {
                return;
            }

            GameObjects[][] go = GO.getGOs(currentGO);

            if (mouseX > width - go.length || mouseY > height - go[0].length) {
                return;
            }

            if (e.getButton() == 1) {
                //map[x][y] = GO.getMapping(lp).get(currentGO)[0][0];
                for (int k = 0; k < go.length; k++) {
                    for (int l = 0; l < go[k].length; l++) {
                        level.getMap()[mouseX + k][mouseY + l] = go[k][l];
                    }
                }
            } else if (e.getButton() == 3) {
                level.getMap()[mouseX][mouseY] = GO.getGOs(defaultGO)[0][0];
            }
        }

        private void setNPC(MouseEvent e) {
            
            if(isOut()) {
                return;
            }
            
            klassen.npc.NPC n = NPC.getNPCs(currentNPC);
            n.setX(mouseX * 25 + padding);
            n.setY(mouseY * 25 + padding);
            n.setMap(level.getMap());
            level.getNpcs().add(n);
        }
        
        private void setMinion(MouseEvent e) {
            if(isOut()) {
                return;
            }
            
            klassen.minion.Minion m = Minion.getMinions(currentMinion);
            m.setX(mouseX * 25 + padding);
            m.setY(mouseY * 25 + padding);
            m.setMap(level.getMap());
            level.getMinions().add(m);
        }

        private void setBoss(MouseEvent e) {
            if(isOut()) {
                return;
            }
            
            klassen.boss.Boss b = Boss.getBoss(currentBoss);
            b.setX(mouseX * 25 + padding);
            b.setY(mouseY * 25 + padding);
            b.setMap(level.getMap());
            level.setBoss(b);
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            mouseX = getMapX(e);
            mouseY = getMapY(e);

            System.out.println(e.getWhen() + " " + e.getClickCount() + " " + e.getButton());

            if (isOutOfMap(mouseX, mouseY)) {
                return;
            }
            
            switch (state) {
                case CONFIG:
                    showConfig();
                    break;
                case GAMEOBJECT:
                    setGO(e);
                    break;
                case NPC:
                    setNPC(e);
                    break;
                case MINION:
                    setMinion(e);
                    break;
                case BOSS:
                    setBoss(e);
                    break;
                default:
                    break;
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

            if (isOutOfMap(mouseX, mouseY)) {
                return;
            }

            if (e.isControlDown()) {
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
                mouseX = this.getMapX(e);
                mouseY = this.getMapY(e);
                GameObjects[][] go = GO.getGOs(currentGO);

                if (mouseX < 0 || mouseY < 0 || mouseX > width || mouseY > height) {
                    mapX = -1;
                    mapY = -1;
                    return;
                }

                if (mapX > mouseX) {
                    int tmp = mapX;
                    mapX = mouseX;
                    mouseX = tmp;
                }
                if (mapY > mouseY) {
                    int tmp = mapY;
                    mapY = mouseY;
                    mouseY = tmp;
                }

                for (int i = mapX; i <= mouseX; i = i + go.length) {
                    for (int j = mapY; j <= mouseY; j = j + go[0].length) {
                        for (int k = 0; k < go.length; k++) {
                            for (int l = 0; l < go[k].length; l++) {
                                level.getMap()[i + k][j + l] = go[k][l];
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
