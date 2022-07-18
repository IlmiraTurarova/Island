import Entities.Entity;
import Entities.Herbivore;
import lombok.Data;

//утка
@Data
public class Duck extends Animal implements Herbivore {
    @Override
    public String toString() {
        return "Duck{" +
                "name='" + this.getName() +
                "'}";
    }

    @Override
    public void eat() {
        int wantEat = this.isFed;
        //имя класса
        String nameClass = this.getClass().getName();
        //идентификатор класса животного
        int key = PlayingField.MAP_ENTITIES.get(nameClass);

        //выбираем рандомно жертву: гусеница(1) или трава(2)
        int rand = (int)(Math.random()*2+1);

        //--------------------------жертва - гусеница-------------------------------------------------
        if (rand==1) {
            for (int i = 0; i < PlayingField.array[this.x][this.y].list.size(); i++) {

                    if (PlayingField.array[this.x][this.y].list.get(i) instanceof Caterpillar) {
                        Caterpillar victim = (Caterpillar) PlayingField.array[this.x][this.y].list.get(i);
                        String nameVictim = victim.getClass().getName();
                        //идентификатор класса жертвы
                        int keyVictim = PlayingField.MAP_ENTITIES.get(nameVictim);

                        if (PlayingField.PROBABILITIES[key][keyVictim] > 0) {

                            int chance = (int) (Math.random() * 100);
                            if (chance > 0 && chance <= PlayingField.PROBABILITIES[key][keyVictim]) {
                                this.isFed++; //животное поело

                                victim.isDelited = true;
                                String returned_value = String.valueOf(PlayingField.animals.remove(victim.getName()));
                                PlayingField.array[this.x][this.y].list.remove(victim);
                                PlayingField.STATISTICS[keyVictim][1]++;
                                PlayingField.STATISTICS[keyVictim][0]--;
                                victim = null;
                                break;
                            }
                        }
                    }


            }
        } //---------------------------------жертва - трава------------------------------------------------
        else {
            int countGrass = PlayingField.countTypeEntities("Grass", this.x, this.y);
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
                            PlayingField.array[this.x][this.y].list.remove(victim);
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
                            PlayingField.array[this.x][this.y].list.remove(victim);
                            PlayingField.STATISTICS[keyGrass][1]++;
                            PlayingField.STATISTICS[keyGrass][0]--;
                            victim = null;
                            break;
                        }

                    }
                }
            } else this.isFed--;
        }

        //если животное не поело - отнимаем сытость
        if (this.isFed==wantEat) {
            this.isFed--;
        }
    }

    @Override
    public void beEaten() {

    }
}
