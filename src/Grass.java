import lombok.Data;

import java.io.IOException;

//травка
public class Grass extends Plant{
    @Override
    public String toString() {
        return "Grass{" +
                "name='" + this.getName() +
                "'}";
    }
}