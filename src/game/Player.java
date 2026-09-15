package game;

public class Player extends Entity {
    private String name, type;
    private String up, down, left, right, baseImagePath, fightImage, bossFight;
    private Inventory inventory;
    private int max, remainingSpecialUses;

    public Player(int health, int damage, int speed, String type, String name, String baseImagePath) {
        super(0, 0, health, damage, speed);
        this.name = name;
        this.type = type;
        this.baseImagePath = baseImagePath;
        this.inventory = new Inventory();
        setCharacterImages(type);
        this.max = health;
        this.remainingSpecialUses = 1;
    }

    public Player() {
    }

    // Select the images for the chosen character class.
    public void setCharacterImages(String type) {
        switch (type) {
            case "warrior":
                this.down = "src/resources/Warrior/down.png";
                this.left = "src/resources/Warrior/Left.png";
                this.right = "src/resources/Warrior/Right.png";
                this.up = "src/resources/Warrior/Up.png";
                this.fightImage = "src/resources/Warrior/fight.png";
                this.bossFight = "src/resources/Warrior/bossFight.png";

                break;
            case "archer":
                this.down = "src/resources/archer/down.png";
                this.left = "src/resources/archer/Left.png";
                this.right = "src/resources/archer/Right.png";
                this.up = "src/resources/archer/Up.png";
                this.fightImage = "src/resources/archer/fight.png";
                this.bossFight = "src/resources/archer/bossFight.png";

                break;
            case "wizard":
                this.down = "src/resources/wizard/down.png";
                this.left = "src/resources/wizard/Left.png";
                this.right = "src/resources/wizard/Right.png";
                this.up = "src/resources/wizard/Up.png";
                this.fightImage = "src/resources/wizard/fight.png";
                this.bossFight = "src/resources/wizard/bossFight.png";

                break;
            default:
                break;
        }

    }

    public String getBossFight() {
        return bossFight;
    }

    public String getFightImage() {
        return fightImage;
    }

    public String getBaseImagePath() {
        return baseImagePath;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDown() {
        return down;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getUp() {
        return up;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getMax() {
        return max;
    }

    public int getRemainingSpecialUses() {
        return remainingSpecialUses;
    }

    public void setRemainingSpecialUses(int remainingSpecialUses) {
        this.remainingSpecialUses = remainingSpecialUses;
    }
}
