package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

public class PlayerConfiguration {

    private float moveSpeed;
    private float moveDuration;
    private Vector2 startingPosition;

    // parser
    public PlayerConfiguration() {
    }

    public PlayerConfiguration(float moveSpeed, float moveDuration, Vector2 startingPosition) {
        this.moveSpeed        = moveSpeed;
        this.moveDuration     = moveDuration;
        this.startingPosition = startingPosition;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getMoveDuration() {
        return moveDuration;
    }


    public Vector2 getStartingPosition() {
        return startingPosition;
    }


    @Override
    public String toString() {
        return "PlayerConfiguration{" +
                ", jumpDistance=" + moveSpeed +
                ", startingPosition=" + startingPosition +
                '}';
    }
}
