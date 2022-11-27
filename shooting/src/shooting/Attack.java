package shooting;
import java.awt.*;


import javax.swing.*;

public class Attack {
	Image attackView = new ImageIcon("src/image/attackView.png").getImage();
	int attackX, attackY;
	int attackWidth = attackView.getWidth(null);
	int attackHeight = attackView.getHeight(null);
	int attack = 4;
	
	public Attack(int attackX, int attackY) {
		this.attackX = attackX;
		this.attackY = attackY;
	}
	
	public void shot() {
		this.attackX += 80;
	}
}
