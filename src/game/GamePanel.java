package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel {
    private Player player;
    private Board board;

    private JTextArea fightMessages;
    private JScrollPane scrollPane;

    private JPanel panel;
    private JPanel sidePanel;
    private JPanel centerPanel;
    private JPanel imagePanel;
    private JPanel infoPanel;
    private JPanel controlPanel;
    private JFrame frame;

    private JLabel labelIcon;
    private JLabel healthLabel;
    private JLabel maxDamageLabel;
    private JLabel speedLabel;

    private int count;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 800));
        setFocusable(true);
        this.panel = new JPanel();
        this.frame = new JFrame();
        this.fightMessages = new JTextArea(5, 20);
        this.fightMessages.setEditable(false);
        this.fightMessages.setLineWrap(true);
        this.fightMessages.setWrapStyleWord(true);
        this.scrollPane = new JScrollPane(fightMessages);
        this.count = 1;
    }

    // Generate monsters with attributes based on their level.
    private void generateMonsters(int count) {
        Random random = new Random();
        Monster boss = new Monster(19, 19, 15, 10, 6, 20);
        board.getTile(19, 19).setMonster(boss);

        for (int i = 0; i < count; i++) {
            int level = random.nextInt(5) + 1; // Random level from 1 to 5.
            int damage = 7 + level;
            int speed = 5 + level;
            int health = 10 + level;
            Monster monster = new Monster(0, 0, damage, speed, level, health);
            placeMonstersOnBoard(monster);
        }
    }

    // Place monsters on tiles without another monster, excluding the starting tile.

    private void placeMonstersOnBoard(Monster monster) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(20);
            y = random.nextInt(20);
        } while (board.getTile(x, y).getMonster() != null || (x == 0 && y == 0));

        board.getTile(x, y).setMonster(monster);
    }

    // Generate items by level.
    private void generateItems(int count) {
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int level = random.nextInt(5) + 1; // Random level from 1 to 5.
            Item item = new Item(level);
            placeItemsOnBoard(item);

        }

    }

    // Place items on tiles without another item, excluding the starting tile.

    private void placeItemsOnBoard(Item item) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(20);
            y = random.nextInt(20);
        } while (board.getTile(x, y).getItem() != null || (x == 0 && y == 0)); // Exclude occupied item slots and the starting tile.

        board.getTile(x, y).setItem(item);

    }

    // Show the main menu with play and tutorial options.
    public void showMainMenu() {
        if (panel != null) {
            clearPanel(panel);
        }

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton playButton = new JButton("Play");
        JButton tutorialButton = new JButton("Tutorial");

        JLabel label = new JLabel("Menu");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue());
        panel.add(label);

        panel.add(Box.createVerticalStrut(20));
        panel.add(playButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(tutorialButton);
        panel.add(Box.createVerticalStrut(20));

        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        frame.add(panel);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open the window maximized.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        playButton.addActionListener((ActionEvent e) -> {
            startNewGame(panel, frame);
        });
        tutorialButton.addActionListener((ActionEvent e) -> {
            showTutorial();
        });
    }

    private void showTutorial() {
        String tutorialText = "RPG Adventure Tutorial\n\n" +
                "Explore the 20 x 20 board, collect potions, and defeat the boss at (19, 19).\n\n" +
                "Movement:\nUse the on-screen Up, Down, Left, and Right buttons.\n\n" +
                "Items are collected automatically when you enter their tile.\n" +
                "Open Inventory to use a potion:\n" +
                "  - Health Potion: restores 5 health, up to maximum health.\n" +
                "  - Speed Potion: adds 2 speed.\n" +
                "  - Vision Potion: reveals adjacent tiles, including diagonals.\n" +
                "  - Damage Potion: adds 2 damage.\n" +
                "  - Greater Damage Potion: adds 5 damage.\n\n" +
                "Combat:\nEntering a monster tile starts a battle. Click Attack to play a round.\n" +
                "Your damage roll is compared with the monster's speed roll.\n" +
                "If damage exceeds speed, the difference is subtracted from health.\n" +
                "A surviving monster then attacks using the same rules.\n" +
                "You can use potions and one special ability per battle:\n" +
                "  - Warrior: +10 damage for the next combat round.\n" +
                "  - Archer: +10 speed for the next combat round.\n" +
                "  - Wizard: restores 5 health, up to maximum health.\n\n" +
                "Defeat the boss to win. Reaching zero health ends your adventure.\n\n" +
                "Good luck!";
        JOptionPane.showMessageDialog(this, tutorialText, "Game Tutorial", JOptionPane.INFORMATION_MESSAGE);
    }

    // Initialize the board and show character selection.
    public void startNewGame(JPanel panel, JFrame frame) {
        if (panel != null) {
            clearPanel(panel);
        }

        this.player = new Player();
        this.board = new Board();
        generateMonsters(20); // Generate 20 monsters.
        generateItems(20); // Generate 20 items.
        board.setI(0);

        JLabel label = new JLabel("Choose your class:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalGlue());
        panel.add(label);

        // Character selection buttons.
        JButton warriorButton = new JButton("Warrior");
        JButton archerButton = new JButton("Archer");
        JButton wizardButton = new JButton("Wizard");

        warriorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        archerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        wizardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(warriorButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(archerButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(wizardButton);

        panel.add(Box.createVerticalGlue());

        warriorButton.addActionListener((ActionEvent e) -> {
            showCharacterClass("Warrior", "src/resources/Warrior/Warrior.png", 20, 20, 5, "warrior", panel, frame);
        });

        archerButton.addActionListener((ActionEvent e) -> {
            showCharacterClass("Archer", "src/resources/archer/archer.png", 15, 10, 10, "archer", panel, frame);
        });

        wizardButton.addActionListener((ActionEvent e) -> {
            showCharacterClass("Wizard", "src/resources/wizard/Wizard.png", 20, 17, 7, "wizard", panel, frame);
        });
    }

    // Confirm the character class before starting the game.
    public void showCharacterClass(String characterClass, String iconPath, int health, int damage, int speed, String type,
            JPanel panel, JFrame frame) {
        String specialAbility = null;
        if (type.equals("warrior")) {
            specialAbility = "Damage + 10";
        } else if (type.equals("archer")) {
            specialAbility = "Speed + 10";
        } else if (type.equals("wizard")) {
            specialAbility = "Health + 5";
        }

        if (panel != null) {
            clearPanel(panel);
        }
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue()); // Center the content vertically.

        JLabel titleLabel = new JLabel(characterClass.toUpperCase() + "!");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        // Base image of the selected character.
        ImageIcon icon = new ImageIcon(iconPath);
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel labelIcon = new JLabel(new ImageIcon(image));
        labelIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(labelIcon);
        panel.add(Box.createVerticalStrut(20));

        addLabel("Health: " + health, panel);
        addLabel("Speed: " + speed, panel);
        addLabel("Damage: " + damage, panel);
        addLabel("Special ability: " + specialAbility, panel);
        panel.add(Box.createVerticalGlue());

        JButton button3 = new JButton("Menu");
        JButton button4 = new JButton("Start Game");
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(button3);
        panel.add(Box.createVerticalStrut(20));
        panel.add(button4);

        panel.add(Box.createVerticalGlue());

        // Return to the main menu.
        button3.addActionListener((ActionEvent a) -> {
            showMainMenu();
        });

        // Start the game.
        button4.addActionListener((ActionEvent a) -> {
            clearPanel(panel);
            this.player = new Player(health, damage, speed, type, type, iconPath);
            showGame();
        });
    }

    // Show the game, movement controls, settings, and inventory.

    public void showGame() {
        if (panel != null) {
            clearPanel(panel);
        }

        // Settings, inventory, and combat messages on the right.
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        JButton settingsButton = new JButton("Settings");
        JButton itemsButton = new JButton("Inventory");

        sidePanel.add(settingsButton);
        sidePanel.add(itemsButton);
        sidePanel.add(scrollPane);

        panel.setLayout(new BorderLayout());

        // Board on the left.

        board.setPreferredSize(new Dimension(400, 400));
        panel.add(board, BorderLayout.WEST);

        // Player statistics at the top.
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 3));

        healthLabel = new JLabel("Health: " + player.getHealth(), SwingConstants.CENTER);
        maxDamageLabel = new JLabel("Damage: " + player.getDamage(), SwingConstants.CENTER);
        speedLabel = new JLabel("Speed: " + player.getSpeed(), SwingConstants.CENTER);

        infoPanel.add(healthLabel);
        infoPanel.add(maxDamageLabel);
        infoPanel.add(speedLabel);

        panel.add(infoPanel, BorderLayout.NORTH);

        // Player image in the center.
        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon icon = new ImageIcon(player.getBaseImagePath());
        Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        labelIcon = new JLabel(new ImageIcon(image));
        labelIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        imagePanel.add(Box.createVerticalGlue());
        imagePanel.add(labelIcon);
        imagePanel.add(Box.createVerticalGlue());

        // Center the player image panel.
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(imagePanel, BorderLayout.CENTER);

        panel.add(sidePanel, BorderLayout.EAST);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Movement buttons at the bottom.
        controlPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        controlPanel.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        JButton btnUp = new JButton("Up");
        JButton btnLeft = new JButton("Left");
        JButton btnDown = new JButton("Down");
        JButton btnRight = new JButton("Right");

        gbc.gridx = 1;
        gbc.gridy = 0;
        controlPanel.add(btnUp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(btnLeft, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        controlPanel.add(btnDown, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        controlPanel.add(btnRight, gbc);

        panel.add(controlPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        btnUp.addActionListener((ActionEvent a) -> {
            movePlayer(0, -1, 1);
        });

        btnLeft.addActionListener((ActionEvent a) -> {
            movePlayer(-1, 0, 2);
        });

        btnDown.addActionListener((ActionEvent a) -> {
            movePlayer(0, 1, 3);
        });

        btnRight.addActionListener((ActionEvent a) -> {
            movePlayer(1, 0, 4);
        });

        itemsButton.addActionListener((ActionEvent e) -> {
            showInventory();
        });

        settingsButton.addActionListener((ActionEvent e) -> {
            showSettings();
        });

        frame.revalidate();
        frame.repaint();
    }

    // Move the player and interact with items and monsters.
    public void movePlayer(int dx, int dy, int movement) {
        int x = player.getX() + dx;
        int y = player.getY() + dy;
        ImageIcon icon;
        Image image = null;

        if (x < 20 && x > -1 && y < 20 && y > -1) { // Keep movement within the board.
            Monster monster = board.getTile(x, y).getMonster();
            if (monster != null) { // Start combat when a monster is encountered.

                if (x == 19 && y == 19) { // Use the boss battle image.
                    icon = new ImageIcon(player.getBossFight());
                    image = icon.getImage().getScaledInstance(800, 400, Image.SCALE_SMOOTH);
                    labelIcon.setIcon(new ImageIcon(image));
                } else {
                    icon = new ImageIcon(player.getFightImage());
                    image = icon.getImage().getScaledInstance(700, 300, Image.SCALE_SMOOTH);
                    labelIcon.setIcon(new ImageIcon(image));
                }

                clearPanel(controlPanel); // Disable movement during combat.

                JButton attackButton = new JButton("Attack");
                JButton specialAbility = new JButton("Special Ability");

                controlPanel.add(attackButton);
                controlPanel.add(specialAbility);

                panel.add(controlPanel, BorderLayout.SOUTH);
                frame.getContentPane().add(panel, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();

                specialAbility.addActionListener((ActionEvent a) -> {
                    if (player.getRemainingSpecialUses() == 1) {
                        count = 1;

                        if (player.getType().equals("warrior")) {
                            player.setDamage(10);
                        } else if (player.getType().equals("archer")) {
                            player.setSpeed(10);
                        } else if (player.getType().equals("wizard")) {
                            if (player.getHealth() + 5 > player.getMax()) { // Limit healing to maximum health.
                                player.setHealth(player.getMax() - player.getHealth());
                            } else {
                                player.setHealth(5);
                            }
                        }
                        player.setRemainingSpecialUses(0);
                        clearPanel(infoPanel);

                        healthLabel = new JLabel("Health: " + player.getHealth(), SwingConstants.CENTER);
                        maxDamageLabel = new JLabel("Damage: " + player.getDamage(), SwingConstants.CENTER);
                        speedLabel = new JLabel("Speed: " + player.getSpeed(), SwingConstants.CENTER);

                        infoPanel.add(healthLabel);
                        infoPanel.add(maxDamageLabel);
                        infoPanel.add(speedLabel);

                        panel.add(infoPanel, BorderLayout.NORTH);
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "You have already used your special ability. Wait until the next battle!");
                    }
                });

                // Advance combat when the player attacks.
                attackButton.addActionListener((ActionEvent a) -> {
                    fightMessages.setText("");
                    boolean battleFinished = fight(monster);

                    clearPanel(infoPanel);

                    healthLabel = new JLabel("Health: " + player.getHealth(), SwingConstants.CENTER);
                    maxDamageLabel = new JLabel("Damage: " + player.getDamage(), SwingConstants.CENTER);
                    speedLabel = new JLabel("Speed: " + player.getSpeed(), SwingConstants.CENTER);

                    infoPanel.add(healthLabel);
                    infoPanel.add(maxDamageLabel);
                    infoPanel.add(speedLabel);

                    panel.add(infoPanel, BorderLayout.NORTH);
                    frame.revalidate();
                    frame.repaint();

                    if (player.getRemainingSpecialUses() == 0 && count == 1){
                        if (player.getType().equals("warrior")) {
                            player.setDamage(-10);
                        }
                        if (player.getType().equals("archer")) {
                            player.setSpeed(-10);
                        }
                        count = 0;
                    }
                    if (battleFinished && player.getHealth() > 0) { // Handle victory over the monster.
                        if (x == 19 && y == 19) {
                            JFrame victoryFrame = new JFrame("Victory!");
                            victoryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            victoryFrame.setSize(300, 150);
                            victoryFrame.setLocationRelativeTo(null);

                            JLabel messageLabel = new JLabel("You won! What would you like to do?");
                            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                            JButton menuButton = new JButton("Back to Menu");
                            JButton quitButton = new JButton("Quit Game");

                            menuButton.addActionListener((ActionListener) new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    victoryFrame.dispose(); // Close the result window.
                                    showMainMenu();
                                }
                            });

                            quitButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.exit(0); // Exit the game.
                                }
                            });

                            JPanel panelF = new JPanel();
                            panelF.setLayout(new GridLayout(3, 1));
                            panelF.add(messageLabel);
                            panelF.add(menuButton);
                            panelF.add(quitButton);

                            victoryFrame.add(panelF);
                            victoryFrame.setVisible(true);
                        }
                        if (board.getTile(x, y).getItem() != null) { // Collect any item on the defeated monster tile.
                            fightMessages.setText("");
                            player.getInventory().addItem(board.getTile(x, y).getItem());
                            fightMessages.append("ITEM COLLECTED: " + board.getTile(x, y).getItem().getName());
                            fightMessages.setCaretPosition(fightMessages.getDocument().getLength());
                            board.getTile(x, y).setItem(null);
                        }

                        board.getTile(x, y).setMonster(null);
                        player.setXY(dx, dy);
                        board.setPlayerPosition(player.getX(), player.getY()); // Update the player position on the minimap.

                        if (player.getRemainingSpecialUses() == 0) {
                            player.setRemainingSpecialUses(1);
                        }
                        showGame();
                    } else if (battleFinished && player.getHealth() <= 0) { // Handle player defeat.
                        JFrame defeatFrame = new JFrame("Defeat");
                        defeatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        defeatFrame.setSize(300, 150);
                        defeatFrame.setLocationRelativeTo(null);

                        JLabel messageLabel = new JLabel("You lost! What would you like to do?");
                        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        JButton menuButton = new JButton("Back to Menu");
                        JButton quitButton = new JButton("Quit Game");

                        menuButton.addActionListener((ActionListener) new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                defeatFrame.dispose(); // Close the result window.
                                showMainMenu();
                            }
                        });

                        quitButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0); // Exit the game.
                            }
                        });

                        JPanel panelF = new JPanel();
                        panelF.setLayout(new GridLayout(3, 1));
                        panelF.add(messageLabel);
                        panelF.add(menuButton);
                        panelF.add(quitButton);

                        defeatFrame.add(panelF);
                        defeatFrame.setVisible(true);
                    }
                });
            } else {

                if (board.getTile(x, y).getItem() != null) { // Collect the item on this tile.
                    fightMessages.setText("");
                    player.getInventory().addItem(board.getTile(x, y).getItem());
                    fightMessages.append("ITEM COLLECTED: " + board.getTile(x, y).getItem().getName());
                    fightMessages.setCaretPosition(fightMessages.getDocument().getLength());
                    board.getTile(x, y).setItem(null);
                }

                // Show the image for the movement direction.
                switch (movement) {
                    case 1:
                        icon = new ImageIcon(player.getUp());
                        break;
                    case 2:
                        icon = new ImageIcon(player.getLeft());
                        break;
                    case 3:
                        icon = new ImageIcon(player.getDown());
                        ;
                        break;
                    case 4:
                        icon = new ImageIcon(player.getRight());
                        break;
                    default:
                        icon = new ImageIcon(player.getBaseImagePath());
                        break;
                }
                player.setXY(dx, dy);
                board.setPlayerPosition(player.getX(), player.getY()); // Update the player position on the board.
                image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                labelIcon.setIcon(new ImageIcon(image));
            }
            frame.revalidate();
            frame.repaint();
        } else {
            JLabel label = new JLabel("ERROR!");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createVerticalGlue());
            panel.add(label);
        }
    }

    // Resolve one combat round and report whether the battle has ended.
    public boolean fight(Monster monster) {
        boolean battleFinished = false;
        Random rand = new Random();
        int playerDamage = rand.nextInt(player.getDamage()) + 1; // Player damage roll.
        int monsterSpeed = rand.nextInt(monster.getSpeed()) + 1; // Monster speed roll.

        if (playerDamage > monsterSpeed) { // A hit requires damage to exceed the speed roll.
            int damageDealt = playerDamage - monsterSpeed; // Damage minus speed.
            fightMessages.append("Monster: " + monster.getHealth() + " - " + damageDealt + "\n\n");
            monster.setHealth(-damageDealt);

        } else {
            fightMessages.append("The monster blocked the attack!\n\n");
        }
        fightMessages.setCaretPosition(fightMessages.getDocument().getLength());

        // Check whether the monster survived.
        if (monster.getHealth() <= 0) {
            fightMessages.append("The monster was defeated!\n\n");
            fightMessages.setCaretPosition(fightMessages.getDocument().getLength());
            battleFinished = true;
        } else {
            // Monster turn.
            int monsterDamage = rand.nextInt(monster.getDamage()) + 1; // Monster damage roll.
            int playerSpeed = rand.nextInt(player.getSpeed()) + 1; // Player speed roll.

            if (monsterDamage > playerSpeed) {
                int damageTaken = monsterDamage - playerSpeed;
                fightMessages.append("Player: " + player.getHealth() + " - " + damageTaken + "\n\n");
                player.setHealth(-damageTaken);

                if (player.getHealth() <= 0) {
                    fightMessages.append("You lost!\n\n");
                    fightMessages.setCaretPosition(fightMessages.getDocument().getLength());
                    battleFinished = true;
                }

            } else {
                fightMessages.append("You blocked the attack!\n");
            }
        }
        fightMessages.setCaretPosition(fightMessages.getDocument().getLength());
        return battleFinished;
    }

    // Add a centered text label to a panel.
    private void addLabel(String text, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
    }

    // Clear and refresh the selected panel.
    private void clearPanel(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    // Show the inventory in a separate window.
    public void showInventory() {
        JFrame itemsFrame = new JFrame("Items");
        itemsFrame.setSize(600, 400);
        itemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridLayout(2, 5));

        // Item names, image paths, and inventory counts.
        String[] itemNames = {
                "Health Potion", "Speed Potion", "Vision Potion", "Damage Potion", "Greater Damage Potion"
        };
        String[] itemImagePaths = {
                "src/resources/Items/Life.png",
                "src/resources/Items/Speed.png",
                "src/resources/Items/Vision.png",
                "src/resources/Items/Damage.png",
                "src/resources/Items/ExtraDamage.png"
        };
        int[] itemCounts = new int[itemNames.length];

        // Count each potion type in the inventory.
        for (Item item : player.getInventory().getItems()) {
            for (int i = 0; i < itemNames.length; i++) {
                if (item != null) {
                    if (item.getName().equals(itemNames[i])) {
                        itemCounts[i]++;
                    }
                }
            }
        }

        for (int i = 0; i < itemNames.length; i++) {
            // Create an image and button panel for each item.
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BorderLayout());

            // Show the potion image.
            JLabel itemLabel = new JLabel(new ImageIcon(itemImagePaths[i]));
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Show the potion name and available quantity.
            JButton itemButton = new JButton(itemNames[i] + " (" + itemCounts[i] + ")");
            itemButton.setHorizontalAlignment(SwingConstants.CENTER);

            int index = i; // Capture the item index for the listener.
            itemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (itemCounts[index] > 0) {
                        // Update the quantity and button text.
                        itemCounts[index]--;
                        itemButton.setText(itemNames[index] + " (" + itemCounts[index] + ")");
                        useItem(itemNames[index]); // Use the selected item.
                    } else {
                        JOptionPane.showMessageDialog(itemsFrame, "You have no more " + itemNames[index] + "!"); // Report an unavailable potion.

                    }
                }
            });

            itemPanel.add(itemLabel, BorderLayout.NORTH);
            itemPanel.add(itemButton, BorderLayout.SOUTH);
            itemsPanel.add(itemPanel);
        }

        itemsFrame.add(itemsPanel);
        itemsFrame.setVisible(true);
    }

    // Apply the selected potion effect and consume one item.
    private void useItem(String itemName) {

        switch (itemName) {
            case "Health Potion":

                if (player.getHealth() + 5 > player.getMax()) { // Limit healing to maximum health.
                    player.setHealth(player.getMax() - player.getHealth());
                } else {
                    player.setHealth(5);
                }
                player.getInventory().removeItem("Health Potion");

                break;

            case "Speed Potion":
                player.setSpeed(2);
                player.getInventory().removeItem("Speed Potion");

                break;

            case "Vision Potion":

                // Player coordinates.
                int playerX = player.getX();
                int playerY = player.getY();

                // Reveal adjacent tiles.
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        // Skip the player tile.
                        if (dx == 0 && dy == 0) {
                            continue;
                        }

                        int blockX = playerX + dx;
                        int blockY = playerY + dy;

                        // Keep adjacent coordinates within the board.
                        if (blockX >= 0 && blockX < 20 && blockY >= 0 && blockY < 20) {
                            board.setVisited(blockX, blockY);
                        }
                    }
                }

                player.getInventory().removeItem("Vision Potion");

                break;

            case "Damage Potion":
                player.setDamage(2);
                player.getInventory().removeItem("Damage Potion");

                break;

            case "Greater Damage Potion":
                player.setDamage(5);
                player.getInventory().removeItem("Greater Damage Potion");

                break;
            default:
                break;
        }
        // Refresh the player statistics.
        clearPanel(infoPanel);

        healthLabel = new JLabel("Health: " + player.getHealth(), SwingConstants.CENTER);
        maxDamageLabel = new JLabel("Damage: " + player.getDamage(), SwingConstants.CENTER);
        speedLabel = new JLabel("Speed: " + player.getSpeed(), SwingConstants.CENTER);

        infoPanel.add(healthLabel);
        infoPanel.add(maxDamageLabel);
        infoPanel.add(speedLabel);

        panel.add(infoPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }

    // Show settings with menu, debug, and tutorial options.
    private void showSettings() {
        JFrame settingsFrame = new JFrame("Settings");
        settingsFrame.setSize(400, 300);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelC = new JPanel();
        panelC.setLayout(new GridLayout(3, 1));

        // Main menu button.
        JButton mainMenuButton = new JButton("Back to Menu");
        panelC.add(mainMenuButton);

        // Debug button.
        JButton debugButton = new JButton("Debug");
        panelC.add(debugButton);

        // Tutorial button.
        JButton tutorialButton = new JButton("Tutorial");
        panelC.add(tutorialButton);

        settingsFrame.add(panelC);
        settingsFrame.setVisible(true);

        mainMenuButton.addActionListener((ActionEvent a) -> {
            showMainMenu();
        });

        debugButton.addActionListener((ActionEvent a) -> {
            if (board.getI() == 0) {
                board.setI(1);
            } else {
                board.setI(0);
            }

            frame.revalidate();
            frame.repaint();
        });

        tutorialButton.addActionListener((ActionEvent a) -> {
            showTutorial();
        });
    }

}
