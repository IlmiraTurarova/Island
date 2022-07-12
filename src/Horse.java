import lombok.Data;

//лошадь
@Data
public class Horse extends Animal implements Herbivore{
    public final double WEIGHT = 400; //Вес одного животного, кг
    public final int MAX_COUNT = 20; //Максимальное количество животных этого вида на одной клетке
    private String name; //Имя животного

    @SpeedAnnotation()
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    @SatietyAnnotation(maxSatiety = 60)
    private double satiety; //Сколько кг пищи съело животное
    @BabyAnnotation(maxCount = 2)
    private int babyCount; //Количество детенышей у животного за один помет

    @Override
    public String toString() {
        return "Horse{" +
                "name='" + name +
                '}';
    }
}
