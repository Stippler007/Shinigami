/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import klassen.Level;
import klassen.karte.GameObjects;

/**
 *
 * @author Julian
 */
public class LevelEditor extends JFrame {

    private LevelPanel lp;

    public LevelEditor() {
        final JPanel controls = new JPanel();
        final JComboBox cbSet = new JComboBox(GO.values());
        final JButton btState = new JButton();
        final JButton btNewMap = new JButton();
        final JButton btSave = new JButton();
        final JButton btLoad = new JButton();
        lp = new LevelPanel();

        lp.setCurrentGameObject((GO) cbSet.getItemAt(0));
        lp.setState(LevelPanel.State.GAMEOBJECT);

        cbSet.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(lp.getState());
                if (lp.getState() == LevelPanel.State.GAMEOBJECT) {
                    lp.setCurrentGameObject((GO) cbSet.getSelectedItem());
                } else if (lp.getState() == LevelPanel.State.NPC) {
                    lp.setCurrentNPC((NPC) cbSet.getSelectedItem());
                } else if (lp.getState() == LevelPanel.State.MINION) {
                    lp.setCurrentMinion((Minion) cbSet.getSelectedItem());
                }
            }
        });
        btState.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (btState.getText()) {
                    case "GameObjects":
                        btState.setText("NPCs");

                        lp.setCurrentGameObject(null);
                        lp.setCurrentMinion(null);
                        lp.setState(LevelPanel.State.NPC);

                        cbSet.removeAllItems();
                        for (NPC n : NPC.values()) {
                            cbSet.addItem(n);
                        }
                        break;
                    case "NPCs":
                        btState.setText("Minions");

                        lp.setCurrentGameObject(null);
                        lp.setCurrentNPC(null);
                        lp.setState(LevelPanel.State.MINION);

                        cbSet.removeAllItems();
                        for (Minion m : Minion.values()) {
                            cbSet.addItem(m);
                        }
                        break;
                    case "Minions":
                        btState.setText("Boss");

                        lp.setCurrentGameObject(null);
                        lp.setCurrentNPC(null);
                        lp.setCurrentMinion(null);
                        lp.setState(LevelPanel.State.BOSS);

                        cbSet.removeAllItems();
                        for (Boss b : Boss.values()) {
                            cbSet.addItem(b);
                        }
                        break;
                    case "Boss":
                        btState.setText("Config");

                        lp.setCurrentGameObject(null);
                        lp.setCurrentNPC(null);
                        lp.setCurrentMinion(null);
                        lp.setState(LevelPanel.State.CONFIG);

                        cbSet.removeAllItems();
                        cbSet.setEnabled(false);
                        break;
                    case "Config":
                        btState.setText("GameObjects");

                        lp.setCurrentGameObject(GO.GRAS);
                        lp.setCurrentNPC(null);
                        lp.setCurrentMinion(null);
                        lp.setState(LevelPanel.State.GAMEOBJECT);

                        cbSet.setEnabled(true);
                        cbSet.removeAllItems();
                        for (GO g : GO.values()) {
                            cbSet.addItem(g);
                        }
                        break;
                    default:
                        break;

                }
            }
        });

        btNewMap.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DlgNewMap dlg = new DlgNewMap(LevelEditor.this);
                dlg.setVisible(true);

                if (dlg.isReady()) {
                    //System.out.println(dlg.getLevelWidth()+" "+dlg.getLevelHeight()+" "+dlg.getGround());
                    //lp.resetMap(dlg.getLevelWidth(), dlg.getLevelHeight(), dlg.getGround());
                    //lp.setBrightness(dlg.getBrightness());
                    lp.newLevel(dlg.getLevelID(), dlg.getLevelWidth(), dlg.getLevelHeight(), dlg.getGround(), dlg.getBrightness());
                }
            }
        });
        btSave.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.showSaveDialog(LevelEditor.this);
                File f = fc.getSelectedFile();

                if (f != null) {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                        oos.writeObject(lp.getLevel());
                        oos.close();
                    } catch (IOException ex) {
                        System.out.println("Could not Save\n" + ex.getMessage());
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

                if (f != null) {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                        lp.setLevel((Level) ois.readObject());
                        ois.close();
                        lp.repaint();
                    } catch (IOException ex) {
                        System.out.println("IOException: "+ex.getMessage());
                    } catch (ClassNotFoundException ex) {
                        System.out.println("Could not load\n" + ex.getMessage());
                    }
                }
            }
        });

        btState.setText("GameObjects");
        btNewMap.setText("New Map");
        btSave.setText("Save");
        btLoad.setText("Load");

        controls.setSize(controls.getWidth(), 20);
        controls.setLayout(new GridLayout(1, 5, 10, 10));
        controls.add(cbSet);
        controls.add(btState);
        controls.add(btNewMap);
        controls.add(btSave);
        controls.add(btLoad);

        this.setLayout(new BorderLayout(5, 5));
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(controls, BorderLayout.NORTH);
        this.add(lp, BorderLayout.CENTER);
    }

    public GameObjects[][] getMap() {
        return lp.getLevel().getMap();
    }

    public static void main(String[] args) {
        LevelEditor le = new LevelEditor();
        le.setVisible(true);
    }
}
