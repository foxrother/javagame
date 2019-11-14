package game;

public class Rectangle {
	public  int x, y, w, h;
	private int[] pixels;

	public Rectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Rectangle() {
		this(0, 0, 0, 0);
	}

	public void generateGraphics(int color) {
		pixels = new int[w * h];
		for (int j = 0; j < h; j++) {
		for (int i = 0; i < w; i++)
			pixels[i + j * w] = color;
		}
	}

	public void generateGraphics(int borderWidth, int color) {
		pixels = new int[w * h];

		// Preenche o vetor com a cor 'alpha', que é ignorada pelo renderizador
		for (int i = 0; i < pixels.length; i++) pixels[i] = Game.alpha;

		for (int y = 0; y < borderWidth; y++) {			  // Topo
		for (int x = 0; x < w; x++) 
			pixels[x + y * w] = color;
		}
		
		for (int x = 0; x < borderWidth; x++) {			  // Esquerda
		for (int y = 0; y < h; y++) 
			pixels[x + y * w] = color;
		}
		
		for (int y = h - 1; y >= h - borderWidth; y--) {  // Base
		for (int x = 0; x < w; x++) 
			pixels[x + y * w] = color;
		}

		for (int x = w - 1; x >= w - borderWidth; x--) {  // Direita
		for (int y = 0; y < h; y++) 
			pixels[x + y * w] = color;
		}
	}

	public int[] getPixels() {
		// @TODO fault-tolerance
		return pixels;
	}
}