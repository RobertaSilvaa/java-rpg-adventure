package game;

public class Monster extends Entity {
    private int level;

    public Monster(int x, int y, int damage, int speed, int level, int health) {
        super(x, y, health, damage, speed);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

}
