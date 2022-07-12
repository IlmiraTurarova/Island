import lombok.Data;

//травка
@Data
public class Grass extends Plant{
    public final double WEIGHT = 1; //Вес одного растения, кг
    public final int MAX_COUNT = 200; //Максимальное количество растений этого вида на одной клетке
    private String name; //Имя животного

    @Override
    public String toString() {
        return "Grass{" +
                "name='" + name +
                '}';
    }
}
