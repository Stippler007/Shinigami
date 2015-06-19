/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.editor;

import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Julian
 */
public class DlgConfigBoss extends JDialog {
    private List<String> types = new ArrayList<>();
    private List<JTextField> inputs = new ArrayList<>();
    private klassen.boss.Boss boss;
    private boolean ready = false;
    
    public DlgConfigBoss(JFrame owner, klassen.boss.Boss obj) {
        super(owner, "Configure NPC", true);
        boss = obj;
        
        JButton btConfig = new JButton();
        JButton btCancel = new JButton();
        
        Parameter[] params = obj.getClass().getConstructors()[0].getParameters();
        
        for (int i = 0; i < params.length; i++) {
            Parameter p = params[i];
            this.types.add(p.getParameterizedType().getTypeName());
            JLabel lb = new JLabel(p.getParameterizedType().getTypeName());
            lb.setAlignmentY(CENTER_ALIGNMENT);
            this.add(lb);
            inputs.add(new JTextField());
            this.add(inputs.get(i));
        }
        
        btConfig.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                List values = new ArrayList();
                
                for (int i = 0; i < inputs.size(); i++) {
                    switch(types.get(i)) {
                        case "float":
                            values.add(Float.parseFloat(inputs.get(i).getText()));
                            break;
                        case "int":
                            values.add(Integer.parseInt(inputs.get(i).getText()));
                            break;
                        case "klassen.LevelDesign":
                            //values.add(new LevelDesign(null, null, null, null, null))
                            break;
                        default:
                            break;
                    }
                }
                
                try {
                    boss = (klassen.boss.Boss) boss.getClass().getConstructors()[0].newInstance(values.toArray());
                    ready = true;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                System.out.println(boss);
                DlgConfigBoss.this.setVisible(false);
            }
        });
        btCancel.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DlgConfigBoss.this.setVisible(false);
            }
        });

        btConfig.setText("Configure");
        btCancel.setText("Cancel");
        
        this.setSize(300, 400);
        this.setLayout(new GridLayout(inputs.size()+1, 2, 5, 5));
        this.setLocationRelativeTo(null);
        
        this.add(btConfig);
        this.add(btCancel);
    }
    
    public boolean isReady() {
        return ready;
    }

    public klassen.boss.Boss getBoss() {
        return boss;
    }
}
