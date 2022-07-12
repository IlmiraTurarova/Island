import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Animal implements Entity {
    public static final int LIFE_TACT_COUNT = 5; //столько тактов живет животное
    public int currentTact; //текущий жизненный такт

    //размножаться
    public void breed() {
        System.out.println("Animal is breeding");
    }

    //выбрать направление передвижения
    public void move() {
        System.out.println("Animal is moving");
    }

    //быть съеденным
    public boolean beEaten() {
        return false;
    }

}
