package com.example.gameproject.entities.particals;

import static android.opengl.ETC1.getHeight;
import static android.opengl.ETC1.getWidth;

import android.graphics.Canvas;
import android.graphics.PointF;

import com.example.gameproject.entities.Entity;
import com.example.gameproject.entities.entities.Player;

public class Particle extends Entity {
    private int amount;
    private int index;
    private boolean isActive = false;
    private Particles particlesType;


    public Particle(int x, int y, Particles particlesType) {
        super(new PointF(x,y),particlesType.getMaxWidth(), particlesType.getMaxHeight());

        this.particlesType = particlesType;
        this.amount = particlesType.getAmount();
    }

    public void update(Player player) {
        if (isActive) {
            index++;
            if (index >= amount)
                isActive = false;
        }else {
            setPos(player);
        }
    }

    public void draw(Canvas canvas) {
        if (isActive)
            canvas.drawBitmap(particlesType.getImages(index), getHitbox().left, getHitbox().top, null);


    }

    public void setPos(Player player) {
       this.getHitbox().set(player.getHitbox());
    }

    public void Activate() {
        isActive = true;
    }
}
