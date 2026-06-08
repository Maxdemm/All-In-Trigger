package com.gambleknight.view;

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
import com.gambleknight.view.player.PlayerView;
import com.gambleknight.view.room.DoorView;
import com.gambleknight.view.room.RoomView;
import com.gambleknight.view.room.WallView;

// Імпорти твоїх ефектів
import com.gambleknight.view.effects.CoinEffectView;
import com.gambleknight.view.effects.DamageTextView;
import com.gambleknight.view.effects.ExplosionEffect;
import com.gambleknight.view.effects.HealTextView;

import com.gambleknight.view.ui.HUD;
import com.gambleknight.view.ui.MinimapUI;
import com.gambleknight.view.ui.SlotMachineUI;
import com.gambleknight.view.ui.UpgradeUI;
import com.gambleknight.view.ui.WeaponPanel;
import com.gambleknight.view.model.Coin;
import com.gambleknight.view.model.Enemy;
import com.gambleknight.view.model.HealthPack;

import java.util.ArrayList;
import java.util.List;

public class GameRenderer {
    private SpriteBatch batch;
    private ShapeRenderer globalShapeRenderer;
    private OrthographicCamera camera;
    private Matrix4 uiMatrix;
    private BitmapFont gameOverFont;

    private RoomView roomView;
    private PlayerView playerView;
    private HUD hud;
    private MinimapUI minimapUI;
    private WeaponPanel weaponPanel;
    private UpgradeUI upgradeUI;
    private SlotMachineUI slotMachineUI;

    private List<Coin> coins;
    private List<Enemy> enemies;
    private List<HealthPack> healthPacks;

    // СПИСКИ ДЛЯ ЕФЕКТІВ
    private List<CoinEffectView> coinEffects;
    private List<DamageTextView> damageTexts;
    private List<ExplosionEffect> explosions;
    private List<HealTextView> healTexts;

    private float playerX = 250f;
    private float playerY = 200f;
    private final float PLAYER_WIDTH = 32f;
    private final float PLAYER_HEIGHT = 36f;
    private final float PLAYER_SPEED = 300f;

    private int playerMoney = 0;
    private float playerHp = 100f;
    private final float MAX_HP = 100f;
    private float playerArm = 50f;
    private final float MAX_ARM = 100f;
    private float playerEng = 100f;
    private final float MAX_ENG = 100f;
    private final float ENERGY_REGEN_SPEED = 25f;

    private boolean isGameOver = false;
    private float attackVisualTimer = 0f;
    private float camTime = 0;

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

        hud = new HUD();
        minimapUI = new MinimapUI();
        weaponPanel = new WeaponPanel();
        upgradeUI = new UpgradeUI();
        slotMachineUI = new SlotMachineUI();

        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        healthPacks = new ArrayList<>();

        // Ініціалізація списків ефектів
        coinEffects = new ArrayList<>();
        damageTexts = new ArrayList<>();
        explosions = new ArrayList<>();
        healTexts = new ArrayList<>();

        initializeEntities();
    }

    private void initializeEntities() {
        coins.clear();
        enemies.clear();
        healthPacks.clear();
        clearAllEffects(); // Очищення ефектів при перезапуску/старті

        coins.add(new Coin(200, 250));
        coins.add(new Coin(300, 200));
        coins.add(new Coin(800, 200));
        coins.add(new Coin(1450, 1000));

        healthPacks.add(new HealthPack(450, 300));
        healthPacks.add(new HealthPack(900, 400));
        healthPacks.add(new HealthPack(200, 850));

        enemies.add(new Enemy(750, 300, "melee"));
        enemies.add(new Enemy(950, 150, "ranged"));
        enemies.add(new Enemy(200, 950, "ranged"));
        enemies.add(new Enemy(1500, 950, "melee"));
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
        playerX = 250f;
        playerY = 200f;
        playerHp = MAX_HP;
        playerArm = 50f;
        playerEng = MAX_ENG;
        playerMoney = 0;
        attackVisualTimer = 0f;
        isGameOver = false;
        initializeEntities();
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (isGameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                restartGame();
            }
            drawScene();
            drawGameOverUI();
            return;
        }

        camTime += delta;
        if (attackVisualTimer > 0) attackVisualTimer -= delta;

        playerEng = Math.min(MAX_ENG, playerEng + ENERGY_REGEN_SPEED * delta);

        // 1. РУХ ГРАВЦЯ
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
        }

        // 2. АТАКА (ПРОБІЛ)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && playerEng >= 15f) {
            playerEng -= 15f;
            attackVisualTimer = 0.15f;
            float attackRange = 65f;

            Rectangle attackArea = new Rectangle(
                playerX + PLAYER_WIDTH / 2f - attackRange,
                playerY + PLAYER_HEIGHT / 2f - attackRange,
                attackRange * 2,
                attackRange * 2
            );

            for (int i = enemies.size() - 1; i >= 0; i--) {
                Enemy enemy = enemies.get(i);
                if (attackArea.overlaps(enemy.getBounds())) {
                    enemy.hp -= 10f;

                    // СПАВН ЕФЕКТІВ ПРИ ПОПАДАННІ
                    explosions.add(new ExplosionEffect(enemy.x, enemy.y));
                    damageTexts.add(new DamageTextView(enemy.x, enemy.y + 30, "-10 HP"));

                    if (enemy.hp <= 0) {
                        enemies.remove(i);
                        playerMoney += 100;
                    }
                }
            }
        }

        // 3. ЗБИРАННЯ ПРЕДМЕТІВ ТА ОНОВЛЕННЯ ЕФЕКТІВ
        Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);

        // Збирання монет
        for (Coin coin : coins) {
            if (!coin.isCollected && playerRect.overlaps(coin.getBounds())) {
                coin.isCollected = true;
                playerMoney += 50;

                // СПАВН ЕФЕКТУ МОНЕТ (створюємо 6 золотих частинок)
                for (int j = 0; j < 6; j++) {
                    coinEffects.add(new CoinEffectView(coin.x, coin.y));
                }
            }
        }

        // Збирання аптечок
        for (HealthPack pack : healthPacks) {
            if (!pack.isCollected && playerRect.overlaps(pack.getBounds())) {
                if (playerHp < MAX_HP) {
                    pack.isCollected = true;
                    playerHp += 30f;
                    if (playerHp > MAX_HP) playerHp = MAX_HP;

                    // СПАВН ЕФЕКТУ ЛІКУВАННЯ
                    healTexts.add(new HealTextView(pack.x, pack.y + 10, "+30 HP"));
                }
            }
        }

        // Оновлення ворогів
        for (Enemy enemy : enemies) {
            float oldX = enemy.x;
            float oldY = enemy.y;

            enemy.update(delta, playerX, playerY);

            if (isColliding(enemy.getBounds())) {
                enemy.x = oldX;
                enemy.y = oldY;
            }

            if (playerRect.overlaps(enemy.getBounds())) {
                float damage = 45f * delta;

                if (playerArm > 0) {
                    playerArm -= damage;
                    if (playerArm < 0) {
                        playerHp += playerArm;
                        playerArm = 0;
                    }
                } else {
                    playerHp -= damage;
                }

                if (playerHp <= 0) {
                    playerHp = 0;
                    isGameOver = true;
                }
            }
        }

        // ОНОВЛЕННЯ ТА ОЧИЩЕННЯ ЕФЕКТІВ (З викликом dispose()!)
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

    private void drawScene() {
        batch.setProjectionMatrix(camera.combined);
        roomView.render(batch, camTime);

        globalShapeRenderer.setProjectionMatrix(camera.combined);
        globalShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Coin coin : coins) coin.render(globalShapeRenderer);
        for (HealthPack pack : healthPacks) pack.render(globalShapeRenderer);
        for (Enemy enemy : enemies) enemy.render(globalShapeRenderer);

        if (attackVisualTimer > 0) {
            globalShapeRenderer.setColor(1, 1, 1, 0.35f);
            globalShapeRenderer.circle(playerX + PLAYER_WIDTH / 2f, playerY + PLAYER_HEIGHT / 2f, 55f);
        }
        globalShapeRenderer.end();

        // РЕНДЕРЕРИНГ ЕФЕКТІВ НА СЦЕНІ (використовують внутрішній ShapeRenderer)
        for (CoinEffectView e : coinEffects) e.render(batch);
        for (ExplosionEffect e : explosions) e.render(batch);

        if (playerHp > 0) {
            playerView.render(batch, playerX, playerY, camTime * 40f);
        }

        // РЕНДЕРЕРИНГ ТЕКСТОВИХ ЕФЕКТІВ (вони самі відкривають/закривають batch)
        for (DamageTextView e : damageTexts) e.render(batch);
        for (HealTextView e : healTexts) e.render(batch);
    }

    private void drawUI() {
        batch.setProjectionMatrix(uiMatrix);
        hud.render(batch, playerHp, MAX_HP, playerArm, MAX_ARM, playerEng, MAX_ENG, playerMoney);
        weaponPanel.render(batch);
        upgradeUI.render(batch);
        slotMachineUI.render(batch);
        minimapUI.render(batch, playerX, playerY, coins, enemies);
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
        gameOverFont.draw(batch, "YOU DIED", 530, 410);
        gameOverFont.setColor(Color.WHITE);
        gameOverFont.draw(batch, "Press [R] to Restart", 450, 330);
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

        // Обов'язкове вивільнення ресурсів усіх активних часток при закритті гри
        clearAllEffects();
    }
}
