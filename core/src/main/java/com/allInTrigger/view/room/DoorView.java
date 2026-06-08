package com.allInTrigger.view.room;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class DoorView {
    private Vector2 position;
    private float width = 50;
    private float height = 80;
    private ShapeRenderer shapeRenderer;
    private boolean isOpen = false;

    public DoorView(float x, float y) {
        position = new Vector2(x, y);
        shapeRenderer = new ShapeRenderer();
    }

    public DoorView(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (isOpen) {
            shapeRenderer.setColor(0.2f, 0.6f, 0.2f, 0.4f);
            shapeRenderer.rect(position.x, position.y, width, height);
        } else {
            shapeRenderer.setColor(0.4f, 0.22f, 0.05f, 1f);
            shapeRenderer.rect(position.x, position.y, width, height);

            shapeRenderer.setColor(0.6f, 0.6f, 0.65f, 1f);
            shapeRenderer.circle(position.x + width / 2f, position.y + height / 2f, 4);
        }

        shapeRenderer.end();
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
    public com.badlogic.gdx.math.Rectangle getBounds() {
        return new com.badlogic.gdx.math.Rectangle(position.x, position.y, width, height);
    }
    public boolean isOpen() { return isOpen; }
    public Vector2 getPosition() { return position; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
