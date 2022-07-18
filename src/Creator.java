import Entities.Entity;

//Фабрика
public abstract class Creator {
    public abstract Entity createEntity();
}

class BearCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Bear();
    }
}

class BoaCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Boa();
    }
}

class BoarCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Boar();
    }
}

class BullCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Bull();
    }
}

class CaterpillarCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Caterpillar();
    }
}

class DeerCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Deer();
    }
}

class DuckCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Duck();
    }
}

class EagleCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Eagle();
    }
}

class FoxCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Fox();
    }
}

class GoatCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Goat();
    }
}

class GrassCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Grass();
    }
}

class HorseCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Horse();
    }
}

class MouseCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Mouse();
    }
}

class RabbitCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Rabbit();
    }
}

class SheepCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Sheep();
    }
}

class WolfCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Wolf();
    }
}
