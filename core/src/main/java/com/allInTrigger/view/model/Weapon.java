package com.allInTrigger.view.model;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;

public class Weapon {
    public String name;
    public String type; // "pistol", "shotgun", "rifle", "minigun", "laser"
    public float fireRate; // seconds between shots
    public float bulletSpeed;
    public float spread; // bullet spread in degrees
    public int bulletsPerShot;
    public float damage;
    public int costPerShot; // Gamble Knight: гроші за постріл
    public float timeSinceLastShot = 0;

    public Weapon(String name, String type, float fireRate, float bulletSpeed, float spread, int bulletsPerShot, float damage, int costPerShot) {
        this.name = name;
        this.type = type;
        this.fireRate = fireRate;
        this.bulletSpeed = bulletSpeed;
        this.spread = spread;
        this.bulletsPerShot = bulletsPerShot;
        this.damage = damage;
        this.costPerShot = costPerShot;
    }

    public void update(float delta) {
        timeSinceLastShot += delta;
    }

    public boolean canShoot() {
        return timeSinceLastShot >= fireRate;
    }

    public void shoot() {
        timeSinceLastShot = 0;
    }

    public static Weapon createPistol() {
        return new Weapon("Pistol", "pistol", 0.15f, 600f, 5f, 1, 10f, 1);
    }

    public static Weapon createShotgun() {
        return new Weapon("Shotgun", "shotgun", 0.5f, 400f, 25f, 8, 8f, 5);
    }

    public static Weapon createRifle() {
        return new Weapon("Rifle", "rifle", 0.08f, 800f, 2f, 1, 15f, 2);
    }

    public static Weapon createMinigun() {
        return new Weapon("Minigun", "minigun", 0.05f, 500f, 8f, 1, 5f, 1);
    }

    public static Weapon createLaser() {
        return new Weapon("Laser", "laser", 0.2f, 1000f, 1f, 1, 20f, 8);
    }
}


