import lombok.Data;

//овца
@Data
public class Sheep extends Animal implements Herbivore{
    public final double WEIGHT = 70; //Вес одного животного, кг
    public final int MAX_COUNT = 140; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 3)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 15)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 2)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Sheep{" +
                "name='" + name +
                '}';
    }
}
