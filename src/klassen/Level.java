/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen;

import java.util.List;
import klassen.karte.GameObjects;
import klassen.minion.Minion;
import klassen.npc.NPC;

/**
 *
 * @author Julian
 */
public class Level {
    private int id;
    private GameObjects[][] map;
    private List<Minion> minions;
    private List<NPC> npcs;
    
    public Level(int id, GameObjects[][] map, List<Minion> minions, List<NPC> npcs) {
        this.id = id;
        this.map = map;
        this.minions = minions;
        this.npcs = npcs;
    }

    public int getId() {
        return id;
    }
    
    public GameObjects[][] getMap() {
        return map;
    }

    public List<Minion> getMinions() {
        return minions;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }
}
