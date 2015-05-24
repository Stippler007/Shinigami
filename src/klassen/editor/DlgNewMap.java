/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Julian
 */
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

    public boolean isReady() {
        return ready;
    }

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

    public GO getGround() {
        return (GO) cbGround.getSelectedItem();
    }

}
