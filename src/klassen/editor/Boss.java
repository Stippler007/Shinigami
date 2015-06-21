/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.editor;

import java.util.HashMap;
import java.util.Map;
import klassen.boss.hundeGhoul.HundeGhoul;

/**
 *
 * @author Julian
 */
enum Boss {
    HUNDEGHOUL;
    
    private static Map<Boss, klassen.boss.Boss> m = new HashMap<>();
    
    static {
        m.put(HUNDEGHOUL, new HundeGhoul(10, 10, 10, null, null, 10, null, null, null));
    }
    
    public static klassen.boss.Boss getBoss(Boss g) {
        return m.get(g);
    }
}
