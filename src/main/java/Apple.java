public class Apple {

    private int y;
    private int x;

    public Apple(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apple)) return false;

        Apple apple = (Apple) o;

        if (getY() != apple.getY()) return false;
        return getX() == apple.getX();
    }

    @Override
    public int hashCode() {
        int result = getY();
        result = 31 * result + getX();
        return result;
    }
}
