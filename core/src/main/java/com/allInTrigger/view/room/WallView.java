package com.allInTrigger.view.room;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class WallView {
    private Vector2 position;
    private float width;
    private float height;
    private ShapeRenderer shapeRenderer;

    public WallView(float x, float y, float width, float height) {
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        shapeRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(position.x, position.y, width, height);

        shapeRenderer.end();
    }
    public com.badlogic.gdx.math.Rectangle getBounds() {
        return new com.badlogic.gdx.math.Rectangle(position.x, position.y, width, height);
    }

    public Vector2 getPosition() { return position; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

