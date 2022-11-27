package shooting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.lang.Math;
import java.awt.Font;

public class Game extends Thread {
	private int delay = 20; //게임의 딜레이
	private long pretime; // 현재 시간 저장
	private int cnt; //딜레이마다 증가할 cnt선언
	private int score; // 게임 내 점수
	
	private Image playerView = new ImageIcon("src/image/playerView.png").getImage();
	
	private int playerX, playerY;
	private int playerWidth = playerView.getWidth(null);
	private int playerHeight = playerView.getHeight(null);
	private int playerSpeed = 15; //플레이어 이동거리
	private int playerHp = 3;
	
	//플레이어 움직임 제어 변수
	private boolean up, down, left, right, shooting;
	
	//점수, 게임 오버 제어
	private boolean gameOver;
	
	ArrayList<Attack> attackArrayList = new ArrayList<Attack>();
	ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();
	
	private Attack attack;
	private Enemy enemy;
	
	//cnt초기화, 플레이어 위치 초기화
	public void run() {
		cnt = 0;
		playerX = 10;
		playerY = (ShootingGame.SCREEN_HEIGHT - playerHeight) / 2;
		
		//cnt가 delay 밀리초가 지날 때마다 증가
		while(true) {
			while(!gameOver) {
				pretime = System.currentTimeMillis();
				if(System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						keyProcess();
						attackProcess();
						enemySpawnProcess();
						enemyMoveProcess();
						cnt++;
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}	
	}
	
	//캐릭터와 공격이 화면을 넘어가지 않도록 설정
	private void keyProcess() {
		if(up && playerY - playerSpeed > 0) playerY -= playerSpeed;
		if(down && playerY + playerHeight + playerSpeed < ShootingGame.SCREEN_HEIGHT) playerY += playerSpeed;
		if(left && playerX - playerSpeed > 0) playerX -= playerSpeed;
		if(right && playerX + playerWidth + playerSpeed < ShootingGame.SCREEN_WIDTH) playerX += playerSpeed;
		if(shooting && cnt % 15 == 0) {
			attack = new Attack(playerX, playerY - 160);
			attackArrayList.add(attack);
		}
	}
	
	private void attackProcess() {
		for(int i = 0; i < attackArrayList.size(); i++) {
			attack = attackArrayList.get(i);
			attack.shot();
			
			//공격에 적이 맞았을 시
			for(int j = 0; j < enemyArrayList.size(); j++) {
				if(Math.abs((attack.attackX + attack.attackWidth / 2) - (enemy.enemyX + attack.attackWidth / 2)) < (enemy.enemyWidth / 2 + attack.attackWidth / 2) && 
						Math.abs((attack.attackY + attack.attackHeight / 2) - (enemy.enemyY + enemy.enemyHeight / 2)) < (enemy.enemyHeight / 2 + attack.attackHeight / 2))  {
					enemy.enemyHp -= attack.attack;
				}
				if(enemy.enemyHp <= 0) {
					enemyArrayList.remove(enemy);
					score += 100;
				}
			}
		}
	}
	
	//적 소환 메소드
	private void enemySpawnProcess() {
		if(cnt % 40 == 0) {
			enemy = new Enemy(1180, (int)(Math.random()*ShootingGame.SCREEN_HEIGHT)-20);
			enemyArrayList.add(enemy);
		}
	}
	
	//적 움직임 메소드
	private void enemyMoveProcess() {
		for(int i = 0; i < enemyArrayList.size(); i++) {
			enemy = enemyArrayList.get(i);
			enemy.EnemyMove();
			
			//적 객체가 화면 밖으로 나갈 시
			if(enemy.enemyX < 10) {
				enemy.enemyX = 1180;
				enemy.enemyY = (int)(Math.random()*ShootingGame.SCREEN_HEIGHT-3)-20;
			}
			
			//적과 충돌 처리
			if(Math.abs((this.playerX + this.playerWidth / 2) - (enemy.enemyX + this.playerWidth / 2)) < (enemy.enemyWidth / 2 + this.playerWidth / 2) && 
					Math.abs((this.playerY + this.playerHeight / 2) - (enemy.enemyY + enemy.enemyHeight / 2)) < (enemy.enemyHeight / 2 + this.playerHeight / 2))  {
				this.playerHp -= 1; // 플레이어 HP 감소
				enemyArrayList.remove(enemy); // 충돌한 적 파괴
				if(this.playerHp <= 0) gameOver = true; //플레이어 체력이 없을 시 게임오버
			}
			
			//체력이 없는 적 파괴
			if(enemy.enemyHp <= 0) {
				enemyArrayList.remove(enemy);
			}
		}
	}
	//게임시작 했을 때의 요소들 그리는 메소드
	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		infoDraw(g);
	}
	
	//게임 정보 그리는 메소드
	public void infoDraw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 40, 80);
		if(gameOver) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("GAME OVER", 300, 380);
			g.drawString("Press ESC to Out", 300, 480);
		}
	}
	
	//플레이어 쪽 요소들 그릴 메소드
	public void playerDraw(Graphics g) {
		g.drawImage(playerView, playerX, playerY, null);
		g.setColor(Color.GREEN);
		g.fillRect(playerX - 1, playerY - 40, playerHp * 30, 20);
		for(int i = 0; i < attackArrayList.size(); i++) {
			attack = attackArrayList.get(i);
			g.drawImage(attack.attackView, attack.attackX, attack.attackY, null);
		}		
	}
	
	public void enemyDraw(Graphics g) {
		for(int i = 0; i < enemyArrayList.size(); i++) {
			enemy = enemyArrayList.get(i);
			g.drawImage(enemy.enemyView, enemy.enemyX, enemy.enemyY, null);
			g.setColor(Color.GREEN);
			g.fillRect(enemy.enemyX - 40, enemy.enemyY - 40, enemy.enemyHp * 2, 20);
		}	
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
}
