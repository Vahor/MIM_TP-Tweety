package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.MathUtils;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.PlayerConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Tweety extends MovingEntity {

    private final float moveSpeed;
    private final float moveDuration;

    private transient State state = State.IDLE;
    private transient float stateTime = 0;
    private transient Direction direction = Direction.UP;

    public Tweety(PlayerConfiguration playerConfiguration) {
        this.moveSpeed    = playerConfiguration.getMoveSpeed();
        this.moveDuration = playerConfiguration.getMoveDuration();
    }

    public State getState() {return state;}

    public void setState(State state) {
        this.state = state;
        stateTime  = 0;
    }

    public boolean canJump() {
        return state == State.IDLE;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    @Override
    public void onLevelRestart() {
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;

        if (state == State.JUMPING && stateTime > moveDuration) {
            onJumpEnd();
        }
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        position.set(
                MathUtils.clamp(position.x, 0, world.getWidth() - getWidth()),
                MathUtils.clamp(position.y, 0, world.getHeight() - getHeight())
        );
        onJumpEnd();
        return false;
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
    }

    public void onJumpEnd() {
        setState(Tweety.State.IDLE);
        getVelocity().set(0, 0);
    }

    public void onJumpStart() {

        getVelocity().x = getDirection().getMotX() * (getMoveSpeed());
        getVelocity().y = getDirection().getMotY() * (getMoveSpeed());
        setState(Tweety.State.JUMPING);
    }

    @Override
    public float getWidth() {
        return 1f;
    }

    @Override
    public float getHeight() {
        return 1f;
    }

    @Override
    public float getRotationOffset() {
        return 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "state=" + state +
                ", super=" + super.toString() +
                '}';
    }

    public enum State {
        IDLE, JUMPING
    }
}
