package com.example.gameproject.entities.entities;

import static com.example.gameproject.entities.entities.GameCharacters.VILLAGER_DAD;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.helpers.GameConstants;
import com.example.gameproject.helpers.HelpMethods;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.GameImages;

import java.util.Random;


public class Villager extends Character {

    private Building building;
    private final Random rand = new Random();
    private long lastDirChange = System.currentTimeMillis();

    private boolean isTalking = false;
    private String conversation = null;

    Paint blackPaint = new Paint();
    Paint redPaint = new Paint();


    public Villager(PointF pos, VillagerType villagerType) {
        super(pos, getCharacterType(villagerType));
        setStartHealth(200);

        conversation = MainActivity.getGeminiAPI().askGemini("pretens you are a villager and will talk to a player," + " can you make some small talk with the player, no more then 10 words, just tell them something nice and friendly," + "do it in 2 lines, and every line no more then 12 letters..", MainActivity.getGameContext());
        splitConversation();

        blackPaint.setColor(android.graphics.Color.BLACK);
        blackPaint.setTextSize(30);
        blackPaint.setStyle(Paint.Style.FILL);
        blackPaint.setStrokeWidth(3);

        redPaint.setColor(android.graphics.Color.RED);
        redPaint.setTextSize(20);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(2);

    }

    private static GameCharacters getCharacterType(VillagerType villagerType) {
        return switch (villagerType) {
            case VILLAGER_DAD -> GameCharacters.VILLAGER_DAD;
            case VILLAGER_MOM -> GameCharacters.VILLAGER_MOM;
            case VILLAGER_BOY -> GameCharacters.VILLAGER_BOY;
            case VILLAGER_GREEN -> GameCharacters.VILLAGER_GREEN;
            case VILLAGER_BLACK -> GameCharacters.VILLAGER_BLACK;
            case VILLAGER_OLIVE -> GameCharacters.VILLAGER_OLIVE;
        };
    }

    public void update(double delta, GameMap gameMap) {
        if (!isTalking) {
            updateMove(delta, gameMap);
            updateAnimation();
        }
    }

    private void updateMove(double delta, GameMap gameMap) {
        RectF buildingHitbox = building.getHitbox();
        float radius = 5 * GameConstants.Sprite.SIZE;

        float buildingCenterX = buildingHitbox.centerX();
        float buildingCenterY = buildingHitbox.centerY();

        float villagerCenterX = hitbox.centerX();
        float villagerCenterY = hitbox.centerY();

        float distanceX = villagerCenterX - buildingCenterX;
        float distanceY = villagerCenterY - buildingCenterY;
        float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        float deltaChange = (float) (delta * 200);

        if (distance > radius) {
            float directionX = -distanceX / distance;
            float directionY = -distanceY / distance;


            if (HelpMethods.CanWalkHere(hitbox, directionX * deltaChange, directionY * deltaChange, gameMap)) {
                hitbox.offset(directionX * deltaChange, directionY * deltaChange);
            }

            changeDirection();
        }

        if (System.currentTimeMillis() - lastDirChange >= 3000) {
            faceDir = rand.nextInt(4);
            lastDirChange = System.currentTimeMillis();
        }
        MovePlayer(deltaChange, gameMap);
    }


    public void MovePlayer(float deltaChange, GameMap gameMap) {

        switch (faceDir) {
            case GameConstants.Face_Dir.DOWN:
                if (HelpMethods.CanWalkHere(hitbox, 0, deltaChange, gameMap)) {
                    hitbox.top += deltaChange;
                    hitbox.bottom += deltaChange;
                } else changeDirection();
                break;

            case GameConstants.Face_Dir.UP:
                if (HelpMethods.CanWalkHere(hitbox, 0, -deltaChange, gameMap)) {
                    hitbox.top -= deltaChange;
                    hitbox.bottom -= deltaChange;
                } else changeDirection();
                break;

            case GameConstants.Face_Dir.RIGHT:
                if (HelpMethods.CanWalkHere(hitbox, deltaChange, 0, gameMap)) {
                    hitbox.left += deltaChange;
                    hitbox.right += deltaChange;
                } else changeDirection();
                break;

            case GameConstants.Face_Dir.LEFT:
                if (HelpMethods.CanWalkHere(hitbox, -deltaChange, 0, gameMap)) {
                    hitbox.left -= deltaChange;
                    hitbox.right -= deltaChange;
                } else changeDirection();
                break;
        }

    }

    public void changeDirection() {
        switch (faceDir) {
            case GameConstants.Face_Dir.DOWN -> faceDir = GameConstants.Face_Dir.UP;
            case GameConstants.Face_Dir.UP -> faceDir = GameConstants.Face_Dir.DOWN;
            case GameConstants.Face_Dir.RIGHT -> faceDir = GameConstants.Face_Dir.LEFT;
            case GameConstants.Face_Dir.LEFT -> faceDir = GameConstants.Face_Dir.RIGHT;
        }

    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void startConversation() {
        if (!isTalking) {
            isTalking = true;
            faceDir = GameConstants.Face_Dir.DOWN;
            aniIndex = 0;
            conversation = MainActivity.getGeminiAPI().askGemini("pretens you are a villager and will talk to a player," + " can you make some small talk with the player, no more then 10 words, just tell them something nice and friendly," + "do it in 2 lines, and every line no more then 12 letters..", MainActivity.getGameContext());

            splitConversation();


            Log.d("conversation", "Start conversation");
            Log.d("conversation", "Conversation: " + conversation);
        }
    }

    private void splitConversation() {
        String rawConversation = MainActivity.getGeminiAPI().askGemini(
                "pretens you are a villager and will talk to a player," +
                        " can you make some small talk with the player, no more then 10 words, just tell them something nice and friendly," +
                        "do it in 2 lines, and every line no more then 12 letters..",
                MainActivity.getGameContext()
        );

        if (rawConversation != null && rawConversation.contains("!")) {
            int splitIndex = rawConversation.indexOf("!") + 1;
            conversation = rawConversation.substring(0, splitIndex) + "\n" + rawConversation.substring(splitIndex).trim();
        } else
            conversation = rawConversation;
    }

    public void endConversation() {
        isTalking = false;
        conversation = null;
    }

    public boolean isTalking() {
        return isTalking;
    }

    public void drawTalk(Canvas canvas, float cameraX, float cameraY) {
        if (conversation.contains("Error")) {
            isTalking = false;
            return;
        }
        int width = GameImages.TALKING_BUBBLE.getImage().getWidth();
        int height = GameImages.TALKING_BUBBLE.getImage().getHeight();
        RectF BubbleHitbox = new RectF(hitbox.left + cameraX + 50, hitbox.top + cameraY - height, hitbox.right + cameraX + width, hitbox.centerY() + cameraY - 50);

        if (GameActivity.isDrawHitbox())
            canvas.drawRect(BubbleHitbox, blackPaint);

        if (conversation != null) {
            canvas.drawBitmap(GameImages.TALKING_BUBBLE.getImage(), null, BubbleHitbox, null);

            String[] lines = conversation.split("\n");
            float textY = BubbleHitbox.top + 40;
            float lineHeight = blackPaint.getTextSize() + 5;

            for (String line : lines) {
                canvas.drawText(line, BubbleHitbox.left + 20, textY, blackPaint);
                textY += lineHeight;
            }
        }
    }



    public enum VillagerType {
        VILLAGER_DAD, VILLAGER_MOM, VILLAGER_BOY, VILLAGER_GREEN, VILLAGER_BLACK, VILLAGER_OLIVE;

        public static VillagerType getRandomVillagerType() {
            VillagerType[] types = VillagerType.values();
            return types[new Random().nextInt(types.length)];
        }
    }
}
