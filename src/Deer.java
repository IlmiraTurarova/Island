import Entities.Herbivore;
import lombok.Data;

//олень
@Data
public class Deer extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Deer{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
