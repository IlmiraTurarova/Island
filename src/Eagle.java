import Entities.Predator;
import lombok.Data;

//орел
@Data
public class Eagle extends Animal implements Predator {
    @Override
    public String toString() {
        return "Eagle{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
