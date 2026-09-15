package game;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** Regression checks for potion behavior and character image paths. */
public class GameRegressionTest {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            GamePanel game = new GamePanel();
            try {
                checkVision(game);
                checkPotions(game);
                checkCharacterImages();
                System.out.println("PASS: vision at all 400 positions, potion effects and consumption, and character images.");
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            } finally {
                try {
                    ((JFrame) getField(game, "frame")).dispose();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        });
    }

    private static void checkVision(GamePanel game) throws Exception {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                Board board = new Board();
                Player player = new Player(20, 20, 5, "warrior", "Warrior", "src/resources/Warrior/Warrior.png");
                player.setXY(x, y);
                prepareGame(game, player, board);
                usePotion(game, player, 3);
                boolean[][] visited = (boolean[][]) getField(board, "visited");
                for (int tileX = 0; tileX < 20; tileX++) {
                    for (int tileY = 0; tileY < 20; tileY++) {
                        boolean adjacent = Math.abs(tileX - x) <= 1 && Math.abs(tileY - y) <= 1
                                && (tileX != x || tileY != y);
                        require(visited[tileX][tileY] == adjacent, "Incorrect vision reveal at " + x + ", " + y);
                    }
                }
            }
        }
    }

    private static void checkPotions(GamePanel game) throws Exception {
        Player player = new Player(20, 20, 5, "warrior", "Warrior", "src/resources/Warrior/Warrior.png");
        prepareGame(game, player, new Board());
        player.setHealth(-8);
        usePotion(game, player, 1);
        require(player.getHealth() == 17, "Health potion must restore five health.");
        usePotion(game, player, 1);
        require(player.getHealth() == 20, "Healing must respect maximum health.");
        usePotion(game, player, 2);
        require(player.getSpeed() == 7, "Speed potion must add two speed.");
        usePotion(game, player, 4);
        require(player.getDamage() == 22, "Damage potion must add two damage.");
        usePotion(game, player, 5);
        require(player.getDamage() == 27, "Greater damage potion must add five damage.");
    }

    private static void checkCharacterImages() {
        String[] types = {"warrior", "archer", "wizard"};
        String[] basePaths = {"src/resources/Warrior/Warrior.png", "src/resources/archer/archer.png",
                "src/resources/wizard/Wizard.png"};
        for (int index = 0; index < types.length; index++) {
            Player player = new Player(20, 20, 5, types[index], types[index], basePaths[index]);
            String[] paths = {player.getBaseImagePath(), player.getUp(), player.getDown(), player.getLeft(),
                    player.getRight(), player.getFightImage(), player.getBossFight()};
            for (String path : paths) {
                require(new ImageIcon(path).getIconWidth() > 0, "Image failed to load: " + path);
            }
            require(player.getRemainingSpecialUses() == 1, "Special ability must start available.");
            player.setRemainingSpecialUses(0);
            require(player.getRemainingSpecialUses() == 0, "Special ability state must update.");
        }
    }

    private static void prepareGame(GamePanel game, Player player, Board board) throws Exception {
        setField(game, "player", player);
        setField(game, "board", board);
        setField(game, "infoPanel", new JPanel());
    }

    private static void usePotion(GamePanel game, Player player, int level) throws Exception {
        Item item = new Item(level);
        require(new ImageIcon(item.getImagePath()).getIconWidth() > 0, "Potion image failed to load.");
        player.getInventory().addItem(item);
        Method method = GamePanel.class.getDeclaredMethod("useItem", String.class);
        method.setAccessible(true);
        method.invoke(game, item.getName());
        require(player.getInventory().getItems().isEmpty(), "The potion must be consumed.");
    }

    private static Object getField(Object object, String name) throws Exception {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }

    private static void setField(Object object, String name, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(object, value);
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
