# Quick Start Guide - All-In-Trigger

## Installation & Building

### Prerequisites
- Java 21+ (JDK installed)
- Gradle (included via wrapper)

### Build Project
```bash
cd /home/raccoon/IdeaProjects/All-In-Trigger
./gradlew build
```

### Run Game
```bash
./gradlew lwjgl3:run
```

## Game Controls

| Key | Action |
|-----|--------|
| **W** | Move Up |
| **A** | Move Left |
| **S** | Move Down |
| **D** | Move Right |
| **1** | Switch to Pistol |
| **2** | Switch to Shotgun |
| **3** | Switch to Rifle |
| **4** | Switch to Minigun |
| **LEFT MOUSE** | Fire Weapon |
| **CTRL** | Fire Weapon (Alternative) |
| **E** | Interact with Doors |
| **R** | Restart Game (on Game Over) |

## Game Objectives

1. **Enter Rooms**: Navigate to dungeon rooms and enter them
2. **Clear Rooms**: Defeat all enemies in each room
3. **Escape**: Once enemies are defeated, portals open - walk to them to exit
4. **Survive**: Don't let enemies kill you (HP reaches 0)
5. **Collect Loot**: Defeat enemies to get coins and health packs

## Weapon Selection Guide

### Starting Player (Use Pistol)
- Reliable single-shot weapon
- Good for learning mechanics
- 1 bullet per shot, 5° spread

### Crowd Control (Use Shotgun)
- 8 bullets per shot
- High spread for wide area
- Best for close-range enemies
- High damage output

### Precision Play (Use Rifle)
- Fast fire rate (12.5 shots/sec)
- Minimal spread (2°)
- Best for single-target elimination
- Highest damage per bullet

### Continuous Fire (Use Minigun)  
- Fastest fire rate (20 shots/sec)
- Medium spread
- Great for staying mobile
- Lower damage per shot

## Tips & Strategies

1. **Learn the Rooms**: Each room has a different layout - explore!
2. **Choose Your Weapon**: Different weapons work better for different situations
3. **Manage Your Position**: Use room layout to avoid enemy attacks
4. **Collect Bonuses**: Enemies drop coins and health - collect them!
5. **Money Matters**: Harder rooms give more money per kill
6. **Watch Your HP**: Keep your health and armor up

## Game Mechanics

### Health System
- Health (HP): Your main life counter
- Armor: Secondary protection (damage hits armor first)
- Energy: Used for special abilities (recovers over time)

### Difficulty Levels
- **Room 1** (Easy): Start here, learn the game
- **Room 2** (Medium): More enemies, escalated combat
- **Room 3** (Hard): Maximum challenge!

### Room Status
- **Red Overlay**: Room is locked, enemies still alive
- **Cyan Text**: Room cleared, portal ready to exit
- **Portal Animation**: Pulsing effect appears when room is cleared

## Troubleshooting

### Game Won't Build
- Ensure Java 21+ is installed: `java -version`
- Clear cache: `./gradlew clean`
- Try rebuild: `./gradlew rebuild`

### Game Runs Slow
- Close other applications
- Lower mouse sensitivity in game settings
- Reduce graphics settings if available

### Enemies Won't Die
- Switch to higher damage weapons (Shotgun/Rifle)
- Ensure bullets are actually hitting enemies
- Check weapon fire rate indicator

### Can't Exit Room
- Are all enemies defeated? Red overlay means room is still locked
- Look for glowing portal when room is cleared
- Portal appears in center of room

---

**Version**: 1.0  
**Game Type**: Dungeon Combat  
**Status**: Fully Playable

