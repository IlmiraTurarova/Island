import lombok.Data;

//мышь
@Data
public class Mouse extends Animal implements Herbivore{
    public final double WEIGHT = 0.05; //Вес одного животного, кг
    public final int MAX_COUNT = 500; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation(maxSpeed = 1)
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 0.01)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 8)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Mouse{" +
                "name='" + name +
                '}';
    }
}
