package com.example.game_of_life;

import java.util.Random;

/**
 * 
 * Par défaut, si une cellule vivante est trop isolée (0 ou 1 voisin) alors elle
 * meurt à l'évolution suivante (mort par sous-population).
 * 
 * Si elle est raisonnablement entourée (2 ou 3 voisins) alors elle reste en
 * vie, mais et si
 * elle est entourée de trop de cellules (4 voisins ou plus) elle meurt à la
 * génération suivante (mort par sur-population).
 * 
 * Une cellule peut aussi prendre vie, si une cellule morte est entourée de 3
 * cellules vivantes alors elle devient vivante (elle naît) à la prochaine
 * évolution (naissance par reproduction).
 */
public class GameOfLife {

    public boolean[][] ocean;

    public void initialize(int x, int y) {
        ocean = new boolean[x][y];
    }

    public void set(int x, int y, boolean bool) {
        ocean[x][y] = bool;
    }

    public boolean[][] generate(int width, int height) {
        // Création d'un objet Random pour générer des nombres aléatoires
        Random random = new Random();

        // Déclaration et création de la matrice
        ocean = new boolean[width][height];

        // Remplissage de la matrice avec des valeurs aléatoires
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Générer un booléen aléatoire (true/false)
                ocean[i][j] = random.nextBoolean();
            }
        }

        return ocean;
    }

    public void random() {
        Random r = new Random();
        for (int i = 0; i < ocean.length; i++) {
            for (int j = 0; j < ocean[i].length; j++) {
                ocean[i][j] = r.nextBoolean();
                System.out.print(ocean[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isAlive(int x, int y) {
        return ocean[x][y];
    }

    public boolean isIsolated(int x, int y) throws Exception {
        // if the cell is alive
        int compteur = 0;

        if ((x < 0 || x > ocean.length - 1) || (y < 0 || y > ocean[0].length - 1)
                || (x < 0 || x > ocean.length - 1) && (y < 0 || y > ocean[0].length - 1)) {
            throw new Exception("the point (" + x + "," + y + ") is outside the ocean");
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == 0) || x + i < 0 || x + i > ocean.length - 1 || y + j < 0
                        || y + j > ocean[0].length - 1) {
                    continue;
                }

                int newx = x + i;
                int newy = y + j;

                if (ocean[newx][newy])
                    compteur++;
            }
        }
        return compteur <= 1;
        // kill the cell
    }

    public boolean isReasonablySurrounded(int x, int y) throws Exception {
        // if the cell is alive
        int count = 0;

        if ((x < 0 || x > ocean.length - 1) || (y < 0 || y > ocean[0].length - 1)
                || (x < 0 || x > ocean.length - 1) && (y < 0 || y > ocean[0].length - 1)) {
            throw new Exception("the point (" + x + "," + y + ") is outside the ocean");
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == 0) || x + i < 0 || x + i > ocean.length - 1 || y + j < 0
                        || y + j > ocean[0].length - 1) {
                    continue;
                }

                int newx = x + i;
                int newy = y + j;

                if (ocean[newx][newy])
                    count++;
            }
        }

        return (count == 2 || count == 3);
        // keep alive
    }

    public boolean isOvercrowded(int x, int y) throws Exception {
        // if the cell is alive
        int count = 0;

        if ((x < 0 || x > ocean.length - 1) || (y < 0 || y > ocean[0].length - 1)
                || (x < 0 || x > ocean.length - 1) && (y < 0 || y > ocean[0].length - 1)) {
            throw new Exception("the point (" + x + "," + y + ") is outside the ocean");
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == 0) || x + i < 0 || x + i > ocean.length - 1 || y + j < 0
                        || y + j > ocean[0].length - 1) {
                    continue;
                }

                int newx = x + i;
                int newy = y + j;

                if (ocean[newx][newy])
                    count++;
            }
        }

        return count >= 4;
        // kill the cell
    }

    public boolean canRevive(int x, int y) throws Exception {
        // if the cell is dead

        int count = 0;
        if ((x < 0 || x > ocean.length - 1) || (y < 0 || y > ocean[0].length - 1)) {
            throw new Exception("the point (" + x + "," + y + ") is outside the ocean");
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i == 0 && j == 0) || x + i < 0 || x + i > ocean.length - 1 || y + j < 0
                        || y + j > ocean[0].length - 1) {
                    continue;
                }

                int newx = x + i;
                int newy = y + j;

                if (ocean[newx][newy])
                    count++;
            }
        }
        return count == 3;
        // revive the cell
    }

    public boolean[][] deepCloneOcean(boolean[][] source) {
        int rows = source.length;
        int cols = source[0].length;
        boolean[][] target = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                target[i][j] = source[i][j];
            }
        }
        return target;
    }
}