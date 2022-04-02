package fr.nathan.mim.game.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Letter;
import fr.nathan.mim.game.model.type.Lombric;
import fr.nathan.mim.game.model.type.Tweety;
import fr.nathan.mim.game.texture.type.LetterTexture;
import fr.nathan.mim.game.texture.type.LombricTexture;
import fr.nathan.mim.game.texture.type.PlayerTexture;

import java.util.HashMap;
import java.util.Map;

public class TextureFactory {

    private static TextureFactory instance = null;

    private final Map<Class<? extends GameElement>, TextureHolder<? extends GameElement>> textureMap = new HashMap<Class<? extends GameElement>, TextureHolder<? extends GameElement>>();

    private final Texture background;
    private final Texture background_blur;
    private final Skin rainbowUISkin;
    private final BitmapFont font;

    public TextureFactory() {


        background      = new Texture(Gdx.files.internal("background.png"));
        background_blur = new Texture(Gdx.files.internal("background_blur.png"));

        font          = new BitmapFont(Gdx.files.internal("pack/freezing/raw/font-export.fnt"));
        rainbowUISkin = new Skin(Gdx.files.internal("pack/freezing/skin/freezing-ui.json"));

        font.setUseIntegerPositions(false);

        textureMap.put(Letter.class, new LetterTexture(
                new TextureAtlas(Gdx.files.internal("letters/pack.txt"))
        ));

        textureMap.put(Tweety.class, new PlayerTexture(
                new TextureRegion(new Texture(Gdx.files.internal("player/bonhomme.png"))
                )));

        textureMap.put(Lombric.class, new LombricTexture(
                new TextureAtlas(Gdx.files.internal("lombric/pack.txt"))
        ));
    }

    public static TextureFactory getInstance() {
        if (instance == null) {
            instance = new TextureFactory();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameElement> TextureRegion getTexture(T model) {
        TextureHolder<T> holder = (TextureHolder<T>) textureMap.get(model.getClass());
        if (holder == null) return null;
        return holder.getTexture(model);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameElement> TextureHolder<T> getTextureHolder(Class<T> clazz) {
        return (TextureHolder<T>) textureMap.get(clazz);
    }

    public Texture getBackground() {
        return background;
    }

    public Texture getBackgroundBlur() {
        return background_blur;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Skin getSkin() {
        return rainbowUISkin;
    }
}
