import Entities.Herbivore;
import lombok.Data;

//буйвол
@Data
public class Bull extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Bull{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
