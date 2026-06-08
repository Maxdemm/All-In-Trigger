package com.allInTrigger.view.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class DamageTextView {
    private Vector2 position;
    private String text;
    private float duration = 1.0f;
    private float elapsed = 0;
    private BitmapFont font;
    private boolean isActive = true;

    public DamageTextView(float x, float y, String text) {
        position = new Vector2(x, y);
        this.text = text;
        font = new BitmapFont();
        font.getData().setScale(1.5f);
    }

    public void update(float deltaTime) {
        elapsed += deltaTime;
        position.y += 50 * deltaTime; // Move up

        if (elapsed >= duration) {
            isActive = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!isActive) return;

        batch.begin();

        float alpha = 1 - (elapsed / duration);
        font.setColor(1, 0, 0, alpha);
        font.draw(batch, text, position.x, position.y);

        batch.end();
    }

    public boolean isActive() { return isActive; }

    public void dispose() {
        font.dispose();
    }
}

