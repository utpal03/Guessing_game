public class Player {
    private String name;
    private int score;

    public Player() {
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public String toString() {
        return "Player name: " + name + "\n Score: " + score;
    }
}
