package fr.nathan.mim.game.config;

public class LetterConfiguration {

    private float downSpeed;

    // parser
    public LetterConfiguration() {
    }

    public LetterConfiguration(float downSpeed) {
        this.downSpeed = downSpeed;
    }

    public float getDownSpeed() {
        return downSpeed;
    }

}
