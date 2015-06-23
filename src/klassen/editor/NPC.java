/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import klassen.npc.Guard;
import klassen.npc.OldMan;
import klassen.npc.Sign;
import klassen.player.Player;

/**
 *
 * @author Julian
 */
enum NPC {

    GUARD, OLDMAN, SIGN;

    private static Player p = new Player(10, 10, 10, null, null, null, null);
    
    public static klassen.npc.NPC getNPCs(NPC g) {
        switch (g) {
            case GUARD:
                return new Guard(0, 0, 0, null, null, "");
            case OLDMAN:
                return new OldMan(0, 0, 0, null, null, "");
            case SIGN:
                return new Sign(0, 0, null, null, "");
            default:
                return null;
        }
    }
}
