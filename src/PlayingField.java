import Entities.Entity;
import Entities.Herbivore;
import Entities.Predator;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//игровое поле
public class PlayingField {
    public static int weight;     //ширина поля
    public static int height;     //высота поля
    public static Cell[][] array; //игровое поле
    public int tact;       //текущий такт

    public volatile static Map<String, Animal> animals = new HashMap<>(); //для потоков животных будем брать из этой мапы

    //мапа сущностей с идентификаторами
    public static final Map<String, Integer> MAP_ENTITIES = new HashMap<>() {
        {
            this.put("Wolf", 0);
            this.put("Boa", 1);
            this.put("Fox", 2);
            this.put("Bear", 3);
            this.put("Eagle", 4);
            this.put("Horse", 5);
            this.put("Deer", 6);
            this.put("Rabbit", 7);
            this.put("Mouse", 8);
            this.put("Goat", 9);
            this.put("Sheep", 10);
            this.put("Boar", 11);
            this.put("Bull", 12);
            this.put("Duck", 13);
            this.put("Caterpillar", 14);
            this.put("Grass", 15);
        }
    };

    //таблица параметров сущностей (0 столбец - вес одного животного;
    // 1 столбец - макс число животных одного вида на одной клетке;
    //2 столбец - скорость перемещения, не более чем, клеток за ход;
    // 3 столбец -  сколько кг пищи нужно животному для полного насыщения;
    //4 столбец - максимальное количество детенышей у животного за один помет
    public static final double[][] CHARACTERISTICS = {
            {50, 30, 3, 8, 3},         //волк
            {15, 30, 1, 3, 2},         //удав
            {8, 30, 2, 2, 6},          //лиса
            {500, 5, 2, 80, 2},        //медведь
            {6, 20, 3, 1, 2},          //орел
            {400, 20, 4, 60, 2},       //лошадь
            {300, 20, 4, 50, 2},       //олень
            {2, 150, 2, 0.45, 5},      //кролик
            {0.05, 500, 1, 0.01, 8},   //мышь
            {60, 140, 3, 10, 2},       //коза
            {70, 140, 3, 15, 2},       //овца
            {400, 50, 2, 50, 5},       //кабан
            {700, 10, 3, 100, 1},      //буйвол
            {1, 200, 4, 0.15, 10},      //утка
            {0.01, 1000, 0, 0.001, 20},     //гусеница
            {1, 200, 0, 0, 0}          //трава
    };

    //таблица статистики (0 столбец - количество животных данного вида на поле за такт;
    //1 столбец - количество съеденных животных и растений за такт;
    //2 столбец - количество умерших своей смертью животных и завядших растений за такт)
    //3 столбец - количество родившихся животных
    public volatile static int[][] STATISTICS = {
            {0, 0, 0, 0}, //волк
            {0, 0, 0, 0}, //удав
            {0, 0, 0, 0}, //лиса
            {0, 0, 0, 0}, //медведь
            {0, 0, 0, 0}, //орел
            {0, 0, 0, 0}, //лошадь
            {0, 0, 0, 0}, //олень
            {0, 0, 0, 0}, //кролик
            {0, 0, 0, 0}, //мышь
            {0, 0, 0, 0}, //коза
            {0, 0, 0, 0}, //овца
            {0, 0, 0, 0}, //кабан
            {0, 0, 0, 0}, //буйвол
            {0, 0, 0, 0}, //утка
            {0, 0, 0, 0}, //гусеница
            {0, 0, 0, 0}, //трава
    };

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
        showStatistic();

        //пока на поле есть животные программа работает
        while (!animals.isEmpty()) {
            setTact();
            try {
                Thread.sleep(10000);
                showStatistic();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    //берем первоначальное количество животных каждого вида
    public int getInitialValue(Entity entity){
            Class someClass = entity.getClass();
            String nameClass = someClass.getName();
            Integer value=0;
            int result;

            //по названию класса вытаскиваем номер типа сущности
            for(var pair: MAP_ENTITIES.entrySet())
            {

                if (pair.getKey().equals(nameClass)) {
                    value = pair.getValue();
                    break;
                }
            }

            //из таблицы на пересечении сущность-максимальное количество животных на одной клетке вытаскиваем информацию
            result = (int) (CHARACTERISTICS[value][1]);

        return result;
    }

    //раскидаем по игровому полю все сущности в первый раз
    public void setFirstPosition() {
        //_______________________________ТРАВЫ________________________________________________________________
        int num = getInitialValue((Grass)new GrassCreator().createEntity())*(weight-1)*(height-1);
        Creator creatorGrass = new GrassCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Grass entity = (Grass) creatorGrass.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            STATISTICS[15][0]++;         //добавляем в Характеристики.количество травы на поле - траву

        }

        //________________________________ВОЛКИ_________________________________________________________
        num = getInitialValue((Wolf)new WolfCreator().createEntity())*(weight/5);
        Creator creatorWolf = new WolfCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Wolf entity = (Wolf) creatorWolf.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[0][0]++; //добавляем в Характеристики.количество волков на поле - волка
        }

        //_______________________________УДАВЫ________________________________________________________________
        num = getInitialValue((Boa)new BoaCreator().createEntity())*(weight/5);
        Creator creatorBoa = new BoaCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Boa entity = (Boa) creatorBoa.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[1][0]++; //добавляем в Характеристики.количество удавов на поле - удава
        }

        //_______________________________ЛИСЫ________________________________________________________________
        num = getInitialValue((Fox)new FoxCreator().createEntity())*(weight/5);
        Creator creatorFox = new FoxCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Fox entity = (Fox) creatorFox.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[2][0]++; //добавляем в Характеристики.количество лис на поле - лиса
        }

        //_______________________________МЕДВЕДИ________________________________________________________________
        num = getInitialValue((Bear)new BearCreator().createEntity())*(weight/5);
        Creator creatorBear = new BearCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Bear entity = (Bear) creatorBear.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[3][0]++; //добавляем в Характеристики.количество медведей на поле - медведя
        }

        //_______________________________ОРЛЫ________________________________________________________________
        num = getInitialValue((Eagle)new EagleCreator().createEntity())*(weight/5);
        Creator creatorEagle = new EagleCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Eagle entity = (Eagle) creatorEagle.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[4][0]++; //добавляем в Характеристики.количество орлов на поле - орла
        }

        //_______________________________ЛОШАДИ________________________________________________________________
        num = getInitialValue((Horse)new HorseCreator().createEntity())*(weight/5);
        Creator creatorHorse = new HorseCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Horse entity = (Horse) creatorHorse.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[5][0]++; //добавляем в Характеристики.количество лошадей на поле - лошадь
        }

        //_______________________________ОЛЕНИ________________________________________________________________
        num = getInitialValue((Deer)new DeerCreator().createEntity())*(weight/5);
        Creator creatorDeer = new DeerCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Deer entity = (Deer) creatorDeer.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[6][0]++; //добавляем в Характеристики.количество оленей на поле - оленя
        }

        //_______________________________КРОЛИКИ________________________________________________________________
        num = getInitialValue((Rabbit)new RabbitCreator().createEntity())*(weight/5);
        Creator creatorRabbit = new RabbitCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Rabbit entity = (Rabbit) creatorRabbit.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[7][0]++; //добавляем в Характеристики.количество кроликов на поле - кролика
        }

        //_______________________________МЫШИ________________________________________________________________
        num = getInitialValue((Mouse)new MouseCreator().createEntity())*(weight/5);
        Creator creatorMouse = new MouseCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Mouse entity = (Mouse) creatorMouse.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[8][0]++; //добавляем в Характеристики.количество мышей на поле - мышь
        }

        //_______________________________КОЗЫ________________________________________________________________
        num = getInitialValue((Goat)new GoatCreator().createEntity())*(weight/5);
        Creator creatorGoat = new GoatCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Goat entity = (Goat) creatorGoat.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[9][0]++; //добавляем в Характеристики.количество коз на поле - козу
        }

        //_______________________________ОВЦЫ________________________________________________________________
        num = getInitialValue((Sheep)new SheepCreator().createEntity())*(weight/5);
        Creator creatorSheep = new SheepCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Sheep entity = (Sheep) creatorSheep.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[10][0]++; //добавляем в Характеристики.количество овец на поле - овцу
        }

        //_______________________________КАБАНЫ________________________________________________________________
        num = getInitialValue((Boar)new BoarCreator().createEntity())*(weight/5);
        Creator creatorBoar = new BoarCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Boar entity = (Boar) creatorBoar.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[11][0]++; //добавляем в Характеристики.количество кабанов на поле - кабана
        }

        //_______________________________БУЙВОЛЫ________________________________________________________________
        num = getInitialValue((Bull)new BullCreator().createEntity())*(weight/5);
        Creator creatorBull = new BullCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Bull entity = (Bull) creatorBull.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[12][0]++; //добавляем в Характеристики.количество буйволов на поле - буйвола
        }

        //_______________________________УТКИ________________________________________________________________
        num = getInitialValue((Duck)new DuckCreator().createEntity())*(weight/5);
        Creator creatorDuck = new DuckCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Duck entity = (Duck) creatorDuck.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[13][0]++; //добавляем в Характеристики.количество уток на поле - утку
        }

        //_______________________________ГУСЕНИЦЫ________________________________________________________________
        num = getInitialValue((Caterpillar)new CaterpillarCreator().createEntity())*(weight/5);
        Creator creatorCaterpillar = new CaterpillarCreator();

        for (int i = 0; i < num; i++) {
            //создаем сущности
            Caterpillar entity = (Caterpillar) creatorCaterpillar.createEntity();

            //рандомно выбираем ячейку
            int x = (int) (Math.random()*weight);
            int y = (int) (Math.random()*height);
            array[x][y].list.add(entity);
            entity.x = x;
            entity.y = y;
            animals.put(entity.getName(), entity);
            STATISTICS[14][0]++; //добавляем в Характеристики.количество гусениц на поле - гусеницу
        }

    }

    //запускаем такт (объекты выполняют все свои жизненные задачи)
    public void setTact() {
        tact++;

        //----------------------------------ScheduleThreadPool-------------------------------------------------
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10000);
            for (var pair : animals.entrySet()) {
                scheduledExecutorService.schedule(pair.getValue(), 5, TimeUnit.SECONDS);
            }
            scheduledExecutorService.shutdown();

        growGrass();


        //идентификатор класса растения
        int keyGrass = PlayingField.MAP_ENTITIES.get("Grass");

        //у травы отнимаем жизненный такт, либо удаляем
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                for (int k = 0; k < array[i][j].list.size(); k++) {
                   if (array[i][j].list.get(k) instanceof Grass) {
                       //если трава прожила больше 10 тактов - удаляем
                       if (((Grass) array[i][j].list.get(k)).currentTact<=0) {
                           ((Grass) array[i][j].list.get(k)).isWilted = true;
                           PlayingField.array[i][j].list.remove(PlayingField.array[i][j].list.get(k));
                           PlayingField.STATISTICS[keyGrass][2]++;
                           PlayingField.STATISTICS[keyGrass][0]--;
                           k--;
                       } else {
                           ((Grass) array[i][j].list.get(k)).currentTact--; //отнимаем 1 такт
                       }
                   }
                }
            }
        }
    }

   //подсчет количества сущностей одного типа в клетке
   public static int countTypeEntities(String clazz, int x, int y) {
       int result = switch (clazz) {
           case "Grass" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Grass) {
                       value++;
                   }
               }
               yield value;
           }
           case "Wolf" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Wolf) {
                       value++;
                   }
               }
               yield value;
           }
           case "Boa" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Boa) {
                       value++;
                   }
               }
               yield value;
           }
           case "Fox" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Fox) {
                       value++;
                   }
               }
               yield value;
           }
           case "Bear" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Bear) {
                       value++;
                   }
               }
               yield value;
           }
           case "Eagle" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Eagle) {
                       value++;
                   }
               }
               yield value;
           }
           case "Horse" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Horse) {
                       value++;
                   }
               }
               yield value;
           }
           case "Deer" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Deer) {
                       value++;
                   }
               }
               yield value;
           }
           case "Rabbit" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Rabbit) {
                       value++;
                   }
               }
               yield value;
           }
           case "Mouse" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Mouse) {
                       value++;
                   }
               }
               yield value;
           }
           case "Goat" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Goat) {
                       value++;
                   }
               }
               yield value;
           }
           case "Sheep" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Sheep) {
                       value++;
                   }
               }
               yield value;
           }
           case "Boar" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Boar) {
                       value++;
                   }
               }
               yield value;
           }
           case "Bull" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Bull) {
                       value++;
                   }
               }
               yield value;
           }
           case "Duck" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Duck) {
                       value++;
                   }
               }
               yield value;
           }
           case "Caterpillar" -> {
               int value = 0;
               for (int i = 0; i < array[x][y].list.size(); i++) {
                   if (array[x][y].list.get(i) instanceof Caterpillar) {
                       value++;
                   }
               }
               yield value;
           }
           default -> -1;
       };

        return result;
    }

    //------------------------------------------расти трава-------------------------------------------------
    public void growGrass() {
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                int countGrass = countTypeEntities("Grass", x, y); //сколько сейчас травы в клетке
                int key = MAP_ENTITIES.get("Grass");
                //разница между максимально возможным количеством и текущим значением
                int difference = (int) (CHARACTERISTICS[key][1]) - countGrass;
                //int random = (int) (Math.random()*difference+ 1);

                Creator creatorGrass = new GrassCreator();

                for (int k = 0; k < difference; k++) {
                    //создаем сущности
                    Grass entity = (Grass) creatorGrass.createEntity();

                    array[x][y].list.add(entity);
                    entity.x = x;
                    entity.y = y;
                    STATISTICS[15][0]++;         //добавляем в Характеристики.количество травы на поле - траву

                }
            }
        }

    }

    public void showStatistic() {
        int countEntities=0, countEaten=0, countDied=0, countBorn=0, countPredators=0;
        for (int i = 0; i < PlayingField.STATISTICS.length-1; i++) {
            countEntities += PlayingField.STATISTICS[i][0];
            countEaten += PlayingField.STATISTICS[i][1];
            countDied += PlayingField.STATISTICS[i][2];
            countBorn += PlayingField.STATISTICS[i][3];
        }

        for (int i = 0; i < 5; i++) {
            countPredators += PlayingField.STATISTICS[i][0];
        }

        System.out.println("Количество животных на острове = " + countEntities);
        System.out.println("Количество съеденных животных на острове = " + countEaten);
        System.out.println("Количество умерших своей смертью животных на острове = " + countDied);
        System.out.println("Количество родившихся животных на острове = " + countBorn);
        System.out.println("Количество растений на острове = "
                + PlayingField.STATISTICS[PlayingField.STATISTICS.length-1][0]);
        System.out.println("Количество завядших растений = "
                + PlayingField.STATISTICS[PlayingField.STATISTICS.length-1][2]);
        System.out.println();
        System.out.println();
    }
}


