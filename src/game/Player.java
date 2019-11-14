package game;

public class Player implements GameObject {

    Rectangle playerRectangle;
    int speed = 5;

    public Player() {
        playerRectangle = new Rectangle(0, 0, 32, 64);
        playerRectangle.generateGraphics(2, 0xFF00FF00);
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom) {
        renderer.renderRectangle(playerRectangle, xZoom, yZoom);
    }

    public void update(Game game) {
        KeyboardListener keyboardListener = game.getKeyboardListener();
        
        if (keyboardListener.up())    { playerRectangle.y -= speed; }
        if (keyboardListener.right()) { playerRectangle.x += speed; }
        if (keyboardListener.down())  { playerRectangle.y += speed; }
        if (keyboardListener.left())  { playerRectangle.x -= speed; }

        updateCamera(game.getRenderer().getCamera());
    }

    public void updateCamera(Rectangle camera) {
        camera.x = playerRectangle.x - (camera.w / 2);
        camera.y = playerRectangle.y - (camera.h / 2);
    }
}