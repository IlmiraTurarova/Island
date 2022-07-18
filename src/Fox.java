import Entities.Predator;
import lombok.Data;

//лиса
@Data
public class Fox extends Animal implements Predator {
    @Override
    public String toString() {
        return "Fox{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
