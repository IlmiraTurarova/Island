import Entities.Herbivore;
import lombok.Data;

//кролик
@Data
public class Rabbit extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Rabbit{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}
