# Configuration
Dans le constructeur de la classe `GameScreen`

## Pour lancer et créer la configuration
```java
world = new World();
world.demoWorld();
//world = worldDao.get("config.json");
worldDao.save("config.json", world);
```

## Pour lancer depuis la configuration
```java
//world = new World();
//world.demoWorld();
world = worldDao.get("config.json");
//worldDao.save("config.json", world);
```

Le fichier de configuration se situe dans le répertoire `core/assets`