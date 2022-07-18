import Entities.Herbivore;
import lombok.Data;

//гусеница
@Data
public class Caterpillar extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Caterpillar{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }

    @Override
    public void move() {
        //гусеницы не передвигаются по локациям
    }
}
