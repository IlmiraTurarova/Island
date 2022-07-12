import lombok.Data;

//гусеница
@Data
public class Caterpillar extends Animal implements Herbivore{
    private final double WEIGHT = 0.01; //Вес одного животного, кг
    public final int MAX_COUNT = 1000; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 0)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 0)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 0)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Caterpillar{" +
                "name='" + name +
                '}';
    }
}
