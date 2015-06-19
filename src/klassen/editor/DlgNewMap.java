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
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Julian
 */
class DlgNewMap extends JDialog {

    private JSpinner spWidth, spHeight, spBright;
    private JComboBox<GO> cbGround;
    private JTextField tfID;
    private boolean ready = false;

    public DlgNewMap(JFrame owner) {
        super(owner, "Create New Map", true);

        JButton btCreate = new JButton();
        JButton btCancel = new JButton();

        spWidth = new JSpinner(new SpinnerNumberModel(30, 10, 500, 1));
        spHeight = new JSpinner(new SpinnerNumberModel(30, 10, 500, 1));
        spBright = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
        tfID = new JTextField();
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
        
        this.setSize(350, 200);
        this.setLayout(new GridLayout(6, 2, 5, 5));
        this.setLocationRelativeTo(null);
        this.add(new JLabel("ID (String)"));
        this.add(tfID);
        this.add(new JLabel("Width"));
        this.add(spWidth);
        this.add(new JLabel("Height"));
        this.add(spHeight);
        this.add(new JLabel("Ground"));
        this.add(cbGround);
        this.add(new JLabel("Brightness"));
        this.add(spBright);
        this.add(btCreate);
        this.add(btCancel);
    }

    public boolean isReady() {
        return ready;
    }
    
    public String getLevelID() {
        return tfID.getText();
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
    
    public int getBrightness() {
        try {
            spBright.commitEdit();
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            spBright.setValue(50);
        }
        return (Integer) spHeight.getValue();
    }

    public GO getGround() {
        return (GO) cbGround.getSelectedItem();
    }

}
