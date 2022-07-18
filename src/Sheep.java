import Entities.Herbivore;
import lombok.Data;

//овца
@Data
public class Sheep extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Sheep{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
