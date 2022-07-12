import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SatietyAnnotation {
    double maxSatiety() default 100; //Сколько кг пищи нужно для полного насыщения - сытость
}
