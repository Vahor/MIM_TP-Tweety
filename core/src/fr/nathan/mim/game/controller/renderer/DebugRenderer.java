package fr.nathan.mim.game.controller.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fr.nathan.mim.game.controller.Controller;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.texture.TextureFactory;

public class DebugRenderer extends Controller {

    private final WorldRenderer worldRenderer;
    private final ShapeRenderer shapeRenderer;

    public DebugRenderer(WorldRenderer worldRenderer) {
        super(worldRenderer.getWorld(), worldRenderer.getBatch());
        this.worldRenderer = worldRenderer;
        shapeRenderer      = new ShapeRenderer();
    }

    public void update(float delta) {

        if (!world.isDebug()) return;

        BitmapFont font = TextureFactory.getInstance().getFont();

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        // Ajout des lignes verticales
        for (int col = 0; col < world.getWidth(); col++) {
            shapeRenderer.line(
                    col * worldRenderer.getPixelsPerUnitX(),
                    0,
                    col * worldRenderer.getPixelsPerUnitX(),
                    world.getHeight() * worldRenderer.getPixelsPerUnitY()
            );
        }

        // Ajout des lignes horizontales
        for (int row = 0; row < world.getHeight(); row++) {
            shapeRenderer.line(
                    0,
                    row * worldRenderer.getPixelsPerUnitY(),
                    world.getWidth() * worldRenderer.getPixelsPerUnitX(),
                    row * worldRenderer.getPixelsPerUnitY()
            );
        }



        shapeRenderer.end();

    }
}
