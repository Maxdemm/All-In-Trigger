package com.allInTrigger.view.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RoomView {
    private ShapeRenderer shapeRenderer;

    private List<WallView> walls;
    private List<DoorView> doors;
    private List<Rectangle> floorAreas;

    public RoomView() {
        shapeRenderer = new ShapeRenderer();
        walls = new ArrayList<>();
        doors = new ArrayList<>();
        floorAreas = new ArrayList<>();

        initializeInfrastructure();
    }

    private void initializeInfrastructure() {
        floorAreas.add(new Rectangle(100, 100, 350, 350));
        floorAreas.add(new Rectangle(450, 220, 200, 100));
        floorAreas.add(new Rectangle(650, 50, 450, 450));
        floorAreas.add(new Rectangle(1100, 220, 200, 100));
        floorAreas.add(new Rectangle(1300, 100, 400, 400));

        floorAreas.add(new Rectangle(225, -300, 100, 400));
        floorAreas.add(new Rectangle(825, 500, 100, 300));
        floorAreas.add(new Rectangle(1450, -300, 100, 400));

        floorAreas.add(new Rectangle(50, -650, 450, 350));
        floorAreas.add(new Rectangle(-150, -500, 200, 100));
        floorAreas.add(new Rectangle(-550, -650, 400, 350));

        floorAreas.add(new Rectangle(1300, -650, 400, 350));
        floorAreas.add(new Rectangle(1700, -500, 200, 100));
        floorAreas.add(new Rectangle(1900, -650, 400, 350));

        floorAreas.add(new Rectangle(650, 800, 450, 400));
        floorAreas.add(new Rectangle(450, 950, 200, 100));
        floorAreas.add(new Rectangle(50, 800, 400, 400));

        floorAreas.add(new Rectangle(1100, 950, 200, 100));
        floorAreas.add(new Rectangle(1300, 800, 450, 400));

        doors.add(new DoorView(225, 100, 100, 20));
        doors.add(new DoorView(825, 480, 100, 20));
        doors.add(new DoorView(1450, 100, 100, 20));
        doors.add(new DoorView(225, -300, 100, 20));
        doors.add(new DoorView(825, 800, 100, 20));
        doors.add(new DoorView(1450, -300, 100, 20));

        doors.add(new DoorView(50, -500, 20, 100));
        doors.add(new DoorView(430, 950, 20, 100));
        doors.add(new DoorView(1300, 950, 20, 100));

        doors.get(0).setOpen(true);

        walls.add(new WallView(100, 450, 350, 15));
        walls.add(new WallView(100, 100, 125, 15));
        walls.add(new WallView(325, 100, 125, 15));
        walls.add(new WallView(100, 115, 15, 335));
        walls.add(new WallView(435, 100, 15, 120));
        walls.add(new WallView(435, 320, 15, 130));

        walls.add(new WallView(450, 320, 200, 15));
        walls.add(new WallView(450, 210, 200, 10));

        walls.add(new WallView(650, 500, 175, 15));
        walls.add(new WallView(925, 500, 175, 15));
        walls.add(new WallView(650, 40, 450, 10));
        walls.add(new WallView(650, 50, 15, 170));
        walls.add(new WallView(650, 320, 15, 180));
        walls.add(new WallView(1085, 50, 15, 170));
        walls.add(new WallView(1085, 320, 15, 180));

        walls.add(new WallView(1100, 320, 200, 15));
        walls.add(new WallView(1100, 210, 200, 10));

        walls.add(new WallView(1300, 500, 400, 15));
        walls.add(new WallView(1300, 100, 150, 15));
        walls.add(new WallView(1550, 100, 150, 15));
        walls.add(new WallView(1700, 100, 15, 400));
        walls.add(new WallView(1300, 100, 15, 120));
        walls.add(new WallView(1300, 320, 15, 180));

        walls.add(new WallView(210, -300, 15, 400));
        walls.add(new WallView(325, -300, 15, 400));
        walls.add(new WallView(810, 500, 15, 300));
        walls.add(new WallView(925, 500, 15, 300));
        walls.add(new WallView(1435, -300, 15, 400));
        walls.add(new WallView(1550, -300, 15, 400));

        walls.add(new WallView(50, -650, 15, 150));
        walls.add(new WallView(50, -400, 15, 115));
        walls.add(new WallView(485, -650, 15, 365));
        walls.add(new WallView(50, -650, 450, 15));
        walls.add(new WallView(325, -300, 175, 15));
        walls.add(new WallView(50, -300, 175, 15));
        walls.add(new WallView(-150, -400, 200, 15));
        walls.add(new WallView(-150, -510, 200, 10));
        walls.add(new WallView(-550, -300, 400, 15));
        walls.add(new WallView(-550, -650, 400, 15));
        walls.add(new WallView(-550, -650, 15, 365));
        walls.add(new WallView(-165, -650, 15, 150));
        walls.add(new WallView(-165, -400, 15, 115));

        walls.add(new WallView(1300, -650, 15, 365));
        walls.add(new WallView(1685, -650, 15, 150));
        walls.add(new WallView(1685, -400, 15, 115));
        walls.add(new WallView(1300, -650, 400, 15));
        walls.add(new WallView(1300, -300, 150, 15));
        walls.add(new WallView(1550, -300, 150, 15));
        walls.add(new WallView(1700, -400, 200, 15));
        walls.add(new WallView(1700, -510, 200, 10));

        walls.add(new WallView(1900, -300, 400, 15));
        walls.add(new WallView(1900, -650, 400, 15));
        walls.add(new WallView(2285, -650, 15, 365));
        walls.add(new WallView(1900, -650, 15, 150));
        walls.add(new WallView(1900, -400, 15, 115));


        walls.add(new WallView(650, 1185, 450, 15));
        walls.add(new WallView(650, 800, 175, 15));
        walls.add(new WallView(925, 800, 175, 15));
        walls.add(new WallView(650, 800, 15, 150));
        walls.add(new WallView(650, 1050, 15, 150));
        walls.add(new WallView(1085, 800, 15, 150));
        walls.add(new WallView(1085, 1050, 15, 150));


        walls.add(new WallView(450, 1050, 200, 15));
        walls.add(new WallView(450, 940, 200, 10));
        walls.add(new WallView(50, 1185, 400, 15));
        walls.add(new WallView(50, 800, 400, 15));
        walls.add(new WallView(50, 800, 15, 400));
        walls.add(new WallView(435, 800, 15, 150));
        walls.add(new WallView(435, 1050, 15, 150));

        walls.add(new WallView(1100, 1050, 200, 15));
        walls.add(new WallView(1100, 940, 200, 10));
        walls.add(new WallView(1300, 1185, 450, 15));
        walls.add(new WallView(1300, 800, 450, 15));
        walls.add(new WallView(1735, 800, 15, 400));
        walls.add(new WallView(1300, 800, 15, 150));
        walls.add(new WallView(1300, 1050, 15, 150));
    }

    public void render(SpriteBatch batch, float time) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.11f, 0.11f, 0.14f, 1f);
        for (Rectangle area : floorAreas) {
            shapeRenderer.rect(area.x, area.y, area.width, area.height);
        }

        shapeRenderer.setColor(0.14f, 0.14f, 0.18f, 0.6f);
        for (Rectangle area : floorAreas) {
            drawGridForRect(area.x, area.y, area.width, area.height);
        }

        shapeRenderer.setColor(0.08f, 0.08f, 0.1f, 1f);
        shapeRenderer.rect(200, 200, 50, 50);
        shapeRenderer.rect(850, 300, 50, 50);
        shapeRenderer.rect(1450, 250, 50, 50);
        shapeRenderer.rect(250, -150, 50, 50);
        shapeRenderer.rect(850, 700, 50, 50);
        shapeRenderer.rect(-300, -500, 50, 50);
        shapeRenderer.rect(2050, -500, 50, 50);
        shapeRenderer.rect(200, 1000, 50, 50);
        shapeRenderer.rect(1500, 1000, 50, 50);

        for (float y = 220; y < 320; y += 25) {
            drawYellowBarrier(450, y);
            drawYellowBarrier(1300, y);
        }
        for (float y = 900; y < 1100; y += 25) {
            drawYellowBarrier(1550, y);
        }

        draw3DPortal(870, 980, time);
        draw3DTorch(680, 1120, time, 1.2f);
        draw3DTorch(1050, 1120, time, 3.4f);
        drawVolumetricCrate(720, 1100);

        draw3DPortal(200, 950, time);
        draw3DTorch(100, 1100, time, 0.5f);
        draw3DTorch(380, 1100, time, 2.5f);
        drawVolumetricCrate(120, 850);
        draw3DBush(300, 850);

        draw3DTorch(1350, 1100, time, 1.9f);
        draw3DTorch(1680, 1100, time, 4.1f);
        drawVolumetricCrate(1600, 1050);
        drawVolumetricCrate(1625, 1050);
        drawVolumetricCrate(1612, 1070);
        drawVolumetricCrate(1400, 900);
        drawVolumetricCrate(1430, 880);
        drawVolumetricCrate(1650, 850);

        draw3DTorch(680, 430, time, 3.1f);
        draw3DTorch(1350, 410, time, 2.3f);
        drawVolumetricCrate(930, 410);
        draw3DTorch(875, 700, time, 0.2f);

        draw35DItems(time);

        draw3DTorch(275, -100, time, 1.9f);
        draw3DTorch(1500, -100, time, 1.1f);

        draw3DPineTree(-400, -450);
        draw3DPineTree(-250, -480);
        draw3DBush(-450, -550);
        draw3DBush(-200, -580);
        draw3DTorch(-500, -400, time, 0.7f);
        draw3DTorch(-200, -400, time, 2.8f);
        drawVolumetricCrate(-350, -600);

        draw3DTorch(100, -400, time, 1.5f);
        draw3DBush(400, -450);
        drawVolumetricCrate(200, -600);

        draw3DTorch(1350, -400, time, 3.0f);
        drawVolumetricCrate(1400, -450);
        drawVolumetricCrate(1430, -460);

        draw3DTorch(1950, -400, time, 0.1f);
        draw3DTorch(2200, -400, time, 1.1f);
        draw3DTorch(1950, -600, time, 2.2f);
        draw3DTorch(2200, -600, time, 3.3f);
        drawVolumetricCrate(2050, -450);
        drawVolumetricCrate(2080, -450);
        drawVolumetricCrate(2065, -430);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        for (DoorView door : doors) door.render(batch);
        for (WallView wall : walls) wall.render(batch);
    }

    private void drawGridForRect(float startX, float startY, float width, float height) {
        for (float x = startX; x <= startX + width; x += 50) {
            shapeRenderer.rect(x, startY, 1.5f, height);
        }
        for (float y = startY; y <= startY + height; y += 50) {
            shapeRenderer.rect(startX, y, width, 1.5f);
        }
    }

    private void draw35DItems(float time) {
        draw3DTorch(750, 400, time, 1.5f);
        draw3DBush(1010, 390);
        draw3DPineTree(740, 380);
        draw3DTorch(1000, 380, time, 4.6f);
        draw3DBush(1580, 370);
        draw3DTorch(350, 350, time, 1.2f);
        draw3DBush(160, 320);
        draw3DPineTree(1360, 320);
        drawVolumetricCrate(690, 300);
        draw3DBush(940, 290);
        draw3DPineTree(320, 280);
        draw3DTorch(950, 270, time, 0.5f);
        draw3DPortal(1500, 240, time);
        draw3DPineTree(820, 220);
        draw3DTorch(220, 210, time, 0.0f);
        drawVolumetricCrate(1350, 200);
        draw3DTorch(880, 200, time, 0.8f);
        drawVolumetricCrate(1376, 185);
        draw3DBush(1610, 170);
        draw3DBush(380, 160);
        drawVolumetricCrate(870, 160);
        drawVolumetricCrate(233, 158);
        draw3DTorch(1580, 150, time, 1.1f);
        drawVolumetricCrate(220, 140);
        draw3DPineTree(1420, 140);
        drawVolumetricCrate(246, 135);
        draw3DPineTree(150, 130);
        draw3DBush(1020, 110);
        draw3DPineTree(700, 90);
    }

    private void draw3DTorch(float x, float y, float time, float phaseOffset) {
        float flicker = (float) Math.sin(time * 6f + phaseOffset) * 4f;
        shapeRenderer.setColor(1f, 0.55f, 0.1f, 0.04f);
        shapeRenderer.circle(x, y + 15, 65 + flicker);
        shapeRenderer.setColor(1f, 0.4f, 0.05f, 0.09f);
        shapeRenderer.circle(x, y + 15, 40 + flicker * 0.6f);
        shapeRenderer.setColor(1f, 0.25f, 0.0f, 0.16f);
        shapeRenderer.circle(x, y + 15, 20 + flicker * 0.3f);
        shapeRenderer.setColor(0.22f, 0.22f, 0.26f, 1f);
        shapeRenderer.rect(x - 6, y, 12, 20);
        shapeRenderer.setColor(0.35f, 0.35f, 0.4f, 1f);
        shapeRenderer.rect(x - 8, y + 20, 16, 4);
        shapeRenderer.setColor(0.9f, 0.25f, 0f, 1f);
        shapeRenderer.triangle(x - 5, y + 24, x + 5, y + 24, x, y + 36 + flicker);
        shapeRenderer.setColor(1f, 0.85f, 0.2f, 1f);
        shapeRenderer.triangle(x - 2.5f, y + 24, x + 2.5f, y + 24, x, y + 31 + flicker * 0.5f);
    }

    private void draw3DPineTree(float x, float y) {
        shapeRenderer.setColor(0, 0, 0, 0.35f);
        shapeRenderer.ellipse(x - 15, y - 4, 40, 12);
        shapeRenderer.setColor(0.22f, 0.1f, 0.02f, 1f);
        shapeRenderer.rect(x - 4, y, 14, 14);
        shapeRenderer.setColor(0.35f, 0.18f, 0.05f, 1f);
        shapeRenderer.rect(x, y, 4, 14);
        shapeRenderer.setColor(0.08f, 0.32f, 0.25f, 1f);
        shapeRenderer.triangle(x - 24, y + 12, x, y + 12, x, y + 38);
        shapeRenderer.setColor(0.14f, 0.48f, 0.38f, 1f);
        shapeRenderer.triangle(x, y + 12, x + 24, y + 12, x, y + 38);
        shapeRenderer.setColor(0.11f, 0.4f, 0.32f, 1f);
        shapeRenderer.triangle(x - 18, y + 28, x, y + 28, x, y + 54);
        shapeRenderer.setColor(0.18f, 0.58f, 0.46f, 1f);
        shapeRenderer.triangle(x, y + 28, x + 18, y + 28, x, y + 54);
        shapeRenderer.setColor(0.15f, 0.48f, 0.38f, 1f);
        shapeRenderer.triangle(x - 12, y + 44, x, y + 44, x, y + 68);
        shapeRenderer.setColor(0.22f, 0.68f, 0.54f, 1f);
        shapeRenderer.triangle(x, y + 44, x + 12, y + 44, x, y + 68);
    }

    private void draw3DBush(float x, float y) {
        shapeRenderer.setColor(0, 0, 0, 0.35f);
        shapeRenderer.ellipse(x - 18, y - 6, 52, 16);
        shapeRenderer.setColor(0.04f, 0.18f, 0.06f, 1f);
        shapeRenderer.circle(x - 12, y + 10, 18);
        shapeRenderer.circle(x + 12, y + 10, 18);
        shapeRenderer.circle(x, y + 20, 22);
        shapeRenderer.setColor(0.09f, 0.35f, 0.12f, 1f);
        shapeRenderer.circle(x - 6, y + 14, 15);
        shapeRenderer.circle(x, y + 24, 16);
        shapeRenderer.setColor(0.16f, 0.52f, 0.2f, 1f);
        shapeRenderer.circle(x + 8, y + 14, 14);
        shapeRenderer.setColor(0.24f, 0.68f, 0.28f, 1f);
        shapeRenderer.circle(x, y + 28, 10);
    }

    private void drawVolumetricCrate(float x, float y) {
        shapeRenderer.setColor(0, 0, 0, 0.35f);
        shapeRenderer.ellipse(x - 2, y - 4, 30, 10);
        shapeRenderer.setColor(0.4f, 0.22f, 0.08f, 1f);
        shapeRenderer.rect(x, y, 26, 20);
        shapeRenderer.setColor(0.58f, 0.34f, 0.15f, 1f);
        shapeRenderer.rect(x, y + 20, 26, 8);
        shapeRenderer.setColor(0.28f, 0.15f, 0.05f, 1f);
        shapeRenderer.rect(x + 3, y, 3, 20);
        shapeRenderer.rect(x + 20, y, 3, 20);
    }

    private void draw3DPortal(float x, float y, float time) {
        shapeRenderer.setColor(0, 0, 0, 0.4f);
        shapeRenderer.ellipse(x - 20, y - 8, 90, 20);
        float pulse = (float) Math.sin(time * 5f) * 4f;
        shapeRenderer.setColor(0.3f, 0f, 0.6f, 0.4f);
        shapeRenderer.ellipse(x + 10, y, 30 + pulse, 75);
        shapeRenderer.setColor(0.1f, 0.6f, 1f, 0.7f);
        shapeRenderer.ellipse(x + 17, y + 10, 16 - pulse, 55);
        shapeRenderer.setColor(0.2f, 0.2f, 0.25f, 1f);
        shapeRenderer.rect(x - 10, y, 16, 80);
        shapeRenderer.setColor(0.35f, 0.35f, 0.4f, 1f);
        shapeRenderer.rect(x - 10, y + 80, 16, 6);
        shapeRenderer.rect(x + 44, y, 16, 80);
        shapeRenderer.rect(x + 44, y + 80, 16, 6);
        shapeRenderer.setColor(0.25f, 0.25f, 0.3f, 1f);
        shapeRenderer.rect(x - 14, y + 70, 78, 12);
    }

    private void drawYellowBarrier(float x, float y) {
        shapeRenderer.setColor(0, 0, 0, 0.3f);
        shapeRenderer.rect(x - 4, y - 2, 12, 24);
        shapeRenderer.setColor(0.8f, 0.5f, 0.0f, 1f);
        shapeRenderer.rect(x - 2, y, 8, 20);
        shapeRenderer.setColor(1f, 0.9f, 0.1f, 1f);
        shapeRenderer.rect(x - 1, y + 2, 6, 16);
    }

    public List<WallView> getWalls() { return walls; }
    public List<DoorView> getDoors() { return doors; }

    public void dispose() {
        shapeRenderer.dispose();
        for (WallView wall : walls) wall.dispose();
        for (DoorView door : doors) door.dispose();
    }
}
