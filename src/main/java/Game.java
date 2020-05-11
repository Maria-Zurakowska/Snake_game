import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Game {
    private final GridPane grid;
    private final Board board;
    int counter = 0;

    public Game(GridPane grid, Board board) {
        this.grid = grid;
        this.board = board;
    }

    public void display() {
        grid.getChildren().clear();
        for (int row = 0; row < board.getBoardLength(); row++) {
            for (int col = 0; col < board.getBoardHeight(); col++) {
                String s = board.getSymbol(col, row);
                Rectangle rectangle = new Rectangle(20, 20);
                if (s.equals("X"))
                    rectangle.setFill(Color.YELLOW);
                else if (s.equals("O"))
                    rectangle.setFill(Color.RED);
                if (!s.equals(" ")) {
                    grid.add(rectangle, col, row);
                }
            }
        }
        counter++;
        if (counter > 20) {
            counter = 0;
            generateApple(board);
        }
    }

    public void move() {

        int counter = 0;
        board.getSnake().move(board.getApples(), board);

        counter++;
        if (counter > 10) {
            counter = 0;
            generateApple(board);
        }
        display();
    }

    private void generateApple(Board board) {
        Random rand = new Random();
        Apple randomApple;

        int xLocation = rand.nextInt(board.getBoardHeight());
        int yLocation = rand.nextInt(board.getBoardLength());

        boolean isPresent = board.getSnake().getTail().stream()
                .filter(c -> c.getX() == xLocation && c.getY() == yLocation)
                .map(c -> true)
                .findAny()
                .orElse(false);
        if (!isPresent) {
            randomApple = new Apple(rand.nextInt(board.getBoardHeight()), rand.nextInt(board.getBoardLength()));
            board.getApples().add(randomApple);
        }
    }
}

