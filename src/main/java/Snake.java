import java.util.ArrayList;
import java.util.List;

public class Snake {

    String direction = "RIGHT";

    public Snake(Coord coord) {
        tail.add(coord);
    }


    List<Coord> tail = new ArrayList<>();

    public void generateTail() {
        Coord coord = tail.get(0);
        for (int i = 0; i < 3; i++) { //3 bo tak
            tail.add(new Coord(coord.getX() - (i + 1), coord.getY()));
        }
    }

    public List<Coord> getTail() {
        return tail;
    }

    public void move(List<Apple> apples, Board board) {

        final Coord head = transformHead(tail.get(0));
        try {
            if (head.getX() <= 0 || head.getX() >= board.getBoardLength() || head.getY() <= 0 || head.getY() >= board.getBoardHeight()) {
                throw new IllegalArgumentException("Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        tail.add(0, head);

        try {
            if (isTail(head.getX(), head.getY())) {
                throw new IllegalArgumentException("Ssssss");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (!isApple(apples, head.getX(), head.getY())) {
            tail.remove(tail.size() - 1);
        } else {
            Apple apple = apples.stream()
                    .filter(a -> a.getX() == head.getX() && a.getY() == head.getY())
                    .findAny()
                    .orElse(null);
            apples.remove(apple);
        }
    }

    private boolean isTail(int x, int y) {
        boolean result = false;

        for (int i = 1; i < tail.size(); i++) {
            if (tail.get(i).getX() == x && tail.get(i).getY() == y) {
                result = true;
            }
        }
        return result;
    }

    private boolean isApple(List<Apple> apples, int x, int y) {
        boolean result = false;
        for (Apple apple : apples) {
            if (apple.getX() == x && apple.getY() == y) {
                result = true;
            }
        }
        return result;
    }

    private Coord transformHead(Coord head) {
        Coord newHead = new Coord(head.getX(), head.getY());
        switch (direction) {
            case "UP":
                newHead.setY(head.getY() - 1);
                break;
            case "DOWN":
                newHead.setY(head.getY() + 1);
                break;
            case "LEFT":
                newHead.setX(head.getX() - 1);
                break;
            case "RIGHT":
                newHead.setX(head.getX() + 1);
                break;
        }
        return newHead;
    }
}
