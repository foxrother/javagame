package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler 
{
	private BufferedImage	view;
	private Rectangle 		camera;
	private int[] 			pixels;

	public RenderHandler(int width, int height) 
	{
		// Define a área visiível pelo jogador, ou seja:
		// o renderizador ignora o que está fora dessa área
		camera = new Rectangle(0, 0, width, height);

		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
	}

	private void setPixel(int pixel, int x, int y) {
		if (x >= camera.x && y >= camera.y &&
			x <= camera.x + camera.w &&
			y <= camera.y + camera.h ) {

			int pixelIndex = (x - camera.x) +
							 (y - camera.y) *
							 view.getWidth();

			if (pixelIndex < pixels.length && pixel != Game.alpha)
				pixels[pixelIndex] = pixel;
		}
	}

	public void render(Graphics graphics)
	{
		graphics.drawImage(view,
						   0, 0,
						   view.getWidth(), view.getHeight(),
						   null );
	}
	
	// @TODO: Renomear métodos
	public void renderImage(BufferedImage image,
							int xPos,  int yPos,
							int xZoom, int yZoom) {
		// @TODO Simplificar
		int[] imagePixels = ((DataBufferInt) image
											 .getRaster()
											 .getDataBuffer()
							).getData();
		
		// @TODO
		renderArray(imagePixels,
					image.getWidth(), image.getHeight(),
					xPos, yPos, xZoom, yZoom);
	}

	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom) {
		int[] rectanglePixels = rectangle.getPixels();
		if (rectanglePixels != null) renderArray(rectanglePixels,
												 rectangle.w, rectangle.h,
												 rectangle.x, rectangle.y,
												 xZoom, yZoom);
	}

	public void renderArray(int[] array,
							int width, int height,
							int xPos,  int yPos,
							int xZoom, int yZoom) {
		for (int y = 0; y < height; y++)
		for (int x = 0; x < width; 	x++)
		for (int yZ = 0; yZ < yZoom; yZ++)
		for (int xZ = 0; xZ < xZoom; xZ++) {
			int xValue = (xPos + (x * xZoom) + xZ);
			int yValue = (yPos + (y * yZoom) + yZ);
			int pixel = array[x + y * width];
			setPixel(pixel, xValue, yValue);
		}
	}

	public void renderSprite(Sprite sprite,
							 int xPos,  int yPos,
							 int xZoom, int yZoom) {
		renderArray(sprite.getPixels(),
					sprite.getWidth(), sprite.getHeight(),
					xPos, yPos,
					xZoom, yZoom);
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public Rectangle getCamera() {
		return camera;
	}
}