import Entities.Predator;
import lombok.Data;

//волк
@Data
public class Wolf extends Animal implements Predator {
    @Override
    public String toString() {
        return "Wolf{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
