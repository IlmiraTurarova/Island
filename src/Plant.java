import Entities.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Getter @Setter @EqualsAndHashCode
public abstract class Plant implements Entity {
    public static final int LIFE_TACT_COUNT = 10; //столько тактов живет растение
    private String name; //Имя растения

    public int x; //координата x
    public int y; //координата y

    public int currentTact = 10; //текущий жизненный такт
    public boolean isWilted = false; //флаг - растение завяло
    public boolean isDelited = false; //объект на удалении

    public Plant() {
        this.name = String.valueOf(UUID.randomUUID()); //имя - уникальный идентификатор UUID
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                '}';
    }


}
