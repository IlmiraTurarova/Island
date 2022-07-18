import Entities.Predator;
import lombok.Data;

import java.util.UUID;

//медведь
@Data
public class Bear extends Animal implements Predator {
    @Override
    public String toString() {
        return "Bear{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void beEaten() {

    }
}

