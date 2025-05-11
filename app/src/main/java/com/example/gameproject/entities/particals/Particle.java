package com.example.gameproject.entities.particals;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Player;

import java.util.Random;


public class Particle extends Entity {
    private final int amount;
    private int index = 0;
    private boolean isActive = false;
    private final Particles particlesType;
    private final Random random = new Random();
    private int randomY1, randomY2;
    private int randomX1;
    private Player player;


    public Particle(Particles particlesType) {
        super(new PointF(0, 0), particlesType.getMaxWidth(), particlesType.getMaxHeight());

        this.particlesType = particlesType;
        this.amount = particlesType.getAmount();
    }

    public void update(Player player) {
        this.player = player;
        if (player.isEffect())
            isActive = true;
        if (isActive) {
            index++;
            if (index >= amount) {
                isActive = false;
                index = 0;
                randomY1 = random.nextInt((int) player.getHitbox().height());
                randomY2 = random.nextInt((int) player.getHitbox().height());
                randomX1 = random.nextInt((int) player.getHitbox().width());
            }
        }
        setPos(player);
    }

    public void draw(Canvas canvas) {
        if (isActive) {
            canvas.drawBitmap(particlesType.getImages(index), getHitbox().left, getHitbox().top, null);
            canvas.drawBitmap(particlesType.getImages(index), player.getHitbox().right, player.getHitbox().top + randomY2, null);
            canvas.drawBitmap(particlesType.getImages(index), player.getHitbox().right - randomX1, player.getHitbox().bottom, null);
        }
    }

    public void setPos(Player player) {
        float x = player.getHitbox().left - getParticlesType().getCurrWidth(index);
        float y = player.getHitbox().bottom - getParticlesType().getCurrHeight(index) - randomY1;
        getHitbox().set((int) x, (int) y, (int) (x + getParticlesType().getCurrWidth(index)), (int) (y + getParticlesType().getCurrHeight(index)));
    }

    public Particles getParticlesType() {
        return particlesType;
    }
}
