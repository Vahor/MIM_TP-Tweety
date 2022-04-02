package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.GameElement;

import java.util.ArrayList;
import java.util.List;

public class Road {

    private final transient List<GameElement> letters = new ArrayList<GameElement>(1);
    private Direction direction;
    private float moveSpeed;
    private float offsetY;

    // Parser
    public Road() {
    }

    public Road(Direction direction, float moveSpeed, float offsetY) {
        this.direction = direction;
        this.moveSpeed = moveSpeed;
        this.offsetY   = offsetY;
    }

    public List<GameElement> getElements() {
        return letters;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addElement(GameElement element) {
        letters.add(element);
        element.setRoad(this);
    }

    public GameElement getFirstValidElement() {
        for (GameElement element : letters) {
            if (element.isValid()) return element;
        }
        return null;
    }

    public GameElement getLastValidElement() {
        for (int i = letters.size() - 1; i >= 0; i--) {
            if (letters.get(i).isValid()) return letters.get(i);
        }
        return null;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    @Override
    public String toString() {
        return "Road{" +
                "direction=" + direction +
                ", moveSpeed=" + moveSpeed +
                ", offsetY=" + offsetY +
                '}';
    }
}
