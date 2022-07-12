import lombok.Data;

//кролик
@Data
public class Rabbit extends Animal implements Herbivore{
    public final double WEIGHT = 2; //Вес одного животного, кг
    public final int MAX_COUNT = 150; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 2)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 0.45)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 5)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Rabbit{" +
                "name='" + name +
                '}';
    }
}
