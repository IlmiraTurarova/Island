public class DuckCreator extends Creator {

    @Override
    public Entity createEntity() {
        return new Duck();
    }
}
