import lombok.Data;

//кабан
@Data
public class Boar extends Animal implements Herbivore{
    private final double WEIGHT = 400; //Вес одного животного, кг
    public final int MAX_COUNT = 50; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 2)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 50)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 5)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Boar{" +
                "name='" + name +
                '}';
    }
}
