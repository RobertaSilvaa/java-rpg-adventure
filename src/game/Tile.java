package game;

public class Tile {
    private Monster monster;
    private Item item;
    private boolean visited;

    public Tile() {
        this.monster = null;
        this.item = null;
        this.visited = false; // Tiles start unexplored.
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void markVisited() {
        this.visited = true;
    }

    public boolean isVisited() {
        return visited;
    }

}
