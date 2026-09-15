# Java RPG Adventure

A Java Swing role-playing game with a 20 x 20 exploration board, three character classes, turn-based combat, and collectible potions. Defeat the boss at tile `(19, 19)` to win.

## Features

- Choose a Warrior, Archer, or Wizard.
- Explore a minimap that tracks visited tiles.
- Encounter 20 randomly placed monsters and a final boss.
- Collect 20 randomly placed potions and use them from the inventory.
- Use one special ability per battle.
- Open Settings to access the tutorial, return to the menu, or toggle the debug map.

## Requirements

- A Java Development Kit (JDK); compilation verified with JDK 21.
- A graphical desktop environment for Swing.
- No third-party libraries or build tools are required.

## Build and Run

Run these commands from the project root (the local `Jogo RPG` folder):

```shell
javac -encoding UTF-8 -d bin src/game/*.java
java -cp bin game.Main
```

Keep the working directory at the project root: images are loaded from relative paths under `src/resources/`.

In VS Code, open this folder as the workspace. The included Java settings use `src` for sources and `bin` for compiled output. Run `game.Main`.

## How to Play

1. Click **Play**, choose a class, and click **Start Game**.
2. Use the on-screen **Up**, **Down**, **Left**, and **Right** buttons to explore.
3. Entering a monster tile starts combat. Click **Attack** to resolve a round; a surviving monster retaliates.
4. Damage is the positive difference between the attacker's damage roll and the defender's speed roll.
5. Open **Inventory** to use potions. Use **Special Ability** once per battle.
6. Defeat the final boss to win. The adventure ends if your health reaches zero.

| Class | Starting health | Maximum damage roll | Maximum speed roll | Special ability |
| --- | ---: | ---: | ---: | --- |
| Warrior | 20 | 20 | 5 | +10 damage for the next combat round |
| Archer | 15 | 10 | 10 | +10 speed for the next combat round |
| Wizard | 20 | 17 | 7 | Restore 5 health, capped at maximum health |

| Potion | Effect |
| --- | --- |
| Health Potion | Restore 5 health, capped at maximum health |
| Speed Potion | Add 2 speed |
| Vision Potion | Reveal adjacent tiles, including diagonals |
| Damage Potion | Add 2 damage |
| Greater Damage Potion | Add 5 damage |

## Project Structure

```text
java-rpg-adventure/
├── .vscode/
│   └── settings.json
├── Relatório/
│   └── Relatório.pdf
├── src/
│   ├── game/
│   │   ├── Board.java
│   │   ├── Entity.java
│   │   ├── GamePanel.java
│   │   ├── Inventory.java
│   │   ├── Item.java
│   │   ├── Main.java
│   │   ├── Monster.java
│   │   ├── Player.java
│   │   └── Tile.java
│   └── resources/
│       ├── archer/
│       ├── Items/
│       ├── monsters/
│       ├── Warrior/
│       └── wizard/
├── tests/
│   └── GameRegressionTest.java
├── .gitignore
└── README.md
```

The original academic report is retained in Portuguese. `bin/` contains generated output and is excluded from Git. The optional local `lib/` folder is currently empty.

| File | Responsibility |
| --- | --- |
| `Main.java` | Application entry point |
| `GamePanel.java` | Menus, game interface, combat, and potion use |
| `Board.java` | Board tiles, exploration state, and minimap rendering |
| `Entity.java` | Shared position and combat attributes |
| `Player.java` | Character class, images, inventory, and special ability state |
| `Monster.java` | Enemy attributes and level |
| `Item.java` | Potion names, levels, and image paths |
| `Inventory.java` | Item storage and removal |
| `Tile.java` | Tile contents and visit flag |

## Verification

Compile and run the regression checks from the project root:

```shell
javac -encoding UTF-8 -d bin src/game/*.java tests/GameRegressionTest.java
java -cp bin game.GameRegressionTest
```

The checks cover potion effects and consumption, vision at every board position, and character image loading. They create an invisible Swing window and require a graphical desktop environment. Full interactive gameplay still requires manual testing.
