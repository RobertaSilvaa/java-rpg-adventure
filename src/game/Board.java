package game;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Board extends JPanel {
    private int x, y, i;
    private Tile[][] tiles;
    private boolean[][] visited;
    private int playerX, playerY;

    public Board() {
        this.x = 20;
        this.y = 20;
        this.tiles = new Tile[this.getX()][this.getY()];
        this.visited = new boolean[this.getX()][this.getY()];
        this.setPreferredSize(new Dimension(400, 400)); // Minimap dimensions.

        // Initialize each tile on the board.
        for (int x = 0; x < this.getX(); x++) {
            for (int y = 0; y < this.getY(); y++) {
                tiles[x][y] = new Tile();
                visited[x][y] = false; // Mark every tile as unexplored.
            }
        }

        // Initialize the player position.
        playerX = 0;
        playerY = 0;
    }

    // Draw the board; debug mode reveals items and monsters.
    public void draw(Graphics g) {
        int tileSize = this.getWidth() / this.getX();
        if (this.i == 0) {
            for (int x = 0; x < this.getX(); x++) {
                for (int y = 0; y < this.getY(); y++) {
                    if (x == playerX && y == playerY) {
                        g.setColor(Color.PINK);
                    } else if (visited[x][y]) {
                        g.setColor(Color.LIGHT_GRAY);
                        if (tiles[x][y].getItem() != null) {
                            g.setColor(Color.GREEN);
                        }
                        if (tiles[x][y].getMonster() != null) {
                            if (tiles[x][y].getItem() != null) {
                                g.setColor(Color.BLUE);
                            } else {
                                g.setColor(Color.ORANGE);
                            }
                        }
                    } else {
                        g.setColor(Color.DARK_GRAY);
                    }
                    if (x == 19 && y == 19) {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        } else {
            for (int x = 0; x < this.getX(); x++) {
                for (int y = 0; y < this.getY(); y++) {
                    if (x == playerX && y == playerY) {
                        g.setColor(Color.PINK); // Player
                    } else if (visited[x][y]) {
                        g.setColor(Color.LIGHT_GRAY); // Explored
                    } else if (tiles[x][y].getItem() != null) {
                        g.setColor(Color.GREEN); // Items
                    } else {
                        g.setColor(Color.DARK_GRAY); // Unexplored
                    }
                    if (tiles[x][y].getMonster() != null) {
                        if (tiles[x][y].getItem() != null) {
                            g.setColor(Color.BLUE); // Monster and item
                        } else {
                            g.setColor(Color.ORANGE); // Monster
                        }
                    }
                    if (x == 19 && y == 19) {
                        g.setColor(Color.RED); // Boss
                    }
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }

        }

    }

    public void setI(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setPlayerPosition(int x, int y) {
        this.visited[playerX][playerY] = true;
        this.playerX = x;
        this.playerY = y;
        repaint();
    }

    public void setVisited(int x, int y) {
        this.visited[x][y] = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}
