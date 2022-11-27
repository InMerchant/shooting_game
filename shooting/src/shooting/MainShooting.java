package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainShooting extends JFrame {
	
	// 더블 버퍼링 변수
	private Image background;
	private Graphics screenGraphic;
	
	private Image mainView = new ImageIcon("src/image/mainView.png").getImage();
	private Image gameView = new ImageIcon("src/image/gameView.png").getImage();
	
	//화면 컨트롤 변수
	private boolean ctrlMainView, ctrlGameView;
	
	public static Game game = new Game();
	
	// 창 제목, 크기, 크기 조절 여부
	public MainShooting() {
		setTitle("Shooting Game");//창 타이틀바의 내용
		setUndecorated(true); //테두리 없는 창
		setSize(ShootingGame.SCREEN_WIDTH, ShootingGame.SCREEN_HEIGHT);//창의 가로 세로 길이 설정
		setResizable(false);//창 사이즈 조절 불가
		setLocationRelativeTo(null);//창을 화면 가운데에 띄우기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//창 종료 시 프로세스까지 종료
		setVisible(true);//창을 화면에 나타낼 것인지
		setLayout(null);
		
		init();
	}
	
	//초기화 메소드
	public void init() {
		ctrlMainView = true;
		ctrlGameView = false;
		
		//창 종료 버튼기능 추가
		addKeyListener(new KeyListener());
	}
	
	//게임화면 넘어가게 해주는 메소드
	private void gameStart() {
		ctrlMainView = false;
		ctrlGameView = true;
		
		game.start();
	}
	
	//더블 버퍼링, 버퍼 이미지 만들기
	public void paint(Graphics g) {
		background = createImage(ShootingGame.SCREEN_WIDTH, 
				ShootingGame.SCREEN_HEIGHT);
		screenGraphic = background.getGraphics();
		draw(screenGraphic);
		g.drawImage(background, 0, 0, null);
	}
	
	//필요한 요소(배경) 그리기
	public void draw(Graphics g) {
		if(ctrlMainView) {
			g.drawImage(mainView, 0, 0, null);
		}
		if(ctrlGameView) {
			g.drawImage(gameView, 0, 0, null);
			game.gameDraw(g);
		}		
		this.repaint();
	}
	
	//메인 화면에서 키입력 기능 추가
	class KeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				//ESE키 누를 시 창 종료
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;
				case KeyEvent.VK_ENTER:
					gameStart();
					break;
				case KeyEvent.VK_W:
					game.setUp(true);
					break;
				case KeyEvent.VK_A:
					game.setLeft(true);
					break;
				case KeyEvent.VK_S:
					game.setDown(true);
					break;
				case KeyEvent.VK_D:
					game.setRight(true);
					break;
				case KeyEvent.VK_UP:
					game.setUp(true);
					break;
				case KeyEvent.VK_LEFT:
					game.setLeft(true);
					break;
				case KeyEvent.VK_DOWN:
					game.setDown(true);
					break;
				case KeyEvent.VK_RIGHT:
					game.setRight(true);
					break;
				case KeyEvent.VK_SPACE:
					game.setShooting(true);
					break;
			}
		}
		
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					game.setUp(false);
					break;
				case KeyEvent.VK_A:
					game.setLeft(false);
					break;
				case KeyEvent.VK_S:
					game.setDown(false);
					break;
				case KeyEvent.VK_D:
					game.setRight(false);
					break;
				case KeyEvent.VK_UP:
					game.setUp(false);
					break;
				case KeyEvent.VK_LEFT:
					game.setLeft(false);
					break;
				case KeyEvent.VK_DOWN:
					game.setDown(false);
					break;
				case KeyEvent.VK_RIGHT:
					game.setRight(false);
					break;
				case KeyEvent.VK_SPACE:
					game.setShooting(false);
					break;
			}
		}
	}
	
}
