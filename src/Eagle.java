import lombok.Data;

//орел
@Data
public class Eagle extends Animal implements Predator{
    public final double WEIGHT = 6; //Вес одного животного, кг
    public final int MAX_COUNT = 20; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 3)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 1)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 2)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Eagle{" +
                "name='" + name +
                '}';
    }
}
