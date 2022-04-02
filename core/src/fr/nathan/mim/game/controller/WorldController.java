package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import fr.nathan.mim.game.Client;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;
import fr.nathan.mim.game.model.type.Letter;
import fr.nathan.mim.game.model.type.Lombric;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.Tweety;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.screen.GameOverScreen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldController extends Controller {

    private final Map<Keys, Boolean> pressedKeys = new HashMap<Keys, Boolean>(4, 1);

    public WorldController(World world, Batch batch) {
        super(world, batch);

        for (Keys value : Keys.values()) {
            pressedKeys.put(value, false);
        }
    }

    public World getWorld() {return world;}

    public void onLeftPressed() {
        pressedKeys.put(Keys.LEFT, true);
    }

    public void onLeftReleased() {
        pressedKeys.put(Keys.LEFT, false);
    }

    public boolean isLeftPressed() {
        return pressedKeys.get(Keys.LEFT);
    }

    public void onRightPressed() {
        pressedKeys.put(Keys.RIGHT, true);
    }

    public void onRightReleased() {
        pressedKeys.put(Keys.RIGHT, false);
    }

    public boolean isRightPressed() {
        return pressedKeys.get(Keys.RIGHT);
    }

    private void handleInput() {
        Tweety player = world.getPlayer();
        if (player.getState() != Tweety.State.IDLE) return;


        // Handle jump
        if (isLeftPressed()) {
            player.setDirection(Direction.LEFT);
        }
        else if (isRightPressed()) {
            player.setDirection(Direction.RIGHT);
        }
        else {
            return;
        }

        if (player.canJump()) {
            player.onJumpStart();
        }
    }

    private boolean handleBorders(MovingEntity element, float delta) {
        if ((element.getDirection() == Direction.LEFT && element.getX() < -element.getWidth())
                || (element.getDirection() == Direction.RIGHT && element.getX() > world.getWidth())
                || element.getYWithRoadOffset() > world.getHeight() - element.getHeight()
                || element.getYWithRoadOffset() < 0
        ) {
            return element.whenOutOfBorder(world, delta);
        }

        return false;
    }

    private void handleBordersPlayer(Tweety element, float delta) {
        if (element.getX() < 0 ||
                element.getX() > world.getWidth() - element.getWidth() ||
                element.getYWithRoadOffset() > world.getHeight() - element.getHeight() ||
                element.getYWithRoadOffset() < 0
        ) {
            element.whenOutOfBorder(world, delta);
        }
    }

    /**
     * @param collideResult Le résultat de la collision.
     * @param element L'élément avec lequel le Player est entré en collision.
     * @return True si l'action empêche la suite d'action
     */
    private boolean resultCollideWith(CollideResult collideResult, GameElement element) {


        if (collideResult == CollideResult.LETTER) {
            element.setVisible(false);
            String elementLetter = ((Letter) element).getLetter();
            String newPanier = world.getPanier() + elementLetter;
            if (world.getCurrentWordObjective().startsWith(newPanier)) {
                world.setPanier(newPanier);

                if (world.getPanier().equals(world.getCurrentWordObjective())) {
                    //world.setSuccessTime(5);
                    //world.setPanier("");
                    //world.generateNewWord();

                    Client.getInstance().setScreen(new GameOverScreen(world, batch, true));
                }
            }
            else {
                Client.getInstance().setScreen(new GameOverScreen(world, batch, false));
            }

        }

        else if (collideResult == CollideResult.DEATH) {
            if(!world.isCheat())
                Client.getInstance().setScreen(new GameOverScreen(world, batch, false));
        }

        return false;
    }

    private void handleCollisions(float delta) {
        // On test la collision avec le frogger
        Tweety player = world.getPlayer();

        if (testCollision(delta, player)) return;

    }

    private boolean testCollision(float delta, MovingEntity entity) {

        for (Road road : world.getRoads()) {
            for (GameElement element : road.getElements()) {
                if (!element.isVisible()) continue;
                CollideResult collideResult = element.handleCollision(entity, delta);
                boolean b = resultCollideWith(collideResult, element);
                if (b) return true;
            }
        }
        return false;
    }

    /**
     * Ajoute un nouvel élément à la route.
     *
     * @param road La route sur laquelle on veut ajouter l'élément.
     */
    private void tryToAddElements(Road road) {

        float offsetX;
        if (road.getDirection() == Direction.RIGHT) {

            GameElement firstElement = road.getFirstValidElement();
            if (firstElement == null) {

                offsetX = 0;

                GameElement element = world.generateElement();
                element.getPosition().x = offsetX;
                road.addElement(element);
                element.afterInitialisation();
            }
        }
        else if (road.getDirection() == Direction.LEFT) {
            GameElement lastElement = road.getLastValidElement();
            if (lastElement == null) {

                offsetX = world.getWidth();

                GameElement element = world.generateElement();
                element.getPosition().x = offsetX;
                road.addElement(element);
                element.afterInitialisation();
            }
        }
    }

    private boolean update(GameElement element, float delta) {
        element.update(delta);
        if (element instanceof MovingEntity) {
            return handleBorders((MovingEntity) element, delta);
        }
        return false;
    }

    private void updatePlayer(float delta) {
        Tweety player = world.getPlayer();
        player.update(delta);
        handleBordersPlayer(player, delta);
    }

    private void tryToAddLombric(float delta, Road road) {
        // On test si on peut ajouter un lombric
        if (world.getNextLombricTime() < 0) {
            // Si c'est le cas, on en ajoute un et on remet le timer au maximum
            world.setNextLombricTime(world.getLombricConfiguration().getSpawnInterval());

            Lombric lombric = new Lombric(world.getLombricConfiguration());
            lombric.getPosition().x = World.SHARED_RANDOM.nextFloat() * world.getWidth();
            lombric.getPosition().y = world.getHeight() - 1f;
            road.addElement(lombric); // la route ne sert qu'à la boucle d'affichage
            lombric.afterInitialisation();

        }

        // Autrement, on diminue le timer
        world.setNextLombricTime(world.getNextLombricTime() - delta);
    }

    @Override
    public void update(float delta) {

        if (!world.isPause()) {
            handleInput();
            boolean isFirst = true;

            for (Road road : world.getRoads()) {
                // Le lombric n'est à ajouter qu'une fois comme il y a un delai.
                if (isFirst) {
                    tryToAddLombric(delta, road);
                    isFirst = false;
                }

                Set<GameElement> removeElementsList = new HashSet<GameElement>(1, 1);

                for (GameElement element : road.getElements()) {
                    boolean toRemove = update(element, delta);
                    if (toRemove)
                        removeElementsList.add(element);
                }

                road.getElements().removeAll(removeElementsList);

                tryToAddElements(road);
            }

            handleCollisions(delta);

            updatePlayer(delta);

        }
    }

    enum Keys {
        LEFT, RIGHT
    }

}
