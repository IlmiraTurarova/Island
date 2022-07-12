import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Plant implements Entity {
    public static final int LIFE_TACT_COUNT = 10; //столько тактов живет растение
    public int currentTact; //текущий жизненный такт
}
