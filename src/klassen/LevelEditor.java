/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import klassen.karte.Boden;
import klassen.karte.GameObjects;
import klassen.karte.Gras;
import klassen.karte.Tree;
import klassen.karte.Wand;
import klassen.karte.Weg;

/**
 *
 * @author Julian
 */
public class LevelEditor extends JFrame {
    
    private LevelPanel lp;
    
    public LevelEditor() {
        final JPanel controls = new JPanel();
        final JComboBox<GO> cbSetGO = new JComboBox<>(GO.values());
        final JButton btNewMap = new JButton();
        final JButton btSave = new JButton();
        final JButton btLoad = new JButton();
        lp = new LevelPanel();
        
        lp.setCurrentGameObject(cbSetGO.getItemAt(0));
        
        cbSetGO.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lp.setCurrentGameObject((GO) cbSetGO.getSelectedItem());
            }
        });
        btNewMap.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DlgNewMap dlg = new DlgNewMap(LevelEditor.this);
                dlg.setVisible(true);
                
                if(dlg.isReady()) {
                    //System.out.println(dlg.getLevelWidth()+" "+dlg.getLevelHeight()+" "+dlg.getGround());
                    lp.resetMap(dlg.getLevelWidth(), dlg.getLevelHeight(), dlg.getGround());
                }
            }
        });
        btSave.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.showSaveDialog(LevelEditor.this);
                File f = fc.getSelectedFile();
                
                if(f != null) {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                        oos.writeObject(lp.map);
                        oos.close();
                    } catch (IOException ex) {
                        System.out.println("Could not Save\n"+ex.getMessage());
                    }
                }
            }
        });
        btLoad.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(LevelEditor.this);
                File f = fc.getSelectedFile();
                
                if(f != null) {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                        LevelEditor.this.lp.map = (GameObjects[][]) ois.readObject();
                        ois.close();
                        lp.repaint();
                    } catch (Exception ex) {
                        System.out.println("Could not load\n"+ex.getMessage());
                    }
                }
            }
        });
        
        btNewMap.setText("New Map");
        btSave.setText("Save");
        btLoad.setText("Load");
        
        controls.setSize(controls.getWidth(), 20);
        controls.setLayout(new GridLayout(1, 5, 10, 10));
        controls.add(cbSetGO);
        controls.add(btNewMap);
        controls.add(btSave);
        controls.add(btLoad);
        
        this.setLayout(new BorderLayout(5, 5));
        this.setSize(800, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(controls, BorderLayout.NORTH);
        this.add(lp, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        LevelEditor le = new LevelEditor();
        le.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        le.setVisible(true);
    }
    
    enum GO {
        GRAS , WAND, WEG, TREE, BODEN;
        
        private static Map<GO, GameObjects> m;
        
        public static Map<GO, GameObjects> getMapping(LevelPanel lp) {
            m = new HashMap<>();

            for(GO g : GO.values()) {
                switch(g) {
                    case BODEN:
                        m.put(g, new Boden(lp.brightness));
                        break;
                    case GRAS:
                        m.put(g, new Gras(lp.brightness));
                        break;
                    case TREE:
                        m.put(g, new Tree(lp.brightness, 0, 0));
                        break;
                    case WAND:
                        m.put(g, new Wand(lp.brightness));
                        break;
                    case WEG:
                        m.put(g, new Weg(lp.brightness));
                        break;
                }
            }
            
            return m;
        }
    }
    
    class LevelPanel extends JPanel {
        private int width;
        private int height;
        int brightness;
        GameObjects[][] map;
        private GO currentGO;
        
        private int padding = 10;
        private double scale = 1;
        private double translationX = 0;
        private double translationY = 0;
        
        public LevelPanel() {
            MouseAdapter ma = new MouseAdapter() {
                private int x0 = 0;
                private int y0 = 0;

                @Override
                public void mouseMoved(MouseEvent e) {
                    x0 = e.getX();
                    y0 = e.getY();
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    translationX = translationX+e.getX()-x0;
                    translationY = translationY+e.getY()-y0;
                    x0 = e.getX();
                    y0 = e.getY();
                    LevelEditor.this.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    int i = (int) ((e.getX()-padding-translationX)/(25*scale));
                    int j = (int) ((e.getY()-padding-translationY)/(25*scale));
                    System.out.println(i+" "+j+" "+e.getButton());

                    if(e.getButton() == 1) {
                        map[i][j] = GO.getMapping(lp).get(currentGO);
                    } else if(e.getButton() == 3) {
                        map[i][j] = GO.getMapping(lp).get(new Wand(lp.brightness));
                    }

                    LevelEditor.this.repaint();
                }

                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                        if(e.getUnitsToScroll() > 0) {
                            scale = scale*1.1;
                        } else {
                            scale = scale*0.9;
                        }
                        LevelEditor.this.repaint();
                    }
                }
            };
            this.addMouseListener(ma);
            this.addMouseMotionListener(ma);
            this.addMouseWheelListener(ma);
        }
        
        public void setBrightness(int b) {brightness = b;}
        
        public void setCurrentGameObject(GO go) { currentGO = go; }
        
        public void resetMap(int width, int height, GO go) {
        this.width = width;
        this.height = height;
        map = new GameObjects[height][width];
        
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = GO.getMapping(this).get(go);
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

            if(map != null) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.translate(translationX, translationY);
                g2d.scale(scale, scale);

                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        GameObjects go = map[i][j];
                        g2d.drawImage(go.getLook(), padding+i*25, padding+j*25, null);
                    }
                }
            }
        }
    }
    
    class DlgNewMap extends JDialog {
    
    private JSpinner spWidth, spHeight;
    private JComboBox<GO> cbGround;
    private boolean ready = false;
    
    public DlgNewMap(JFrame owner) {
        super(owner, "Create New Map", true);
        
        JButton btCreate = new JButton();
        JButton btCancel = new JButton();

        spWidth = new JSpinner(new SpinnerNumberModel(30, 10, 500, 1));
        spHeight = new JSpinner(new SpinnerNumberModel(30, 10, 500, 1));
        cbGround = new JComboBox<>(GO.values());
        
        btCreate.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ready = true;
                DlgNewMap.this.setVisible(false);
            }
        });
        btCancel.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DlgNewMap.this.setVisible(false);
            }
        });
        
        btCreate.setText("Create");
        btCancel.setText("Cancel");
        
        this.setSize(200, 200);
        this.setLayout(new GridLayout(5, 1, 5, 5));
        this.setLocationRelativeTo(null);
        this.add(spWidth);
        this.add(spHeight);
        this.add(cbGround);
        this.add(btCreate);
        this.add(btCancel);
    }
    
    public boolean isReady() { return ready; }
    
    public int getLevelWidth() { 
        try {
            spWidth.commitEdit();
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            spWidth.setValue(30);
        }
        return (Integer) spWidth.getValue(); 
    }
    
    public int getLevelHeight() { 
        try {
            spHeight.commitEdit();
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            spHeight.setValue(30);
        }
        return (Integer) spHeight.getValue(); 
    }
    
    public GO getGround() { return (GO) cbGround.getSelectedItem(); }
    
}
}
