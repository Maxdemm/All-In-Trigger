package com.allInTrigger.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.allInTrigger.view.player.PlayerView;
import com.allInTrigger.view.enemy.EnemyView;
import com.allInTrigger.view.room.DoorView;
import com.allInTrigger.view.room.RoomView;
import com.allInTrigger.view.room.WallView;

import com.allInTrigger.view.effects.CoinEffectView;
import com.allInTrigger.view.effects.DamageTextView;
import com.allInTrigger.view.effects.ExplosionEffect;
import com.allInTrigger.view.effects.HealTextView;

import com.allInTrigger.view.ui.HUD;
import com.allInTrigger.view.ui.MinimapUI;
import com.allInTrigger.view.ui.SlotMachineUI;
import com.allInTrigger.view.ui.SoundManager; // ← ЗВУК
import com.allInTrigger.view.ui.UpgradeUI;
import com.allInTrigger.view.ui.WeaponPanel;
import com.allInTrigger.view.model.*;

import com.allInTrigger.GameSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameRenderer {
    private SpriteBatch batch;
    private ShapeRenderer globalShapeRenderer;
    private OrthographicCamera camera;
    private Matrix4 uiMatrix;
    private BitmapFont gameOverFont;

    private RoomView roomView;
    private PlayerView playerView;
    private EnemyView enemyView; // Додано гарний вигляд ворогів
    private HUD hud;
    private MinimapUI minimapUI;
    private WeaponPanel weaponPanel;
    private UpgradeUI upgradeUI;
    private SlotMachineUI slotMachineUI;

    private List<Coin> coins;
    private List<HealthPack> healthPacks;
    private List<LootDrop> lootDrops;

    private List<CoinEffectView> coinEffects;
    private List<DamageTextView> damageTexts;
    private List<ExplosionEffect> explosions;
    private List<HealTextView> healTexts;

    private List<Room> rooms;
    private Room currentRoom;
    private boolean isInRoom = false;
    private boolean roomLocked = false;

    private Weapon currentWeapon;
    private List<Weapon> availableWeapons;
    private List<Bullet> bullets;
    private Random random;

    private float playerX = 250f;
    private float playerY = 200f;
    private final float PLAYER_WIDTH = 32f;
    private final float PLAYER_HEIGHT = 36f;
    private final float PLAYER_SPEED = 300f;

    private int playerMoney = 100;
    private int initialMoney = 100;
    private final int MAX_MONEY = 1000;
    private float moneyShakeAmount = 0f;
    private float moneyShakeTimer = 0f;

    private int currentLevel = 1;
    private float inflationTimer = 0f;
    private boolean levelCleared = false;
    private boolean isGameOver = false;
    private float gameOverTimer = 0f;

    private boolean isGameOverScreen = false;
    private float attackVisualTimer = 0f;
    private float camTime = 0;

    private GameSettings gameSettings;

    public GameRenderer() {
        batch = new SpriteBatch();
        globalShapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        uiMatrix = new Matrix4().setToOrtho2D(0, 0, 1280, 720);

        gameOverFont = new BitmapFont();
        gameOverFont.getData().setScale(2.5f);

        roomView = new RoomView();
        playerView = new PlayerView();
        enemyView = new EnemyView(); // Ініціалізація красивого вигляду ворогів

        hud = new HUD();
        minimapUI = new MinimapUI();
        weaponPanel = new WeaponPanel();
        upgradeUI = new UpgradeUI();
        slotMachineUI = new SlotMachineUI();

        coins = new ArrayList<>();
        healthPacks = new ArrayList<>();
        lootDrops = new ArrayList<>();

        coinEffects = new ArrayList<>();
        damageTexts = new ArrayList<>();
        explosions = new ArrayList<>();
        healTexts = new ArrayList<>();

        rooms = new ArrayList<>();
        bullets = new ArrayList<>();
        availableWeapons = new ArrayList<>();
        random = new Random();

        availableWeapons.add(Weapon.createPistol());
        availableWeapons.add(Weapon.createShotgun());
        availableWeapons.add(Weapon.createRifle());
        availableWeapons.add(Weapon.createMinigun());
        currentWeapon = availableWeapons.get(0);

        initializeRoomsAndEntities();
    }

    private void initializeRoomsAndEntities() {
        // 1. Очищення всього старого
        coins.clear();
        healthPacks.clear();
        lootDrops.clear();
        rooms.clear();
        bullets.clear();
        clearAllEffects();

        if (currentLevel == 1) {
            playerMoney = GameConfig.Level1.INITIAL_MONEY;
            initialMoney = GameConfig.Level1.INITIAL_MONEY;
        } else if (currentLevel == 2) {
            playerMoney = GameConfig.Level2.INITIAL_MONEY;
            initialMoney = GameConfig.Level2.INITIAL_MONEY;
        } else if (currentLevel == 3) {
            playerMoney = GameConfig.Level3.INITIAL_MONEY;
            initialMoney = GameConfig.Level3.INITIAL_MONEY;
        } else {
            playerMoney = GameConfig.Level3.INITIAL_MONEY + (currentLevel - 3) * 20;
            initialMoney = playerMoney;
        }

        coins.add(new Coin(300, 250));

        Room spawnRoom = new Room(-1, 100, 250, 200, 200, 0);
        rooms.add(spawnRoom);

        Room room1 = new Room(0, 50, 800, 400, 400, 1);
        room1.addEnemy(new Enemy(150, 950, "melee"));
        room1.addEnemy(new Enemy(250, 900, "melee"));
        room1.addEnemy(new Enemy(200, 850, "ranged"));
        rooms.add(room1);

        Room room2 = new Room(1, 650, 50, 450, 450, 2);
        room2.addEnemy(new Enemy(750, 250, "melee"));
        room2.addEnemy(new Enemy(950, 150, "ranged"));
        room2.addEnemy(new Enemy(800, 300, "melee"));
        room2.addEnemy(new Enemy(850, 200, "ranged"));
        rooms.add(room2);

        Room room3 = new Room(2, 1300, 800, 450, 400, 3);
        room3.addEnemy(new Enemy(1400, 950, "melee"));
        room3.addEnemy(new Enemy(1500, 1050, "ranged"));
        room3.addEnemy(new Enemy(1550, 950, "melee"));
        room3.addEnemy(new Enemy(1600, 1000, "ranged"));
        room3.addEnemy(new Enemy(1650, 900, "melee"));
        rooms.add(room3);

        playerX = 150f;
        playerY = 300f;
    }

    /**
     * Допоміжний метод для пошуку безпечного місця появи ворога всередині кімнати.
     * Перевіряє, щоб ворог не з'явився у стіні або за межами карти.
     */
    private void spawnEnemySafely(Room room, String type) {
        float margin = 60f; // Відступ від стін для безпеки
        float ex = 0, ey = 0;
        boolean posValid = false;
        int attempts = 0;

        // Створюємо тимчасовий прямокутник для перевірки колізій (розмір ворога 32x32)
        Rectangle tempBounds = new Rectangle(0, 0, 32, 32);

        // Пробуємо знайти місце (максимум 15 спроб)
        while (!posValid && attempts < 15) {
            ex = room.bounds.x + margin + random.nextFloat() * (room.bounds.width - margin * 2);
            ey = room.bounds.y + margin + random.nextFloat() * (room.bounds.height - margin * 2);

            tempBounds.setPosition(ex, ey);

            // Використовуємо метод перевірки колізій зі стінами та дверима
            if (!isColliding(tempBounds)) {
                posValid = true;
            }
            attempts++;
        }

        Enemy e = new Enemy(ex, ey, type);
        applyDifficultyToEnemy(e);
        room.addEnemy(e);
    }

    private void applyDifficultyToEnemy(Enemy enemy) {
        if (enemy == null || gameSettings == null) return;
        float hpMult = gameSettings.getHpMultiplier();
        float speedMult = gameSettings.getSpeedMultiplier();

        if (currentLevel >= 4) {
            float extra = 1f + (currentLevel - 3) * 0.6f;
            hpMult *= extra;
            speedMult *= 1f + (currentLevel - 3) * 0.2f;
        }

        enemy.hp = enemy.hp * hpMult;
        enemy.speed = enemy.speed * speedMult;
    }

    private void clearAllEffects() {
        for (CoinEffectView e : coinEffects) e.dispose();
        for (DamageTextView e : damageTexts) e.dispose();
        for (ExplosionEffect e : explosions) e.dispose();
        for (HealTextView e : healTexts) e.dispose();
        coinEffects.clear();
        damageTexts.clear();
        explosions.clear();
        healTexts.clear();
    }

    private void restartGame() {
        playerX = 150f;
        playerY = 300f;
        playerMoney = initialMoney;
        attackVisualTimer = 0f;
        isGameOverScreen = false;
        isInRoom = false;
        roomLocked = false;
        currentRoom = null;
        currentLevel = 1;
        inflationTimer = 0f;
        levelCleared = false;
        bullets.clear();
        initializeRoomsAndEntities();
        // ── Відновлюємо музику рівня після рестарту ───────────────────────
        SoundManager.getInstance().playLevelMusic();
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.F9)) {
            gameSettings.cycleDifficulty();
            for (Enemy e : getAllEnemies()) {
                e.hp = e.hp * gameSettings.getHpMultiplier();
                e.speed = e.speed * gameSettings.getSpeedMultiplier();
            }
        }

        if (isGameOverScreen) {
            gameOverTimer += delta;
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                restartGame();
            }
            drawScene();
            drawGameOverUI();
            return;
        }

        camTime += delta;
        if (attackVisualTimer > 0) attackVisualTimer -= delta;
        if (moneyShakeTimer > 0) moneyShakeTimer -= delta;

        if (currentLevel == 3) {
            inflationTimer += delta;
            if (inflationTimer >= 2f) {
                playerMoney = Math.max(0, playerMoney - 1);
                inflationTimer = 0f;
                moneyShakeAmount = 3f;
                moneyShakeTimer = 0.1f;
            }
        }

        if (playerMoney <= 0) {
            isGameOverScreen = true;
            gameOverTimer = 0f;
            return;
        }

        updateCurrentRoom();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) currentWeapon = availableWeapons.get(0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) currentWeapon = availableWeapons.get(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) currentWeapon = availableWeapons.get(2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) currentWeapon = availableWeapons.get(3);

        float speed = PLAYER_SPEED * delta;
        float nextX = playerX;
        float nextY = playerY;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) nextY += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) nextY -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) nextX -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) nextX += speed;

        Rectangle playerBoundsX = new Rectangle(nextX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        Rectangle playerBoundsY = new Rectangle(playerX, nextY, PLAYER_WIDTH, PLAYER_HEIGHT);

        if (!isColliding(playerBoundsX)) playerX = nextX;
        if (!isColliding(playerBoundsY)) playerY = nextY;

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            interactWithDoors();
            tryEnterPortal();
        }

        currentWeapon.update(delta);
        if ((Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) && currentWeapon.canShoot()) {
            fireWeapon(delta);
        }

        Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);

            if (!bullet.isAlive()) {
                bullets.remove(i);
                continue;
            }

            if (currentRoom != null) {
                for (int j = currentRoom.enemies.size() - 1; j >= 0; j--) {
                    Enemy enemy = currentRoom.enemies.get(j);
                    if (bullet.getBounds().overlaps(enemy.getBounds())) {
                        enemy.hp -= bullet.damage;
                        explosions.add(new ExplosionEffect(enemy.x, enemy.y));
                        damageTexts.add(new DamageTextView(enemy.x, enemy.y + 30, "-" + (int)bullet.damage + " HP"));

                        if (enemy.hp <= 0) {
                            currentRoom.removeEnemy(enemy);
                            int moneyReward = GameConfig.MONEY_FROM_BASIC_ENEMY + (currentLevel * 25);
                            playerMoney += moneyReward;
                            moneyShakeAmount = GameConfig.MONEY_SHAKE_AMOUNT;
                            moneyShakeTimer = 0.15f;
                            SoundManager.getInstance().playCoin(); // ← ЗВУК нагороди

                            if (random.nextFloat() < 0.6f) {
                                lootDrops.add(new LootDrop(enemy.x, enemy.y, "coin"));
                            }
                            if (random.nextFloat() < 0.2f) {
                                lootDrops.add(new LootDrop(enemy.x, enemy.y, "health"));
                            }
                        }

                        bullets.remove(i);
                        break;
                    }
                }
            }
        }

        for (Coin coin : coins) {
            if (!coin.isCollected && playerRect.overlaps(coin.getBounds())) {
                coin.isCollected = true;
                playerMoney += 20;
                moneyShakeAmount = 2f;
                moneyShakeTimer = 0.1f;
                SoundManager.getInstance().playCoin(); // ← ЗВУК монети

                for (int j = 0; j < 6; j++) {
                    coinEffects.add(new CoinEffectView(coin.x, coin.y));
                }
            }
        }

        for (int i = lootDrops.size() - 1; i >= 0; i--) {
            LootDrop loot = lootDrops.get(i);
            if (!loot.collected && playerRect.overlaps(loot.getBounds())) {
                loot.collected = true;
                if (loot.type.equals("coin")) {
                    playerMoney += 15;
                    moneyShakeAmount = 1.5f;
                    moneyShakeTimer = 0.1f;
                    SoundManager.getInstance().playCoin(); // ← ЗВУК лут-монети
                }
                lootDrops.remove(i);
            }
        }

        // Update enemies
        if (currentRoom != null) {
            for (int i = currentRoom.enemies.size() - 1; i >= 0; i--) {
                Enemy enemy = currentRoom.enemies.get(i);
                float oldX = enemy.x;
                float oldY = enemy.y;

                enemy.updateInRoom(delta, playerX, playerY, currentRoom.bounds);

                if (isColliding(enemy.getBounds())) {
                    enemy.x = oldX;
                    enemy.y = oldY;
                }

                if (playerRect.overlaps(enemy.getBounds())) {
                    float damage = 5f * delta;
                    playerMoney = Math.max(0, (int)(playerMoney - damage));
                    moneyShakeAmount = 2f;
                    moneyShakeTimer = 0.05f;
                }
            }

            if (roomLocked && currentRoom.allEnemiesDefeated()) {
                roomLocked = false;
                currentRoom.clear();
            }
        }

        for (int i = coinEffects.size() - 1; i >= 0; i--) {
            CoinEffectView e = coinEffects.get(i);
            e.update(delta);
            if (!e.isActive()) { e.dispose(); coinEffects.remove(i); }
        }
        for (int i = damageTexts.size() - 1; i >= 0; i--) {
            DamageTextView e = damageTexts.get(i);
            e.update(delta);
            if (!e.isActive()) { e.dispose(); damageTexts.remove(i); }
        }
        for (int i = explosions.size() - 1; i >= 0; i--) {
            ExplosionEffect e = explosions.get(i);
            e.update(delta);
            if (!e.isActive()) { e.dispose(); explosions.remove(i); }
        }
        for (int i = healTexts.size() - 1; i >= 0; i--) {
            HealTextView e = healTexts.get(i);
            e.update(delta);
            if (!e.isActive()) { e.dispose(); healTexts.remove(i); }
        }

        camera.position.set(playerX + PLAYER_WIDTH / 2f, playerY + PLAYER_HEIGHT / 2f, 0);
        camera.update();

        drawScene();
        drawUI();
    }

    private void updateCurrentRoom() {
        Room previousRoom = currentRoom;
        currentRoom = null;

        for (Room room : rooms) {
            if (room.playerIsInRoom(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT)) {
                currentRoom = room;
                break;
            }
        }

        if (currentRoom != previousRoom && currentRoom != null && !currentRoom.isCleared) {
            if (!currentRoom.isEntered) {
                currentRoom.enter();
                roomLocked = true;
            }
        }

        if (currentRoom == null) {
            roomLocked = false;
        }
    }

    private void fireWeapon(float delta) {
        if (playerMoney < currentWeapon.costPerShot) {
            return;
        }

        currentWeapon.shoot();
        SoundManager.getInstance().playShoot(); // ← ЗВУК пострілу

        playerMoney -= currentWeapon.costPerShot;
        moneyShakeAmount = GameConfig.MONEY_SHAKE_AMOUNT;
        moneyShakeTimer = 0.1f;

        Enemy target = null;
        if (currentRoom != null && currentRoom.enemies.size() > 0) {
            float nearestDist = Float.MAX_VALUE;
            for (Enemy enemy : currentRoom.enemies) {
                float dx = enemy.x - playerX;
                float dy = enemy.y - playerY;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                if (dist < nearestDist) {
                    nearestDist = dist;
                    target = enemy;
                }
            }
        }

        float baseAngle = 0f;
        if (target != null) {
            float dx = target.x - (playerX + PLAYER_WIDTH / 2);
            float dy = target.y - (playerY + PLAYER_HEIGHT / 2);
            float angleToTarget = (float) Math.atan2(dy, dx);
            baseAngle = (float) Math.toDegrees(angleToTarget);
        }

        for (int i = 0; i < currentWeapon.bulletsPerShot; i++) {
            float spread = (random.nextFloat() - 0.5f) * currentWeapon.spread;
            float finalAngle = baseAngle + spread;
            bullets.add(new Bullet(
                playerX + PLAYER_WIDTH / 2,
                playerY + PLAYER_HEIGHT / 2,
                finalAngle,
                currentWeapon.bulletSpeed,
                currentWeapon.damage,
                currentWeapon.type
            ));
        }

        attackVisualTimer = 0.1f;
    }

    private void drawScene() {
        batch.setProjectionMatrix(camera.combined);
        roomView.render(batch, camTime);

        globalShapeRenderer.setProjectionMatrix(camera.combined);
        globalShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Coin coin : coins) coin.render(globalShapeRenderer);
        for (HealthPack pack : healthPacks) pack.render(globalShapeRenderer);
        for (LootDrop loot : lootDrops) loot.render(globalShapeRenderer);

        if (currentRoom != null && roomLocked) {
            globalShapeRenderer.setColor(1f, 0f, 0f, 0.6f);
            globalShapeRenderer.rect(currentRoom.bounds.x, currentRoom.bounds.y, 8, currentRoom.bounds.height);
            globalShapeRenderer.rect(currentRoom.bounds.x + currentRoom.bounds.width - 8, currentRoom.bounds.y, 8, currentRoom.bounds.height);
            globalShapeRenderer.rect(currentRoom.bounds.x, currentRoom.bounds.y, currentRoom.bounds.width, 8);
            globalShapeRenderer.rect(currentRoom.bounds.x, currentRoom.bounds.y + currentRoom.bounds.height - 8, currentRoom.bounds.width, 8);
        }

        if (currentRoom != null) {
            // Використовуємо новий красивий рендеринг ворогів
            for (Enemy enemy : currentRoom.enemies) {
                enemyView.render(globalShapeRenderer, enemy.x, enemy.y, enemy.type, camTime);
            }

            if (currentRoom.isCleared) {
                drawPortal(currentRoom.portalBounds.x, currentRoom.portalBounds.y, camTime);
            }
        }
        if (attackVisualTimer > 0) {
            globalShapeRenderer.setColor(1, 1, 1, 0.35f);
            globalShapeRenderer.circle(playerX + PLAYER_WIDTH / 2f, playerY + PLAYER_HEIGHT / 2f, 55f);
        }
        globalShapeRenderer.end();

        globalShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Bullet bullet : bullets) {
            bullet.render(globalShapeRenderer);
        }
        globalShapeRenderer.end();

        for (CoinEffectView e : coinEffects) e.render(batch);
        for (ExplosionEffect e : explosions) e.render(batch);

        float targetEnemyX = 0;
        float targetEnemyY = 0;
        if (currentRoom != null && currentRoom.enemies.size() > 0) {
            Enemy nearest = currentRoom.enemies.get(0);
            float minDist = Float.MAX_VALUE;
            for (Enemy enemy : currentRoom.enemies) {
                float dx = enemy.x - playerX;
                float dy = enemy.y - playerY;
                float dist = (float) Math.sqrt(dx * dx + dy * dy);
                if (dist < minDist) {
                    minDist = dist;
                    nearest = enemy;
                }
            }
            targetEnemyX = nearest.x + nearest.width / 2;
            targetEnemyY = nearest.y + nearest.height / 2;
        }
        playerView.render(batch, playerX, playerY, camTime * 40f, targetEnemyX, targetEnemyY);

        for (DamageTextView e : damageTexts) e.render(batch);
        for (HealTextView e : healTexts) e.render(batch);
    }

    public void draw3DPortal(float x, float y, float time) {
        globalShapeRenderer.setColor(0, 0, 0, 0.4f);
        globalShapeRenderer.ellipse(x - 20, y - 8, 90, 20);
        float pulse = (float) Math.sin(time * 5f) * 4f;
        globalShapeRenderer.setColor(0.3f, 0f, 0.6f, 0.4f);
        globalShapeRenderer.ellipse(x + 10, y, 30 + pulse, 75);
        globalShapeRenderer.setColor(0.1f, 0.6f, 1f, 0.7f);
        globalShapeRenderer.ellipse(x + 17, y + 10, 16 - pulse, 55);
        globalShapeRenderer.setColor(0.2f, 0.2f, 0.25f, 1f);
        globalShapeRenderer.rect(x - 10, y, 16, 80);
        globalShapeRenderer.setColor(0.35f, 0.35f, 0.4f, 1f);
        globalShapeRenderer.rect(x - 10, y + 80, 16, 6);
        globalShapeRenderer.rect(x + 44, y, 16, 80);
        globalShapeRenderer.rect(x + 44, y + 80, 16, 6);
        globalShapeRenderer.setColor(0.25f, 0.25f, 0.3f, 1f);
        globalShapeRenderer.rect(x - 14, y + 70, 78, 12);
    }

    private void drawUI() {
        batch.setProjectionMatrix(uiMatrix);

        batch.begin();
        gameOverFont.getData().setScale(3f);
        gameOverFont.setColor(1f, 0.84f, 0f, 1f);

        float shakeX = 0, shakeY = 0;
        if (moneyShakeTimer > 0) {
            shakeX = (random.nextFloat() - 0.5f) * moneyShakeAmount;
            shakeY = (random.nextFloat() - 0.5f) * moneyShakeAmount;
        }

        gameOverFont.draw(batch, "$ " + playerMoney, 50 + shakeX, 680 + shakeY);
        gameOverFont.getData().setScale(1.5f);
        gameOverFont.setColor(Color.WHITE);

        String levelName = (currentLevel == 1 ? "Трущоби" : currentLevel == 2 ? "Казино" : "Волл-Стріт");
        gameOverFont.draw(batch, "Level " + currentLevel + ": " + levelName, 50, 630);

        gameOverFont.getData().setScale(1.2f);
        gameOverFont.setColor(Color.CYAN);
        gameOverFont.draw(batch, "1-4: " + currentWeapon.name + " ($" + currentWeapon.costPerShot + "/shot)", 50, 580);

        gameOverFont.setColor(Color.YELLOW);
        if (currentRoom != null) {
            gameOverFont.draw(batch, "Enemies: " + currentRoom.enemies.size(), 50, 530);
        } else {
            gameOverFont.draw(batch, "Not in room", 50, 530);
        }

        if (currentRoom != null && roomLocked) {
            gameOverFont.setColor(Color.RED);
            gameOverFont.getData().setScale(2f);
            gameOverFont.draw(batch, "KILL ALL ENEMIES!", 300, 680);
        }
        if (currentRoom != null
            && currentRoom.isCleared
            && currentRoom.isPortalEnabledForCurrentLevel(currentLevel)) {

            gameOverFont.setColor(Color.GREEN);
            gameOverFont.getData().setScale(2f);
            gameOverFont.draw(batch, "EXIT PORTAL OPEN!", 300, 680);
        }

        if (currentLevel == 3) {
            gameOverFont.setColor(Color.RED);
            gameOverFont.getData().setScale(1.5f);
            gameOverFont.draw(batch, "INFLATION: -$1 every 2 sec", 50, 480);
        }

        gameOverFont.setColor(Color.WHITE);
        gameOverFont.getData().setScale(1.2f);
        gameOverFont.draw(batch, "Difficulty: " + gameSettings.getDifficulty().name(), 50, 440);

        batch.end();

        try {
            minimapUI.render(batch, playerX, playerY, coins, getAllEnemies());
        } catch (Throwable t) {
            // safe-ignore
        }
    }

    private List<Enemy> getAllEnemies() {
        List<Enemy> allEnemies = new ArrayList<>();
        for (Room room : rooms) {
            allEnemies.addAll(room.enemies);
        }
        return allEnemies;
    }

    private void drawGameOverUI() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        globalShapeRenderer.setProjectionMatrix(uiMatrix);
        globalShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        globalShapeRenderer.setColor(0, 0, 0, 0.75f);
        globalShapeRenderer.rect(0, 0, 1280, 720);
        globalShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        gameOverFont.setColor(Color.RED);
        gameOverFont.getData().setScale(3f);
        gameOverFont.draw(batch, "BANKRUPTCY!", 420, 450);

        gameOverFont.setColor(Color.YELLOW);
        gameOverFont.getData().setScale(2f);
        gameOverFont.draw(batch, "Balance: $" + playerMoney, 420, 350);

        gameOverFont.setColor(Color.WHITE);
        gameOverFont.getData().setScale(1.5f);
        gameOverFont.draw(batch, "Level " + currentLevel + " - " + (currentLevel == 1 ? "Трущоби" : currentLevel == 2 ? "Казино" : "Волл-Стріт"), 420, 280);

        gameOverFont.getData().setScale(1.5f);
        gameOverFont.draw(batch, "Press [R] to Restart", 450, 150);
        batch.end();
    }

    private boolean isColliding(Rectangle bounds) {
        for (WallView wall : roomView.getWalls()) {
            if (bounds.overlaps(wall.getBounds())) return true;
        }
        for (DoorView door : roomView.getDoors()) {
            if (!door.isOpen() && bounds.overlaps(door.getBounds())) return true;
        }
        return false;
    }

    private void interactWithDoors() {
        Rectangle interactionArea = new Rectangle(playerX - 20, playerY - 20, PLAYER_WIDTH + 40, PLAYER_HEIGHT + 40);
        for (DoorView door : roomView.getDoors()) {
            if (interactionArea.overlaps(door.getBounds())) {
                door.setOpen(!door.isOpen());
                break;
            }
        }
    }

    private void tryEnterPortal() {
        if (currentRoom == null) return;
        if (!currentRoom.isCleared) return;
        if (!currentRoom.isPortalEnabledForCurrentLevel(currentLevel)) return;

        Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        if (playerRect.overlaps(currentRoom.portalBounds)) {
            goToNextLevel();
        }
    }

    private void goToNextLevel() {
        currentLevel++;
        if (currentLevel > 7) currentLevel = 1;

        playerX = 150f;
        playerY = 300f;
        bullets.clear();
        initializeRoomsAndEntities();
    }

    public void dispose() {
        batch.dispose();
        globalShapeRenderer.dispose();
        gameOverFont.dispose();
        roomView.dispose();
        playerView.dispose();
        hud.dispose();
        minimapUI.dispose();
        weaponPanel.dispose();
        upgradeUI.dispose();
        slotMachineUI.dispose();

        clearAllEffects();
        bullets.clear();

        SoundManager.getInstance().dispose(); // ← ЗВУК: очищаємо ресурси
    }

    private String getLevelName(int level) {
        switch (level) {
            case 1: return "Трущоби";
            case 2: return "Казино";
            case 3: return "Волл-Стріт";
            case 4: return "Бізнес-центр";
            case 5: return "Фінансова глибина";
            case 6: return "Крипто-лабіринт";
            case 7: return "Космічна станція";
            default: return "Невідомо";
        }
    }
}
