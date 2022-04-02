package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.config.LetterConfiguration;
import fr.nathan.mim.game.config.LombricConfiguration;
import fr.nathan.mim.game.config.PlayerConfiguration;
import fr.nathan.mim.game.config.WordsConfiguration;
import fr.nathan.mim.game.model.GameElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class World implements Configurable {

    public static final Timer TIMER = new Timer();
    public static Random SHARED_RANDOM = new Random();

    public static World instance;

    private long seed = -1;
    private boolean debug = false;

    private float width;
    private float height;

    private transient float nextLombricTime;

    private transient Tweety player;
    private final List<Road> roads = new ArrayList<Road>(3);

    private transient String panier = "";
    private transient String currentWordObjective = "";

    private PlayerConfiguration playerConfiguration;
    private LetterConfiguration letterConfiguration;
    private WordsConfiguration wordsConfiguration;
    private LombricConfiguration lombricConfiguration;

    private transient boolean pause = false;
    private transient boolean cheat = false;

    private transient float successTime = 0;

    public LombricConfiguration getLombricConfiguration() {
        return lombricConfiguration;
    }

    public float getNextLombricTime() {
        return nextLombricTime;
    }

    public void setNextLombricTime(float nextLombricTime) {
        this.nextLombricTime = nextLombricTime;
    }

    public Tweety getPlayer() {
        return player;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public float getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(float successTime) {
        this.successTime = successTime;
    }

    public String getPanier() {
        return panier;
    }

    public void setPanier(String panier) {
        this.panier = panier;
    }

    public void setCurrentWordObjective(String currentWordObjective) {
        this.currentWordObjective = currentWordObjective;
    }

    public String getCurrentWordObjective() {
        return currentWordObjective;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isCheat() {
        return cheat;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    public GameElement generateElement() {
        return new Letter(Letter.getRandomLetter(), letterConfiguration);
    }

    public void generateNewWord() {
        String randomWord = wordsConfiguration.getWords().get(SHARED_RANDOM.nextInt(wordsConfiguration.getWords().size()));
        randomWord = randomWord.toUpperCase(); // Au cas où, on met en majuscule
        setCurrentWordObjective(
                randomWord
        );
    }

    @Override
    public void afterInitialisation() {
        if (seed != 0)
            SHARED_RANDOM = new Random(seed);
        else
            SHARED_RANDOM = new Random();


        instance = this;
        init();

    }

    public void init() {

        player = new Tweety(playerConfiguration);
        player.getPosition().set(playerConfiguration.getStartingPosition());


        for (Road road : roads) {
            road.getElements().clear();
            GameElement element = generateElement();
            road.addElement(element);
            element.afterInitialisation();
        }

        nextLombricTime = lombricConfiguration.getSpawnInterval();
        generateNewWord();
    }

    public void demoWorld() {

        instance = this;

        debug  = true;
        width  = 9;
        height = 13f;

        playerConfiguration = new PlayerConfiguration(
                1,
                .5f,
                new Vector2(width / 2, .15f));

        letterConfiguration = new LetterConfiguration(1.5f);
        wordsConfiguration  = new WordsConfiguration(new ArrayList<String>(Arrays.asList("MIM", "A", "B")));

        lombricConfiguration = new LombricConfiguration(3, 1.5f);


        player = new Tweety(playerConfiguration);
        player.getPosition().set(4, .20f);

        for (int i = (int) (height - 3); i < height; i++) {
            Road road = new Road(
                    i % 2 == 0 ? Direction.LEFT : Direction.RIGHT,
                    SHARED_RANDOM.nextFloat() + .5f,
                    i + .1f
            );
            roads.add(road);

        }

        SHARED_RANDOM = new Random();

        seed = 0;

        init();
    }
}
