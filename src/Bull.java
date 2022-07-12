import lombok.Data;

//буйвол
@Data
public class Bull extends Animal implements Herbivore{
    private final double WEIGHT = 700; //Вес одного животного, кг
    public final int MAX_COUNT = 10; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 3)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation()
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 1)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Bull{" +
                "name='" + name +
                '}';
    }
}
