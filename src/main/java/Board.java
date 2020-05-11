import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {

    // wymiary mojej planszy
    private int boardHeight = 40;
    private int boardLength = 40;
    private Snake snake; // przechowuje info o ulozeniu weza (kratki)
    private List<Apple> apples = new ArrayList<>(); //kolekcja jabłek//wylosuje gdzie maja byc//co x cykli ruchu//przechpowuje wszystkie jablka na planszy

    public Board(int boardHeight, int boardLength) {
        this.boardHeight = boardHeight;
        this.boardLength = boardLength;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getBoardLength() {
        return boardLength;
    }

    public void setBoardLength(int boardLength) {
        this.boardLength = boardLength;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public List<Apple> getApples() {
        return apples;
    }

    @Override //do testowania w konsoli (uzycie), bo finalnie niepotrzebne
    public String toString() {
        String s = genMinus(boardLength + 2) + "\n";
        for (int row = 0; row < boardHeight; row++) {
            s += "|";
            for (int col = 0; col < boardLength; col++) {
                s += getSymbol(col, row);
            }
            s += "|\n";
        }
        s += genMinus(boardLength + 2);
        return s;
    }

    public String getSymbol(int col, int row) { // do testowania w konsoli

        Optional<String> sign = snake.tail.stream()
                .filter(c -> c.getX() == col && c.getY() == row) //
                .map(c -> "X")
                .findAny();

        String s = sign.orElse(" ");
        s = apples.stream()
                .filter(a -> a.getX() == col && a.getY() == row)
                .map(a -> "O")
                .findAny()
                .orElse(s); // Return the value if present, otherwise return (what's in brackets)
        return s;
    }

    private String genMinus(int boardLength) { // do testowania w konsoli
        String s = ""; //tworze pustego Stringa
        while (s.length() < boardLength) { //s.length() zmienia się z każdym nowym dodanym znakiem
            s += "-";                    // "" daje s.length() równe 0, "-" daje 1, "--" daje 2
        }
        return s;
    }
}
