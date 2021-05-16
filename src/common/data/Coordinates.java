package common.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * X-Y координаты
 */
public class Coordinates implements Serializable {
    private Float x; //Значение поля должно быть больше -107
    private Float y;

    public Coordinates(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return X координата
     */
    public Float getX() {
        return x;
    }

    /**
     * @return Y координата
     */
    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X:" + x + " Y:" + y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) {
            Coordinates coordinatesObj = (Coordinates) obj;
            return (x.equals(coordinatesObj.getX())) && (y.equals(coordinatesObj.getY()));
        }
        return false;
    }
}
