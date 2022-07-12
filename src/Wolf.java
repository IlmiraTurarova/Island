import lombok.Data;

//волк
@Data
public class Wolf extends Animal implements Predator {
    public final double WEIGHT = 50; //Вес одного животного, кг
    public final int MAX_COUNT = 30; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 3)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 8)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 3)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Wolf{" +
                "name='" + name +
                '}';
    }
}
