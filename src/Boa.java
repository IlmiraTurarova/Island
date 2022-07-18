import Entities.Predator;
import lombok.Data;

//удав
@Data
public class Boa extends Animal implements Predator {
    @Override
    public String toString() {
        return "Boa{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
