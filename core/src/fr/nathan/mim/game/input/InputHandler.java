package fr.nathan.mim.game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.controller.WorldController;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;

public class InputHandler implements InputProcessor, GestureDetector.GestureListener {

    private final WorldController worldController;
    private final World world;

    public InputHandler(WorldController worldController) {
        this.worldController = worldController;
        this.world           = worldController.getWorld();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT)
            worldController.onLeftPressed();
        if (keycode == Input.Keys.RIGHT)
            worldController.onRightPressed();

        if (keycode == Input.Keys.D)
            world.setDebug(!world.isDebug());
        if (keycode == Input.Keys.ESCAPE)
            world.setPause(!world.isPause());
        if (keycode == Input.Keys.C)
            world.setCheat(!world.isCheat());
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            worldController.onLeftReleased();
        if (keycode == Input.Keys.RIGHT)
            worldController.onRightReleased();
        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (world.isPause()) return false;
        for (Road road : world.getRoads()) {
            for (GameElement element : road.getElements()) {
                if (element.collideWith(
                        (int) (x / WorldRenderer.pixelsPerUnitX),
                        (int) (world.getHeight() - (y / WorldRenderer.pixelsPerUnitY)),
                        1,
                        1)) {
                    element.onClick();
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(float screenX, float screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
