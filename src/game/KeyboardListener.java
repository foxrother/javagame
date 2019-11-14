package game;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class KeyboardListener implements KeyListener, FocusListener {

    public boolean[] keys = new boolean[256];

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keys.length)
            keys[keyCode] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keys.length)
            keys[keyCode] = false;
    }

    @Override
    public void focusLost(FocusEvent e) {
        for (int i = 0; i < keys.length; i++)
            keys[i] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void focusGained(FocusEvent e) {}

    public boolean up() {
        return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
    }

    public boolean right() {
        return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }

    public boolean down() {
        return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
    }

    public boolean left() {
        return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
    }

    public boolean pause() {
        return keys[KeyEvent.VK_P] || keys[KeyEvent.VK_ESCAPE];
    }
}