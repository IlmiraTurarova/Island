import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

//игровое поле
public class PlayingField {
    public int weight; //ширина поля
    public int height; //высота поля
    public Cell[][] array;//игровое поле

    public PlayingField(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    //запуск программы
    public void setField() {
        //создаем игровое поле
        array = new Cell[weight][height];
        
        //заполняем двумерный массив Ячейками Cell. то есть в каждую клетку кладем ячейку, которая в дальнейшем будет
        // заполняться сущностями
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = new Cell();;
            }
        }


        setFirstPosition();
        setTact();




        /*
       //выводим посмотреть животных на игровом поле
       for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print("  " + array[i][j]);
            }
            System.out.println();
        }*/


        //System.out.println(array[0][0]);
        





    }

    //берем первоначальное количество животных каждого вида и умножаем на 5
    public int getInitialValue(Entity entity){
        try {
            Class someClass = entity.getClass();
            Field[] fields = someClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("MAX_COUNT")) {

                        Object value = field.get(entity);

                    //максимально возможное количество в клетке
                    if (value instanceof Number) {
                        return ((Number) value).intValue()*5;
                    }

                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //раскидаем по игровому полю все сущности в первый раз
    public void setFirstPosition() {
        //________________________________ВОЛКИ_________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        int num = getInitialValue((Wolf)new WolfCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new WolfCreator();
            Wolf entity = (Wolf) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________УДАВЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Boa)new BoaCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new BoaCreator();
            Boa entity = (Boa) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________ЛИСЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Fox)new FoxCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new FoxCreator();
            Fox entity = (Fox) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________МЕДВЕДИ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Bear)new BearCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new BearCreator();
            Bear entity = (Bear) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________ОРЛЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Eagle)new EagleCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new EagleCreator();
            Eagle entity = (Eagle) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________ЛОШАДИ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Horse)new HorseCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new HorseCreator();
            Horse entity = (Horse) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________ОЛЕНИ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Deer)new DeerCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new DeerCreator();
            Deer entity = (Deer) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________КРОЛИКИ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Rabbit)new RabbitCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new RabbitCreator();
            Rabbit entity = (Rabbit) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________МЫШИ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Mouse)new MouseCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new MouseCreator();
            Mouse entity = (Mouse) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________КОЗЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Goat)new GoatCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new GoatCreator();
            Goat entity = (Goat) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________ОВЦЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Sheep)new SheepCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new SheepCreator();
            Sheep entity = (Sheep) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________КАБАНЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Boar)new BoarCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new BoarCreator();
            Boar entity = (Boar) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________БУЙВОЛЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Bull)new BullCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new BullCreator();
            Bull entity = (Bull) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________УТКИ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Duck)new DuckCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new DuckCreator();
            Duck entity = (Duck) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________ГУСЕНИЦЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Caterpillar)new CaterpillarCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new CaterpillarCreator();
            Caterpillar entity = (Caterpillar) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

        //_______________________________ТРАВЫ________________________________________________________________
        //берем максимальное количество данной сущности на клетке, умноженное на 5
        num = getInitialValue((Grass)new GrassCreator().createEntity());

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Creator creator = new GrassCreator();
            Grass entity = (Grass) creator.createEntity();
            //имя сущности - это его hashCode
            String name = String.valueOf(creator.hashCode());
            entity.setName(name);

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
        }

    }



    //запускаем такт (объекты выполняют все свои жизненные задачи)
    public void setTact() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {


                //____________________________________________________________________________________

                //состояние одной конкретной клетки
                ArrayList<Entity> list = array[i][j].list;
                //перетасовываем элементы в списке
                Collections.shuffle(list);

                //проходимся по всем сущностям в клетке
                for (int k = 0; k < list.size(); k++) {
                    //тип сущности
                    String typeEntity = list.get(k).getClass().getName();
                    Class clazz = list.get(k).getClass();
                    //вытаскиваем из мапы индекс нашей сущности
                    int key = islandRunner.MAP_ENTITIES.get(typeEntity);

                    //проверяем текущий жизненный такт и если он меньше жизненного цикла увеличиваем на единицу
                    //если сущность - это животное
                    if (list.get(k) instanceof Animal) {
                        int currentTact = ((Animal) list.get(k)).currentTact;
                        if (currentTact < Animal.LIFE_TACT_COUNT) {
                            ((Animal) list.get(k)).setCurrentTact(currentTact+1);


                            //вызываем метод покушать
                            //----------------------------покушать------------------------------------------
                            for (int x = 0; x < list.size(); x++) {
                                //сам себя съесть не может
                                if (k!=x) {
                                    //тип сущности предполагаемой жертвы
                                    String typeEntityVictim = list.get(x).getClass().getName();
                                    //вытаскиваем из мапы индекс предполагаемой жертвы
                                    int keyVictim = islandRunner.MAP_ENTITIES.get(typeEntityVictim);

                                    //если наша сущность питается сущностью типа жертвы
                                    if (islandRunner.PROBABILITIES[key][keyVictim]>0) {
                                        //если наша сущность Травоядное
                                        if (list.get(k) instanceof Herbivore) {
                                            ((Herbivore) list.get(k)).eat();
                                        } //если наша сущность Хищник
                                        else if (list.get(k) instanceof Predator) {
                                            ((Predator) list.get(k)).eat();
                                        }
                                    }

                                    //int random = ThreadLocalRandom.current().nextInt(0, list.size());
                                    //System.out.println(random);
                                }
                            }






                            //вызываем метод размножиться
                            //вызываем метод передвижения
                        } else {
                            //устанавливаем флаг умер
                        }
                    }
                    //если сущность - это растение
                    else if (list.get(k) instanceof Plant) {
                        int currentTact = ((Plant) list.get(k)).currentTact;
                        if (currentTact < Plant.LIFE_TACT_COUNT) {
                            ((Plant) list.get(k)).setCurrentTact(currentTact+1);
                        } else {
                            //устанавливаем флаг завял
                        }
                    }






                }
            }
        }
    }
}


