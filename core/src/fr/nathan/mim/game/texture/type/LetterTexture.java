package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Letter;
import fr.nathan.mim.game.texture.TextureHolder;

public class LetterTexture extends TextureHolder<Letter> {

    private final TextureAtlas atlas;

    public LetterTexture(TextureAtlas atlas) {
        this.atlas     = atlas;
    }

    @Override
    public TextureRegion getTexture(Letter letter) {
        return atlas.findRegion(letter.getLetter());
    }
}
