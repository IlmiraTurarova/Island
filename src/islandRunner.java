import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class islandRunner {
    public static final Map<String, Integer> MAP_ENTITIES = new HashMap<>(); //мапа сущностей с идентификаторами
    //таблица, с какой вероятностью животное съедает пищу
    public static final int[][] PROBABILITIES = {
            {0,0,0,0,0,10,15,60,80,60,70,15,10,40,0,0},  //волк - хищник
            {0,0,15,0,0,0,0,20,40,0,0,0,0,10,0,0},       //удав - хищник
            {0,0,0,0,0,0,0,70,90,0,0,0,0,60,40,0},       //лиса - хищник
            {0,80,0,0,0,40,80,80,90,70,70,50,20,10,0,0}, //медведь - хищник
            {0,0,10,0,0,0,0,90,90,0,0,0,0,80,0,0},       //орел - хищник
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100},         //лошадь
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100},         //олень
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100},         //кролик
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,90,100},        //мышь - полутравоядный
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100},         //коза
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100},         //овца
            {0,0,0,0,0,0,0,0,50,0,0,0,0,0,90,100},       //кабан - полутравоядный
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100},         //буйвол
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,90,100},        //утка - полутравоядный
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100},        //гусеница
    };

    static {
       MAP_ENTITIES.put("Wolf", 0);
       MAP_ENTITIES.put("Boa", 1);
       MAP_ENTITIES.put("Fox", 2);
       MAP_ENTITIES.put("Bear", 3);
       MAP_ENTITIES.put("Eagle", 4);
       MAP_ENTITIES.put("Horse", 5);
       MAP_ENTITIES.put("Deer", 6);
       MAP_ENTITIES.put("Rabbit", 7);
       MAP_ENTITIES.put("Mouse", 8);
       MAP_ENTITIES.put("Goat", 9);
       MAP_ENTITIES.put("Sheep", 10);
       MAP_ENTITIES.put("Boar", 11);
       MAP_ENTITIES.put("Bull", 12);
       MAP_ENTITIES.put("Duck", 13);
       MAP_ENTITIES.put("Caterpillar", 14);
       MAP_ENTITIES.put("Grass", 15);
    }


    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException {
        //создание новой сущности через Фабрику
        /*Creator creator = new BearCreator();
        Entity entity = creator.createEntity();
        System.out.println(entity.getClass().getName());

        Class clazz = entity.getClass();
        if (clazz == Wolf.class) {

        }*/



        PlayingField playingField = new PlayingField(10, 10);
        playingField.setField();




    }
}
