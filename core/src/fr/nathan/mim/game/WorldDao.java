package fr.nathan.mim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import fr.nathan.mim.game.model.type.Letter;
import fr.nathan.mim.game.model.type.Tweety;
import fr.nathan.mim.game.model.type.World;

import java.util.ArrayList;

public class WorldDao {

    private final Json parser;

    public WorldDao() {
        parser = new Json();

        parser.setOutputType(JsonWriter.OutputType.json);

        parser.addClassTag("Player", Tweety.class);
        parser.addClassTag("Letter", Letter.class);

        parser.addClassTag("ArrayList", ArrayList.class);
    }

    public World get(String path) {

        FileHandle fileHandle = Gdx.files.local(path);
        String rawJson = fileHandle.readString();

        World world = parser.fromJson(World.class, rawJson);
        world.afterInitialisation();
        world.getPlayer().afterInitialisation();

        return world;
    }

    public void save(String path, World world) {

        FileHandle fileHandle = Gdx.files.local(path);
        fileHandle.writeString(parser.prettyPrint(world), false); // todo toJson à la place de prettyPrint
    }
}
