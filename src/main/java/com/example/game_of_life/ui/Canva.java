package com.example.game_of_life.ui;

import javax.swing.JPanel;
import com.example.game_of_life.GOLApplication;
import java.awt.Color;
import java.awt.Graphics;

public class Canva extends JPanel {

    int cellSize = 10; // Taille d'une cellule en pixels

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.black);

        var i = 0;
        var width = GOLApplication.zone.ocean.length;
        var height = GOLApplication.zone.ocean[0].length;
        var cellWidth = this.getWidth() / Math.max(width, 1);
        var cellHeight = this.getHeight() / Math.max(height, 1);
        for (var row : GOLApplication.zone.ocean) {
            var j = 0;
            for (boolean b : row) {
                if (b)
                    g.fillRect(i * cellWidth, j * cellHeight, cellWidth,
                            cellHeight);
                j++;
            }
            i++;
        }

    }
}
