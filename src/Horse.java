import Entities.Herbivore;
import lombok.Data;

//лошадь
@Data
public class Horse extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Horse{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
