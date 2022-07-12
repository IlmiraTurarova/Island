import lombok.Data;

//удав
@Data
public class Boa extends Animal implements Predator{
    public final double WEIGHT = 15; //Вес одного животного, кг
    public final int MAX_COUNT = 30; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 1)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 3)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 2)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Boa{" +
                "name='" + name +
                '}';
    }
}
