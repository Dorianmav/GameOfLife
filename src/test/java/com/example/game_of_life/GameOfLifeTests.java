package com.example.game_of_life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import com.example.game_of_life.GameOfLife;

/**
 * 
 * Par défaut, si une cellule vivante est trop isolée (0 ou 1 voisin) alors elle
 * meurt à l'évolution suivante (mort par sous-population). Si elle est
 * raisonnablement entourée (2 ou 3 voisins) alors elle reste en vie, mais et si
 * elle est entourée de trop de cellules (4 voisins ou plus) elle meurt à la
 * génération suivante (mort par sur-population).
 * 
 * Une cellule peut aussi prendre vie, si une cellule morte est entourée de 3
 * cellules vivantes alors elle devient vivante (elle naît) à la prochaine
 * évolution (naissance par reproduction).
 */

public class GameOfLifeTests {

    GameOfLife gameoLife;

    @Test
    void isTooIsolated() throws Exception {
        gameoLife = new GameOfLife();
        gameoLife.initialize(6, 6);

        gameoLife.set(5, 5, true);

        gameoLife.set(5, 4, true);
        gameoLife.set(0, 0, true);
        assertTrue(gameoLife.isIsolated(5, 4));
    }

    @Test
    void isReasonablySurrounded() throws Exception {
        gameoLife = new GameOfLife();
        gameoLife.initialize(6, 6);

        gameoLife.set(2, 2, true);

        gameoLife.set(2, 1, true);
        gameoLife.set(2, 3, true);
        gameoLife.set(1, 2, true);
        // gameoLife.set(1, 1, true);
        assertTrue(gameoLife.isReasonablySurrounded(2, 2));
    }

    @Test
    void isOvercrowded() throws Exception {
        gameoLife = new GameOfLife();
        gameoLife.initialize(6, 6);

        gameoLife.set(2, 2, true);

        gameoLife.set(1, 1, true);
        gameoLife.set(1, 2, true);
        gameoLife.set(1, 3, true);
        gameoLife.set(2, 3, true);
        gameoLife.set(3, 3, true);
        gameoLife.set(3, 2, true);
        gameoLife.set(3, 1, true);
        gameoLife.set(2, 1, true);

        assertTrue(gameoLife.isOvercrowded(2, 2));
    }

    @Test
    void canRevive() throws Exception {
        gameoLife = new GameOfLife();
        gameoLife.initialize(6, 6);
        System.out.println("Ocean array dimensions: " + gameoLife.ocean.length + " x " + gameoLife.ocean[0].length);

        gameoLife.set(2, 2, false);

        gameoLife.set(1, 1, true);
        gameoLife.set(1, 2, true);
        gameoLife.set(1, 3, true);

        // gameoLife.set(2, 3, true);

        assertTrue(gameoLife.canRevive(2, 2));
    }

    @Test
    void ok() throws Exception {
        gameoLife = new GameOfLife();

        boolean[][] newOcean = new boolean[8][6];

        boolean[][] expected = {
                { false, false, false, false, false, false },
                { false, false, true, false, true, false },
                { false, false, false, true, false, false },
                { true, true, false, true, true, false },
                { false, false, false, true, true, false },
                { true, true, false, false, false, false },
                { false, false, false, false, true, true },
                { false, false, false, false, false, false },
        };

        for (int i = 0; i < gameoLife.ocean.length; i++) {
            for (int j = 0; j < gameoLife.ocean[0].length; j++) {
                if (gameoLife.ocean[i][j] == true) {

                    if (gameoLife.isIsolated(i, j)) {
                        // System.out.println("le point (" + i + "," + j + ") est isolé");
                        newOcean[i][j] = !gameoLife.ocean[i][j];
                    }

                    if (gameoLife.isReasonablySurrounded(i, j)) {
                        // System.out.println("le point (" + i + "," + j + ") est bien entouré");
                        newOcean[i][j] = gameoLife.ocean[i][j];
                    }

                    if (gameoLife.isOvercrowded(i, j)) {
                        // System.out.println("le point (" + i + "," + j + ") est trop entouré");
                        newOcean[i][j] = !gameoLife.ocean[i][j];
                    }
                } else {
                    if (gameoLife.canRevive(i, j)) {
                        // System.out.println("le point (" + i + "," + j + ") peut vivre");
                        newOcean[i][j] = !gameoLife.ocean[i][j];
                    }
                }
                // System.out.print(newOcean[i][j] + ",");
            }
            // System.out.println();
        }

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
               assertEquals(expected[i][j], newOcean[i][j]);
            }
            System.out.println();
        }

    }

}
