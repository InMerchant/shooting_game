package shooting;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {
	Image enemyView = new ImageIcon("src/image/EnemyView.png").getImage();
	int enemyX, enemyY;
	int enemyWidth = enemyView.getWidth(null);
	int enemyHeight = enemyView.getHeight(null);
	int enemyHp = 140;
	
	public Enemy(int enemyX, int enemyY) {
		this.enemyX = enemyX;
		this.enemyY = enemyY;
	}
	
	public void EnemyMove() {
		this.enemyX -= 25;
	}
}
