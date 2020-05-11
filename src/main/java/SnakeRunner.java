import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeRunner extends Application {

    private Image imageBack = new Image("file:src/main/resources/Snake-skin-texture-01-HD-Images-41083.jpg");

    //kod niżej jest do javyfx
    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setBackground(background);

        Scene scene = new Scene(grid, 1600, 900, Color.SADDLEBROWN);
        Board board = new Board(45, 80);

        Snake snake = new Snake(new Coord(5, 5));
        Apple apple = new Apple(8, 8);

        snake.generateTail();
        board.setSnake(snake);
        board.getApples().add(apple);

        Game game = new Game(grid, board);
        for (int row = 0; row < board.getBoardHeight(); row++) {
            grid.getRowConstraints().add(new RowConstraints(20));

        }
        for (int col = 0; col < board.getBoardLength(); col++) {
            grid.getColumnConstraints().add(new ColumnConstraints(20));
        }
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D && !snake.direction.equals("LEFT")) {
                snake.direction = "RIGHT";
            } else if (event.getCode() == KeyCode.A && !snake.direction.equals("RIGHT")) {
                snake.direction = "LEFT";
            } else if (event.getCode() == KeyCode.W && !snake.direction.equals("DOWN")) {
                snake.direction = "UP";
            } else if (event.getCode() == KeyCode.S && !snake.direction.equals("UP")) {
                snake.direction = "DOWN";
            }

        });

        MyTimer myTimer = new MyTimer(game);
        myTimer.start();
        game.display();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(() -> game.move());
            }
        };
        timer.schedule(timerTask, 500);

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args); //przejscie do javy fx

        //kod niżej jest do testowania w konsoli
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        Board board = new Board(12, 20);

        Snake snake = new Snake(new Coord(5, 5));
        snake.generateTail();

        Apple apple = new Apple(3, 3);

        board.setSnake(snake);
        board.getApples().add(apple);

        int counter = 0;
        while (true) {
            System.out.println(board);
            String s = scanner.nextLine().toUpperCase();

            if (s.equals("U")) {
                snake.direction = "UP";
            } else if (s.equals("D")) {
                snake.direction = "DOWN";
            } else if (s.equals("R")) {
                snake.direction = "RIGHT";
            } else if (s.equals("L")) {
                snake.direction = "LEFT";
            }
            snake.move(board.getApples(), board);

            counter++;
            if (counter > 10) {
                counter = 0;
                generateApple(board);
            }
        }
    }

    private static void generateApple(Board board) {

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

    private class MyTimer extends AnimationTimer {

        private final Game game;
        private int counter = 0;

        public MyTimer(Game game) {

            this.game = game;
        }

        @Override
        public void handle(long now) {
            counter++;
            if (counter > 20) {
                game.move();
                counter = 0;
            }
        }
    }
}