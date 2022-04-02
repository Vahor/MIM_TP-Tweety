package fr.nathan.mim.game.config;

public class LombricConfiguration {

    private float spawnInterval;
    private float downSpeed;

    // parser
    public LombricConfiguration() {
    }

    public LombricConfiguration(float spawnInterval, float downSpeed) {
        this.spawnInterval = spawnInterval;
        this.downSpeed     = downSpeed;
    }

    public float getDownSpeed() {
        return downSpeed;
    }

    public float getSpawnInterval() {
        return spawnInterval;
    }
}
