package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles {
	private SpriteSheet spriteSheet;
	private ArrayList<Tile> tilesList = new ArrayList<Tile>();

	// Sprites precisam ter sido carregadas
	public Tiles(File tilesFile, SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
		
		try {
			Scanner scanner = new Scanner(tilesFile);
		
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("//")) {
					String[] splitString = line.split(":");

					String tileName = splitString[0];
					int	   spriteX  = Integer.parseInt(splitString[1]);
					int	   spriteY  = Integer.parseInt(splitString[2]);

					Tile tile = new Tile(tileName, this.spriteSheet.getSprite(spriteX, spriteY));
					tilesList.add(tile);
				}
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void renderTile(int tileID, RenderHandler renderer,
						   int xPos,   int yPos,
						   int xZoom,  int yZoom) {
		if (tileID < tilesList.size()) {
			renderer.renderSprite(tilesList.get(tileID).sprite,
								  xPos, yPos, xZoom, yZoom);
		} else {
			System.out.println("TileID " + tileID + " is out of bounds.");
		}
		
	}

	class Tile {
		public String tileName;
		public Sprite sprite;

		public Tile(String tileName, Sprite sprite) {
			this.tileName = tileName;
			this.sprite	  = sprite;
		}
	}
}