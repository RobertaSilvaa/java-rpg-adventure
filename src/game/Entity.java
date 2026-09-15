package game;

public abstract class Entity { // Shared base class for players and monsters.
    private int x, y, health, damage, speed;

    public Entity(int x, int y, int health, int damage, int speed) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
    }

    public Entity() {
    }

    public void setXY(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }

    public void setSpeed(int speed) {
        this.speed += speed;
    }

    public void setHealth(int health) {
        this.health += health;
    }

}
