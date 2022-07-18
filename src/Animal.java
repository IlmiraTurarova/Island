import Entities.Entity;
import Entities.Herbivore;
import Entities.Predator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter @Setter @EqualsAndHashCode
public abstract class Animal implements Entity, Runnable {
    public static final int LIFE_TACT_COUNT = 5; //столько тактов живет животное

    private String name; //Имя животного
    private int speed; //Скорость перемещения, не более чем, клеток за ход
    private double satiety; //Сколько кг пищи съело животное
    private int babyCount; //Количество детенышей у животного за один помет

    public int x; //координата x
    public int y; //координата y


    public int currentTact = 5; //текущий жизненный такт
    public int isFed = 3; //животное может жить без еды максимум 3 такта
    public boolean isBreed = false; //животное в процессе размножения
    public boolean isDied = false; //флаг - животное умерло
    public boolean isDelited = false; //объект на удалении
    private Animal animal;

    public Animal() {
        this.name = String.valueOf(UUID.randomUUID()); //имя - уникальный идентификатор UUID
    }


    /* @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                ", satiety=" + satiety +
                ", babyCount=" + babyCount +
                ", currentTact=" + currentTact +
                ", isDied=" + isDied +
                '}';
    }*/

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                '}';
    }

    //--------------------------------------------КУШАТЬ--------------------------------------------------
    public void eat() {
        //------------------------------если травоядное---------------------------------------------------
        if (this instanceof Herbivore) {
            int countGrass = PlayingField.countTypeEntities("Grass", this.x, this.y);
            //имя класса
            String nameClass = this.getClass().getName();
            //идентификатор класса животного
            int key = PlayingField.MAP_ENTITIES.get(nameClass);
            //идентификатор класса растения
            int keyGrass = PlayingField.MAP_ENTITIES.get("Grass");

            //количество еды для насыщения
            int countSatiety = (int) (PlayingField.CHARACTERISTICS[key][3]);
            //рандомно указываем сколько животное может съесть
            //int random = (int) (Math.random() * countSatiety + 1);
            double random = countSatiety;
            if (random>0 && random<1) {
                random=1;
            }

            //если травы достаточно
            if (countGrass >= random) {
                this.isFed++; //животное поело
                for (int i = 0; i < random; i++) {
                    for (int j = 0; j < PlayingField.array[this.x][this.y].list.size(); j++) {
                        //трава - жертва
                        Entity victim = PlayingField.array[this.x][this.y].list.get(j);
                        if (victim instanceof Grass) {
                            ((Grass) victim).isDelited = true;
                            PlayingField.array[this.x][this.y].list.remove(PlayingField.array[this.x][this.y].list.get(j));
                            PlayingField.STATISTICS[keyGrass][1]++;
                            PlayingField.STATISTICS[keyGrass][0]--;
                            victim = null;
                            break;
                        }
                    }
                }
            } else if ((countGrass < random) && (countGrass > 0)) {
                this.isFed++; //животное поело
                for (int i = 0; i < countGrass; i++) {
                    for (int j = 0; j < PlayingField.array[this.x][this.y].list.size(); j++) {
                        //трава - жертва
                        Entity victim = PlayingField.array[this.x][this.y].list.get(j);
                        if (victim instanceof Grass) {
                            //растение удаляем
                            ((Grass) victim).isDelited = true;
                            PlayingField.array[this.x][this.y].list.remove(PlayingField.array[this.x][this.y].list.get(j));
                            PlayingField.STATISTICS[keyGrass][1]++;
                            PlayingField.STATISTICS[keyGrass][0]--;
                            victim = null;
                            break;
                        }

                    }
                }
            } else this.isFed--;


        } //------------------------------если хищник---------------------------------------------------
        else if (this instanceof Predator) {
            int wantEat = this.isFed;
            //имя класса
            String nameClass = this.getClass().getName();
            //идентификатор класса животного
            int key = PlayingField.MAP_ENTITIES.get(nameClass);
            for (int i = 0; i < PlayingField.array[this.x][this.y].list.size(); i++) {

                //если наше животное не совпадает с самим собой
                if (PlayingField.array[this.x][this.y].list.get(i) instanceof Animal) {
                    //жертва
                    Animal victim = (Animal) PlayingField.array[this.x][this.y].list.get(i);
                    String nameVictim = victim.getClass().getName();
                    //идентификатор класса жертвы
                    int keyVictim = PlayingField.MAP_ENTITIES.get(nameVictim);
                    if (PlayingField.PROBABILITIES[key][keyVictim] > 0) {
                        int chance = (int) (Math.random() * 100);
                        if (chance > 0 && chance <= PlayingField.PROBABILITIES[key][keyVictim]) {
                            this.isFed++; //животное поело


                            //жертву убираем
                            victim.isDelited = true;
                            String returned_value = String.valueOf(PlayingField.animals.remove(victim.getName()));
                            PlayingField.array[this.x][this.y].list.remove(PlayingField.array[this.x][this.y].list.get(i));
                            PlayingField.STATISTICS[keyVictim][1]++;
                            PlayingField.STATISTICS[keyVictim][0]--;
                            victim = null;
                            break;
                        }

                    }
                }
            }

            //если животное не поело - отнимаем сытость
            if (this.isFed==wantEat) {
                this.isFed--;
            }
        }
    }

    //--------------------------------------------РАЗМНОЖАТЬСЯ----------------------------------------------
    public void breed() {
        if (!this.isBreed) {
            //имя класса
            String nameClass = this.getClass().getName();
            //идентификатор класса животного
            int key = PlayingField.MAP_ENTITIES.get(nameClass);
            //сколько сейчас животных данного типа в клетке
            int countAnimal = PlayingField.countTypeEntities(nameClass, this.x, this.y);
            //разница между максимально возможным количеством и текущим значением
            int difference = (int) (PlayingField.CHARACTERISTICS[key][1]) - countAnimal;

            //ищем пару в текущей локации
            for (int i = 0; i < PlayingField.array[this.x][this.y].list.size(); i++) {
                //если наше животное не совпадает с самим собой
                //if (!this.equals(PlayingField.array[this.x][this.y].list.get(i))) {
                    if (PlayingField.array[this.x][this.y].list.get(i) instanceof Animal) {
                        Animal pair = (Animal) PlayingField.array[this.x][this.y].list.get(i);
                        String namePair = pair.getClass().getName();

                        //если тип животного в локации совпадает (и оно не размножалось в текущем такте) с типом
                        // нашего животного
                        if ((nameClass.equals(namePair)) && (!this.getName().equals(pair.getName())) && (pair.isBreed==false)) {
                            //животные размножаются
                            int chance = (int) (Math.random() * (PlayingField.CHARACTERISTICS[key][4]+1));
                            //если выпало малышей животных по рандому >0 и <=остатку до максимального количества на клетке
                            if ((chance>0) && (chance<=difference)) {
                                //-------------------------если волки------------------------------------
                                if (this instanceof Wolf) {
                                    Creator creatorWolf = new WolfCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Wolf entity = (Wolf) creatorWolf.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если удавы------------------------------------
                                else if (this instanceof Boa) {
                                    Creator creatorBoa = new BoaCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Boa entity = (Boa) creatorBoa.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если лисы------------------------------------
                                else if (this instanceof Fox) {
                                    Creator creatorFox = new FoxCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Fox entity = (Fox) creatorFox.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если медведи------------------------------------
                                else if (this instanceof Bear) {
                                    Creator creatorBear = new BearCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Bear entity = (Bear) creatorBear.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если орлы------------------------------------
                                else if (this instanceof Eagle) {
                                    Creator creatorEagle = new EagleCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Eagle entity = (Eagle) creatorEagle.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если лошади------------------------------------
                                else if (this instanceof Horse) {
                                    Creator creatorHorse = new HorseCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Horse entity = (Horse) creatorHorse.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если олени------------------------------------
                                else if (this instanceof Deer) {
                                    Creator creatorDeer = new DeerCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Deer entity = (Deer) creatorDeer.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если кролики------------------------------------
                                else if (this instanceof Rabbit) {
                                    Creator creatorRabbit = new RabbitCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Rabbit entity = (Rabbit) creatorRabbit.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если мыши------------------------------------
                                else if (this instanceof Mouse) {
                                    Creator creatorMouse = new MouseCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Mouse entity = (Mouse) creatorMouse.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если козы------------------------------------
                                else if (this instanceof Goat) {
                                    Creator creatorGoat = new GoatCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Goat entity = (Goat) creatorGoat.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если овцы------------------------------------
                                else if (this instanceof Sheep) {
                                    Creator creatorSheep = new SheepCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Sheep entity = (Sheep) creatorSheep.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если кабаны------------------------------------
                                else if (this instanceof Boar) {
                                    Creator creatorBoar = new BoarCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Boar entity = (Boar) creatorBoar.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если буйволы------------------------------------
                                else if (this instanceof Bull) {
                                    Creator creatorBull = new BullCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Bull entity = (Bull) creatorBull.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если утки------------------------------------
                                else if (this instanceof Duck) {
                                    Creator creatorDuck = new DuckCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Duck entity = (Duck) creatorDuck.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }
                                //-------------------------если гусеницы------------------------------------
                                else if (this instanceof Caterpillar) {
                                    Creator creatorCaterpillar = new CaterpillarCreator();

                                    for (int j = 0; j < chance; j++) {
                                        //создаем сущности
                                        Caterpillar entity = (Caterpillar) creatorCaterpillar.createEntity();

                                        PlayingField.array[this.x][this.y].list.add(entity);
                                        entity.x = this.x;
                                        entity.y = this.y;
                                        PlayingField.animals.put(entity.getName(), entity);
                                        //добавляем в Характеристики.количество родившихся
                                        PlayingField.STATISTICS[key][3]++;
                                        PlayingField.STATISTICS[key][0]++;
                                    }
                                }

                                //в текущем такте животные размножились
                                this.isBreed = true;
                                pair.isBreed = true;
                            }
                           break;
                        }
                    }
                //}

            }
        }

    }

    //----------------------------------------ПЕРЕДВИГАТЬСЯ-------------------------------------------------
    public void move() {
        //рандомом выбираем направление:
        //1 - вверх
        //2 - вправо
        //3 - вниз
        //4 - влево
        int chance = (int) (Math.random()*4 + 1);

        //имя класса
        String nameClass = this.getClass().getName();
        //идентификатор класса животного
        int key = PlayingField.MAP_ENTITIES.get(nameClass);
        int firstX = this.x;
        int firstY = this.y;
        //разница между максимально возможным количеством и текущим значением
        int maxStep = (int) (PlayingField.CHARACTERISTICS[key][2]);
        int step = (int) (Math.random()*maxStep + 1);

        //если доходим до края отталкиваемся и двигаемся в обратном направлении
        //если вверх или вниз работаем с координатой X
        if (chance%2!=0) {
            int coordX = 0;
            if (chance==1) {
                coordX = (int) Math.abs(this.x - step);
            } else if (chance==3) {
                coordX = this.x + step;
            }

                if (coordX>=PlayingField.weight) {
                    coordX = coordX - (PlayingField.weight-1);
                    coordX = (PlayingField.weight-1) - coordX;
                }
           this.x = coordX;
        } //если вправо или влево работаем с координатой Y
        else {
            int coordY = 0;
            if (chance==4) {
                coordY = (int) Math.abs(this.y - step);
            } else if (chance==2) {
                coordY = this.y + step;
            }

            if (coordY>=PlayingField.height) {
                coordY = coordY - (PlayingField.height-1);
                coordY = (PlayingField.height-1) - coordY;
            }
            this.y = coordY;
        }

        //животное идет в новую локацию
        PlayingField.array[this.x][this.y].list.add(this);
        //убираем животного из текущей локации
        PlayingField.array[firstX][firstY].list.remove(this);

    }

    //метод run переопределяем у каждого животного
    @Override
    public void run() {
        isBreed = false;
        int coordX = this.x; //координата X нашего животного
        int coordY = this.y; //координата Y нашего животного

        //---------------------------если клеточка, в которой находится наше животное не залочена---------------
       try
        {
            try {
                if (PlayingField.array[coordX][coordY].getLock().tryLock(80, TimeUnit.MILLISECONDS)) {
                    eat();
                    breed();
                    move();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    currentTact--; //отнимаем 1 такт из жизни

                    //если животное больше 3 тактов голодное или прожило больше 5 тактов - оно умирает
                    if (isFed <= 0 || currentTact <= 0) {
                        //имя класса
                        String nameClass = this.getClass().getName();
                        //идентификатор класса животного
                        int key = PlayingField.MAP_ENTITIES.get(nameClass);

                        isDelited = true;
                        isDied = true;
                        PlayingField.array[this.x][this.y].list.remove(this);
                        String returned_value = String.valueOf(PlayingField.animals.remove(this.getName()));

                        PlayingField.STATISTICS[key][2]++;
                        PlayingField.STATISTICS[key][0]--;

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally{
    //------------------------------------------освобождаем лок-------------------------------------------------
    PlayingField.array[coordX][coordY].getLock().unlock();
}



    }

    public abstract void beEaten();

}
