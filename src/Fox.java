import lombok.Data;

//лиса
@Data
public class Fox extends Animal implements Predator{
    public final double WEIGHT = 8; //Вес одного животного, кг
    public final int MAX_COUNT = 30; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 2)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 2)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 6)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Fox{" +
                "name='" + name +
                '}';
    }
}
