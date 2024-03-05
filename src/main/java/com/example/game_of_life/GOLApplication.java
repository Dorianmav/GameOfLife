package com.example.game_of_life;

import javax.swing.JFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.game_of_life.ui.Canva;

@SpringBootApplication
public class GOLApplication {

	public static JFrame jFrame;

	public static Canva canva;

	public static GameOfLife zone = new GameOfLife();

	public static int frameHeight;

	public static int frameWidth;

	public static Thread thread;

	public static void main(String[] args) {

		jFrame = new JFrame("Game of life");
		canva = new Canva();

		frameWidth = 760;
		frameHeight = 660;

		jFrame.setSize(frameWidth, frameHeight);
		jFrame.setContentPane(canva);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		zone.generate(200, 200);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					// long timestamps = System.currentTimeMillis(); 
					try {
						// Effectuer une copie profonde de zone.ocean
						boolean[][] newZone = zone.deepCloneOcean(zone.ocean);

						for (int i = 0; i < zone.ocean.length; i++) {
							for (int j = 0; j < zone.ocean[0].length; j++) {
								if (zone.ocean[i][j]) {

									if (zone.isIsolated(i, j)) {
										newZone[i][j] = !zone.ocean[i][j];
									}

									if (zone.isReasonablySurrounded(i, j)) {
										newZone[i][j] = zone.ocean[i][j];
									}

									if (zone.isOvercrowded(i, j)) {
										newZone[i][j] = !zone.ocean[i][j];
									}
								} else {
									if (zone.canRevive(i, j)) {
										newZone[i][j] = !zone.ocean[i][j];
									}
								}
							}
						}
						zone.ocean = newZone;
						// System.out.println("time : " + (System.currentTimeMillis() - timestamps));

						Thread.sleep(26);

						canva.repaint();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}