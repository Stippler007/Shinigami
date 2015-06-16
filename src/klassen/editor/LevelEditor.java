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

        cbSet.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                lp.setCurrentGameObject((GO) cbSet.getSelectedItem());
            }
        });
        btState.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (btState.getText()) {
                    case "GameObjects":
                        btState.setText("NPCs");
                        lp.setState(LevelPanel.State.NPC);
                        break;
                    case "NPCs":
                        btState.setText("Minions");
                        lp.setState(LevelPanel.State.MINION);
                        break;
                    case "Minions":
                        btState.setText("Config");
                        lp.setState(LevelPanel.State.CONFIG);
                        break;
                    case "Config":
                        btState.setText("GameObjects");
                        lp.setState(LevelPanel.State.GAMEOBJECT);
                        break;
                    default:
                        btState.setText("GameObjects");

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
                    lp.resetMap(dlg.getLevelWidth(), dlg.getLevelHeight(), dlg.getGround());
                    lp.setBrightness(dlg.getBrightness());
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
                        oos.writeObject(lp.getMap());
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
                        lp.setMap((GameObjects[][]) ois.readObject());
                        ois.close();
                        lp.repaint();
                    } catch (Exception ex) {
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
        return lp.map;
    }

    public static void main(String[] args) {
        LevelEditor le = new LevelEditor();
        le.setVisible(true);
    }
}
