package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;

import java.io.File;
import java.io.IOException;

import java.lang.Runnable;
import java.lang.Thread;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JFrame implements Runnable
{
	private static final long serialVersionUID = 5480935963814446906L;
	
	public	 static int              alpha = 0xFFFF00DC;
	public			boolean          paused = false;

	public			JPanel           gamePanel = new JPanel();
	private			Canvas           gameCanvas = new Canvas();
	public			JPanel           gameMenu = new JPanel();

	private			KeyboardListener keyboardListener = new KeyboardListener();
	private			RenderHandler    renderer;

	private			SpriteSheet      sheet;
	private			Tiles            tiles;
	private			Map              map;
	private			GameObject[]     objects;
	private			Player           player;

	public Game() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 950, 700);
		setLocationRelativeTo(null);

		gameCanvas.setSize(getWidth(), getHeight());
		gamePanel.setSize(getWidth(), getHeight());
		gamePanel.add(gameCanvas);

		gameMenu.setBackground(new Color(3, 0, 10));

		setContentPane(gamePanel);
		setVisible(true);

		addKeyListener(keyboardListener);
		addFocusListener(keyboardListener);

		gameCanvas.createBufferStrategy(3);
		renderer = new RenderHandler(getWidth(), getHeight());

		BufferedImage sheetImage = loadImage("tiles.png");
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(32, 32);

		File tilesFile = new File(getClass().getResource("tiles.txt").getFile());
		tiles = new Tiles(tilesFile, sheet);

		File mapFile = new File(getClass().getResource("map.txt").getFile());
		map = new Map(mapFile, tiles);

		objects = new GameObject[1];

		player = new Player();
		objects[0] = player;
		
	}
	
	public void update() {
		for (int i = 0; i < objects.length; i++) {
			objects[i].update(this);
		}
	}

	public void render() {
		BufferStrategy bufferStrategy = gameCanvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paintComponents(graphics);

		map.renderMap(renderer, 2, 2);

		for (int i = 0; i < objects.length; i++) {
			objects[i].render(this.renderer, 2, 2);
		}

		renderer.render(graphics);
		graphics.dispose();
		bufferStrategy.show();
		renderer.clear();
	}

	// A interface Runnable permite que a classe implementadora possa
	// executar em um novo thread a partir das instruções do método run.
	public void run() {
		
		// Atualizada com a hora da última iteração
		// do laço de renderização/atualização do jogo.
		long lastRender = System.nanoTime();
		
		double changeInSeconds = 0;

		double nanoSecondConversion = 1000000000d / 57; // 
		
		long nextPauseMinimum = System.nanoTime() + 500 * 1000 * 1000;

		// Game loop
		while(true) {
			if (keyboardListener.pause() && System.nanoTime() > nextPauseMinimum) {
				paused = !paused;
				togglePanel();
				nextPauseMinimum = System.nanoTime() + 500 * 1000 * 1000;
				System.out.println(paused);
			}
			
			if (!paused) {
				long now = System.nanoTime();

				// Limita a atualização para 60 vezes por segundo
				changeInSeconds += (now - lastRender) / nanoSecondConversion;
				while(changeInSeconds >= 1) {
					update();
					render();
					changeInSeconds--;
				}
				lastRender = now; // Atualiza variável lastTime para o horário da última iteração	
			}
		}
	}

	private BufferedImage loadImage(String path) {
		try {
			BufferedImage rawImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage image = new BufferedImage(rawImage.getWidth(),
													rawImage.getHeight(),
													BufferedImage.TYPE_INT_RGB);

			image.getGraphics().drawImage(rawImage, 0, 0, null);

			return image;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public KeyboardListener getKeyboardListener() {
		return keyboardListener;
	}

	public RenderHandler getRenderer() {
		return renderer;
	}

	private void togglePanel() {
		if (paused) setContentPane(gameMenu);
		else setContentPane(gamePanel);
		this.validate();
	}

	public static void main(String[] args) {
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
}