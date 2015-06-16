/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import java.util.HashMap;
import java.util.Map;
import klassen.minion.Minion;
import klassen.npc.Guard;
import klassen.npc.OldMan;
import klassen.npc.Sign;

/**
 *
 * @author Julian
 */
enum NPC {

    GUARD, OLDMAN, SIGN;

    public static klassen.npc.NPC getNPCs(NPC g) {
        switch (g) {
            case GUARD:
                return new Guard(0, 0, 0, null, null, null);
            case OLDMAN:
                return new OldMan(0, 0, 0, null, null, null);
            case SIGN:
                return new Sign(0, 0, null, null, null);
            default:
                return null;
        }
    }
}
