package game;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private			int[] pixels;
	private			BufferedImage image;
	public final	int SIZEX;
	public final	int SIZEY;
	private			boolean loaded = false;
	private			Sprite[] loadedSprites = null;

	private int spriteSizeX;
	private int spriteSizeY;

	public SpriteSheet(BufferedImage sheetImage) {
		image = sheetImage;
		SIZEX = sheetImage.getWidth();
		SIZEY = sheetImage.getHeight();

		pixels = new int[SIZEX * SIZEY];
		pixels = image.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
	}
	
	public void loadSprites(int spriteSizeX, int spriteSizeY) {

		this.spriteSizeX = spriteSizeX;
		this.spriteSizeY = spriteSizeY;

		loadedSprites = new Sprite[(SIZEX / spriteSizeX) * (SIZEY / spriteSizeY)];

		int spriteID = 0;
		for (int y = 0; y < SIZEY; y += spriteSizeY) 
		for (int x = 0; x < SIZEX; x += spriteSizeX) {
			loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
			spriteID++;
		}
	}

	public Sprite getSprite(int x, int y) {
		if (!loaded) {
			int spriteID = x + y * (SIZEX / spriteSizeX);

			if (spriteID < loadedSprites.length) return loadedSprites[spriteID];
			else System.out.println("Sprite out of bounds.");
		} else System.out.println("SpriteSheet needs to be loaded before getting a sprite.");
		
		return null;
	}

	public int[] getPixels() {
		return pixels;
	} 

	public BufferedImage getImage() {
		return image;
	}
}