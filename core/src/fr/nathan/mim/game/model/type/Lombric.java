package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.config.LombricConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Lombric extends MovingEntity {

    private final float downSpeed;
    private final int id;

    public Lombric(LombricConfiguration lombricConfiguration) {
        this.downSpeed = lombricConfiguration.getDownSpeed();
        this.id        = World.SHARED_RANDOM.nextInt(2);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean isValid() {
        return false; // Le lombric ne gène pas l'apparition des elements sur la route
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
    }

    @Override
    public float getWidth() {
        return .7f;
    }

    @Override
    public float getHeight() {
        return .7f;
    }

    @Override
    public void afterInitialisation() {
        updateVelocity();
    }

    @Override
    public void updateVelocity() {
        getVelocity().set(0, -downSpeed);
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        return true;
    }

    @Override
    public CollideResult onCollideWith(MovingEntity element, float delta) {
        if (element instanceof Tweety) {
            return CollideResult.DEATH;
        }
        return CollideResult.NOTHING;
    }

    @Override
    public String toString() {
        return "Lombric{" +
                "downSpeed=" + downSpeed +
                ", super=" + super.toString() +
                '}';
    }
}
