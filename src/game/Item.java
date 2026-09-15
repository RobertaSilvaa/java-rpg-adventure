package game;

public class Item {
    private int level;
    private String name;
    private String imagePath;

    public Item(int level) {
        this.level = level;

        // Select the item name and image by level.
        switch (level) {
            case 1:
                this.name = "Health Potion";
                this.imagePath = "src/resources/Items/Life.png";
                break;
            case 2:
                this.name = "Speed Potion";
                this.imagePath = "src/resources/Items/Speed.png";
                break;
            case 3:
                this.name = "Vision Potion";
                this.imagePath = "src/resources/Items/Vision.png";
                break;
            case 4:
                this.name = "Damage Potion";
                this.imagePath = "src/resources/Items/Damage.png";
                break;
            case 5:
                this.name = "Greater Damage Potion";
                this.imagePath = "src/resources/Items/ExtraDamage.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid item level: " + level);
        }
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

}
