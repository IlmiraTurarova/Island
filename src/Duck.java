import lombok.Data;

//утка
@Data
public class Duck extends Animal implements Herbivore{
    public final double WEIGHT = 1; //Вес одного животного, кг
    public final int MAX_COUNT = 200; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation()
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 0.15)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 10)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Duck{" +
                "name='" + name +
                '}';
    }
}
