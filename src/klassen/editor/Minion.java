/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen.editor;

import java.util.Map;
import klassen.minion.Hund;
import klassen.minion.evilguard.EvilGuard;

/**
 *
 * @author Julian
 */
public enum Minion {
    HUND, EVILGUARD;
    
    public static klassen.minion.Minion getMinions(Minion g) {
        switch (g) {
            case HUND:
                return new Hund(0, 0, 0, null, null, null, null);
            case EVILGUARD:
                return new EvilGuard(0, 0, 0, 0, null, null, null, null);
            default:
                return null;
        }
    }
}
