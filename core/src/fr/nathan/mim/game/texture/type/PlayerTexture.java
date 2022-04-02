package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Tweety;
import fr.nathan.mim.game.texture.TextureHolder;

public class PlayerTexture extends TextureHolder<Tweety> {

    private final TextureRegion idle;

    public PlayerTexture(TextureRegion idle) {
        this.idle = idle;
    }

    @Override
    public TextureRegion getTexture(Tweety player) {
        return idle;
    }

}
