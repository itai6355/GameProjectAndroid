package com.example.gameproject.entities.particals;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Player;

import java.util.Random;

public class Particle extends Entity {
    private final int amount;
    private int index = 0;
    private boolean isActive = false;
    private final Particles particlesType;
    private final Random random = new Random();
    private int randomY1, randomY2, randomX1;
    private Player player;

    public Particle(Particles particlesType) {
        super(new PointF(0, 0), particlesType.getMaxWidth(), particlesType.getMaxHeight());
        this.particlesType = particlesType;
        this.amount = particlesType.getAmount();
    }

    public void update(Player player) {
        this.player = player;
        Log.d("Particles", "updating");

        if (!player.isEffect()) {
            isActive = false;
            index = 0;
            return;
        }
        Log.d("Particles", "Player is under effect, activating particles.");


        isActive = true;

        index++;
        if (index >= amount) {
            index = 0;
            randomY1 = random.nextInt(Math.max(1, (int) player.getHitbox().height()));
            randomY2 = random.nextInt(Math.max(1, (int) player.getHitbox().height()));
            randomX1 = random.nextInt(Math.max(1, (int) player.getHitbox().width()));
        }
    }

    public void draw(Canvas canvas) {
        if (!isActive || player == null) return;

        canvas.drawBitmap(particlesType.getImages(index),
                player.getHitbox().left - particlesType.getCurrWidth(index),
                player.getHitbox().bottom - particlesType.getCurrHeight(index) - randomY1,
                null);

        canvas.drawBitmap(particlesType.getImages(index),
                player.getHitbox().right,
                player.getHitbox().top + randomY2,
                null);

        canvas.drawBitmap(particlesType.getImages(index),
                player.getHitbox().right - randomX1,
                player.getHitbox().bottom,
                null);
    }

    public Particles getParticlesType() {
        return particlesType;
    }

    public boolean isActive() {
        return isActive;
    }
}