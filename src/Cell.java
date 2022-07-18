import Entities.Entity;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Getter @Setter
public class Cell{
    public ArrayList<Entity> list; //список сущностей в одной ячейке
    public int x; //координата x
    public int y; //координата y
    private final Lock lock;

    public Cell() {
        this.list = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

}
