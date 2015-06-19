/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klassen;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import klassen.boss.Boss;
import klassen.karte.GameObjects;
import klassen.minion.Minion;
import klassen.npc.NPC;

/**
 *
 * @author Julian
 */
public class Level implements Serializable {
    private String id;
    private GameObjects[][] map;
    private List<Minion> minions;
    private List<NPC> npcs;
    private Boss boss;
    
    public Level(String id, GameObjects[][] map) {
        this(id, map, new ArrayList<Minion>(), new ArrayList<NPC>(), null);
    }
    
    public Level(String id, GameObjects[][] map, List<Minion> minions, List<NPC> npcs, Boss boss) {
        this.id = id;
        this.map = map;
        this.minions = minions;
        this.npcs = npcs;
        this.boss = boss;
    }
    
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        System.out.println(stream);
        stream.defaultReadObject();
        System.out.println(stream);
    }

    public String getId() {
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

    public Boss getBoss() {
        return boss;
    }
}
