package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Letter;
import fr.nathan.mim.game.model.type.Lombric;
import fr.nathan.mim.game.texture.TextureHolder;

public class LombricTexture extends TextureHolder<Lombric> {

    private final TextureAtlas atlas;

    public LombricTexture(TextureAtlas atlas) {
        this.atlas     = atlas;
    }

    @Override
    public TextureRegion getTexture(Lombric letter) {
        return atlas.findRegion(Integer.toString(letter.getId()));
    }
}
