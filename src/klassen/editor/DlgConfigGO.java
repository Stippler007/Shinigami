/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import klassen.karte.GameObjects;

/**
 *
 * @author Julian
 */
class DlgConfigGO extends JDialog {

    private List<String> types = new ArrayList<>();
    private List<JTextField> inputs = new ArrayList<>();
    private GameObjects go;
    private boolean ready = false;

    public DlgConfigGO(JFrame owner, GameObjects obj) {
        super(owner, "Configure GameObject", true);
        this.go = obj;

        JButton btConfig = new JButton();
        JButton btCancel = new JButton();

        Method[] methods = obj.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            if (m.getName().startsWith("set") && m.getParameterCount() == 1) {
                this.types.add(m.getName());
                JLabel lb = new JLabel(m.getParameters()[0].getParameterizedType().getTypeName() + " " + m.getName().substring(3));
                lb.setAlignmentY(CENTER_ALIGNMENT);
                this.add(lb);
                JTextField tf = new JTextField();
                inputs.add(tf);
                this.add(tf);
            }
        }

        btConfig.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < inputs.size(); i++) {
                    if (!inputs.get(i).getText().isEmpty()) {
                        String parse = inputs.get(i).getText();
                        if (types.get(i).contains("Brigthness")) {
                            go.setBrightness(Integer.parseInt(parse));
                        } else if (types.get(i).contains("Solid")) {
                            go.setSolid(Boolean.parseBoolean(parse));
                        } else if (types.get(i).contains("SubX")) {
                            go.setSubX(Integer.parseInt(parse));
                        } else if (types.get(i).contains("SubY")) {
                            go.setSubY(Integer.parseInt(parse));
                        } else if (types.get(i).contains("Thorny")) {
                            go.setThorny(Boolean.parseBoolean(parse));
                        }
                    }
                }
                ready = true;
                DlgConfigGO.this.setVisible(false);
            }
        });
        btCancel.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DlgConfigGO.this.setVisible(false);
            }
        });

        btConfig.setText("Configure");
        btCancel.setText("Cancel");

        this.setSize(300, 400);
        this.setLayout(new GridLayout(inputs.size() + 1, 2, 5, 5));
        this.setLocationRelativeTo(null);

        this.add(btConfig);
        this.add(btCancel);
    }

    public boolean isReady() {
        return ready;
    }

    public GameObjects getGO() {
        return go;
    }
}
