package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.nathan.mim.game.controller.renderer.DebugRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.texture.TextureFactory;

public class WorldRenderer extends Controller {

    private final Viewport viewport;

    DebugRenderer debugRenderer;

    public static float pixelsPerUnitX;
    public static float pixelsPerUnitY;

    public WorldRenderer(World world, Batch batch) {
        super(world, batch);
        debugRenderer = new DebugRenderer(this);

        OrthographicCamera camera = new OrthographicCamera(world.getWidth(), world.getHeight());
        viewport = new FitViewport(480, 640, camera);
        viewport.apply();


        pixelsPerUnitX = camera.viewportWidth / world.getWidth();
        pixelsPerUnitY = camera.viewportHeight / world.getHeight();

    }

    public float getPixelsPerUnitX() {
        return pixelsPerUnitX;
    }

    public float getPixelsPerUnitY() {
        return pixelsPerUnitY;
    }

    public void resize(float width, float height) {
        viewport.update((int) width, (int) height, true);
    }

    public void drawBackground() {
        batch.draw(
                TextureFactory.getInstance().getBackground(),
                0,
                0,
                viewport.getWorldWidth(),
                viewport.getWorldHeight()
        );
    }

    private void drawElement(GameElement element) {
        if (!element.isVisible()) return;
        TextureRegion region = TextureFactory.getInstance().getTexture(element);
        if (region == null) {
            System.out.println("element = " + element.getClass());
            return;
        }

        float realWidth = element.getWidth() * pixelsPerUnitX;
        float realHeight = element.getHeight() * pixelsPerUnitY;

        batch.draw(region,
                element.getX() * pixelsPerUnitX,
                element.getYWithRoadOffset() * pixelsPerUnitY,
                realWidth / 2f,
                realHeight / 2f,
                realWidth,
                realHeight,
                1,
                1,
                element.getRotationOffset()
        );

    }

    public void drawElements() {
        for (Road road : world.getRoads()) {
            for (GameElement element : road.getElements()) {
                drawElement(element);
            }
        }
    }

    public void drawHUD(float delta) {
        BitmapFont font = TextureFactory.getInstance().getFont();
        font.setColor(Color.BLACK);

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "Objectif : " + world.getCurrentWordObjective());
        font.draw(batch, glyphLayout,
                0,
                1 * pixelsPerUnitY);


        glyphLayout.setText(font, "Panier : " + world.getPanier());
        font.draw(batch, glyphLayout,
                (world.getWidth() * pixelsPerUnitX - glyphLayout.width),
                1 * pixelsPerUnitY);

        if (world.isCheat()) {
            glyphLayout.setText(font, "C");
            font.draw(batch, glyphLayout,
                    (world.getWidth() * pixelsPerUnitX - glyphLayout.width),
                    2 * pixelsPerUnitY);
        }

        if (world.getSuccessTime() > 0) {
            world.setSuccessTime(world.getSuccessTime() - delta);
            glyphLayout.setText(font, "Bravo ! ");
            font.draw(batch, glyphLayout,
                    (world.getWidth() * pixelsPerUnitX - glyphLayout.width) / 2,
                    1 * pixelsPerUnitY);
        }

    }

    @Override
    public void update(float delta) {
        batch.begin();

        drawBackground();
        drawElements();

        drawElement(world.getPlayer());
        drawHUD(delta);

        batch.end();

        debugRenderer.update(delta);

    }
}
