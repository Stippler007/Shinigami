/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klassen.editor;

import java.util.HashMap;
import java.util.Map;
import klassen.karte.*;
import klassen.karte.arrow.*;
import klassen.karte.carpet.*;
import klassen.karte.fence.*;
import klassen.karte.flowers.*;
import klassen.karte.haus.*;

/**
 *
 * @author Julian
 */
enum GO {

    GRAS, WAND, WEG, TREE, BODEN, BLUEFLOWER, YELLOWFLOWER, FENCESEITE, FENCEVORNELINKS, FENCEVORNEMID, FENCEVORNERECHTS, HAUS;
        //TODO: CARPET_FULL, FOOT_CARPET, DOOR, STONECARPET, ARROW

    private static Map<GO, GameObjects[][]> m;

    static {
        m = new HashMap<>();

        for (GO g : GO.values()) {
            switch (g) {
                case BODEN:
                    m.put(g, new GameObjects[][]{{new Boden(0)}});
                    break;
                case GRAS:
                    m.put(g, new GameObjects[][]{{new Gras(0)}});
                    break;
                case TREE:
                    m.put(g, new GameObjects[][]{
                        {new Tree(0, 0, 0), new Tree(0, 0, 1), new Tree(0, 0, 2)},
                        {new Tree(0, 1, 0), new Tree(0, 1, 1), new Tree(0, 1, 2)}
                    });
                    break;
                case WAND:
                    m.put(g, new GameObjects[][]{{new Wand(0)}});
                    break;
                case WEG:
                    m.put(g, new GameObjects[][]{{new Weg(0)}});
                    break;
                case FENCESEITE:
                    m.put(g, new GameObjects[][]{{new FenceSeite(0)}});
                    break;
                case FENCEVORNELINKS:
                    m.put(g, new GameObjects[][]{{new FenceVorneLinks(0)}});
                    break;
                case FENCEVORNEMID:
                    m.put(g, new GameObjects[][]{{new FenceVorneMid(0)}});
                    break;
                case FENCEVORNERECHTS:
                    m.put(g, new GameObjects[][]{{new FenceVorneRechts(0)}});
                    break;
                case BLUEFLOWER:
                    m.put(g, new GameObjects[][]{{new BlueFlower(0)}});
                    break;
                case YELLOWFLOWER:
                    m.put(g, new GameObjects[][]{{new YellowFlower(0)}});
                    break;
                case HAUS:
                    GameObjects[][] go = new GameObjects[11][11];

                    for (int i = 0; i < go.length; i++) {
                        for (int j = 0; j < go[i].length; j++) {
                            go[i][j] = new Haus(0, i, j);

                        }
                    }

                    m.put(g, go);
                    break;
                default:
                    StoneCarpet.class.getConstructors()[0].getParameters();
                    new StoneCarpet(0, 0, 0);
                    break;
            }
        }
    }
    
    public static GameObjects[][] getGOs(GO g) {
        return m.get(g);
    }
}
