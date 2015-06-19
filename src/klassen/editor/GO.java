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

    GRAS, WAND, WEG, TREE, BODEN, BLUEFLOWER, YELLOWFLOWER, FENCESEITE, FENCEVORNELINKS, FENCEVORNEMID, FENCEVORNERECHTS, HAUS, HAUS2, HAUS3, HAUS4, FOOT_CARPET, STONECARPET, CARPET_FULL, DOOR, ARROW;

    private static Map<GO, GameObjects[][]> m;

    static {
        m = new HashMap<>();

        m.put(BODEN, new GameObjects[][]{{new Boden(0)}});

        m.put(GRAS, new GameObjects[][]{{new Gras(0)}});

        m.put(TREE, new GameObjects[][]{
            {new Tree(0, 0, 0), new Tree(0, 0, 1), new Tree(0, 0, 2)},
            {new Tree(0, 1, 0), new Tree(0, 1, 1), new Tree(0, 1, 2)}
        });

        m.put(WAND, new GameObjects[][]{{new Wand(0)}});

        m.put(WEG, new GameObjects[][]{{new Weg(0)}});

        m.put(FENCESEITE, new GameObjects[][]{{new FenceSeite(0)}});

        m.put(FENCEVORNELINKS, new GameObjects[][]{{new FenceVorneLinks(0)}});

        m.put(FENCEVORNEMID, new GameObjects[][]{{new FenceVorneMid(0)}});

        m.put(FENCEVORNERECHTS, new GameObjects[][]{{new FenceVorneRechts(0)}});

        m.put(BLUEFLOWER, new GameObjects[][]{{new BlueFlower(0)}});

        m.put(YELLOWFLOWER, new GameObjects[][]{{new YellowFlower(0)}});

        GameObjects[][] go = new GameObjects[11][11];
        for (int i = 0; i < go.length; i++) {
            for (int j = 0; j < go[i].length; j++) {
                go[i][j] = new Haus(0, i, j);
            }
        }
        m.put(HAUS, go);

        go = new GameObjects[11][11];
        for (int i = 0; i < go.length; i++) {
            for (int j = 0; j < go[i].length; j++) {
                go[i][j] = new Haus2(0, i, j);
            }
        }
        m.put(HAUS2, go);

        go = new GameObjects[11][11];
        for (int i = 0; i < go.length; i++) {
            for (int j = 0; j < go[i].length; j++) {
                go[i][j] = new Haus3(0, i, j);
            }
        }
        m.put(HAUS3, go);

        go = new GameObjects[11][11];
        for (int i = 0; i < go.length; i++) {
            for (int j = 0; j < go[i].length; j++) {
                go[i][j] = new Haus4(0, i, j);
            }
        }
        m.put(HAUS4, go);

        m.put(FOOT_CARPET, new GameObjects[][]{{new FootCarpet(0, 0, 0, 0, 0, 0, null)}, {new FootCarpet(0, 1, 0, 0, 0, 0, null)}});

        go = new GameObjects[3][3];

        for (int i = 0; i < go.length; i++) {
            for (int j = 0; j < go[i].length; j++) {
                go[i][j] = new StoneCarpet(0, i, j);

            }
        }

        m.put(STONECARPET, go);

        go = new GameObjects[3][3];

        for (int i = 0; i < go.length; i++) {
            for (int j = 0; j < go[i].length; j++) {
                go[i][j] = new Carpet_Full(0, i, j);

            }
        }

        m.put(CARPET_FULL, go);

        m.put(DOOR, new GameObjects[][]{{new Door(0, 0, 0, null, 0, 0, 0), new Door(0, 0, 1, null, 0, 0, 0)}, {new Door(0, 1, 0, null, 0, 0, 0), new Door(0, 1, 1, null, 0, 0, 0)}});

        m.put(ARROW, new GameObjects[][]{{new Arrow(0, 0, 0)}});
    }

    public static GameObjects[][] getGOs(GO g) {
        return m.get(g);
    }
}
