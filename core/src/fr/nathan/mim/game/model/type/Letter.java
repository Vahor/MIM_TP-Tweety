package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.config.LetterConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Letter extends MovingEntity {

    private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private final String letter;
    private final float downSpeed;

    public Letter(String letter, LetterConfiguration letterConfiguration) {
        this.letter    = letter;
        this.downSpeed = letterConfiguration.getDownSpeed();
    }

    public static String getRandomLetter() {
        return letters[World.SHARED_RANDOM.nextInt(letters.length)];
    }

    public String getLetter() {
        return letter;
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
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public boolean isValid() {
        return velocity.y >= 0;
    }

    @Override
    public void onClick() {
        velocity.set(0, -downSpeed); // Lorsqu'on clique sur une lettre, elle descend
    }

    @Override
    public void afterInitialisation() {
        updateVelocity();
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        return true;
    }

    @Override
    public CollideResult onCollideWith(MovingEntity element, float delta) {
        if (element instanceof Tweety) {
            System.out.println("Collide with letter");
            return CollideResult.LETTER;
        }
        return CollideResult.NOTHING;
    }

    @Override
    public String toString() {
        return "Letter{" +
                "letter='" + letter + '\'' +
                ", super=" + super.toString() +
                '}';
    }
}
