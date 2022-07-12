import lombok.Data;

//медведь
@Data
public class Bear extends Animal implements Predator{
    public final double WEIGHT = 500; //Вес одного животного, кг
    public final int MAX_COUNT = 5; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 2)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 80)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 2)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Bear{" +
                "name='" + name +
                '}';
    }
}

