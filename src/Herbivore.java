//травоядное
public interface Herbivore {
    //кушать
    default void eat() {
        System.out.println("Herbivore is eating");
    }
}
