import Entities.Herbivore;
import lombok.Data;

//коза
@Data
public class Goat extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Goat{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
