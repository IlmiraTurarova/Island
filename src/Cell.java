import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter @Setter
public class Cell extends Thread{
    public ArrayList<Entity> list; //список сущностей в одной ячейке
    public int x;
    public int y;

    public Cell() {
        this.list = new ArrayList<>();
    }

    @Override
    public void run() {
        super.run();
    }
}
