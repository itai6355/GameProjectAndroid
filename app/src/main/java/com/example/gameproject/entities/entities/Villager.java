package com.example.gameproject.entities.entities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;


import com.example.gameproject.entities.objects.Building;
import com.example.gameproject.environments.GameMap;
import com.example.gameproject.helpers.var.GameConstants;
import com.example.gameproject.helpers.var.HelpMethods;
import com.example.gameproject.helpers.var.Paints;
import com.example.gameproject.main.GameActivity;
import com.example.gameproject.main.MainActivity;
import com.example.gameproject.ui.GameImages;

import java.util.Locale;
import java.util.Random;


public class Villager extends Character {

    private Building building;
    private final Random rand = new Random();
    private long lastDirChange = System.currentTimeMillis();
    private final VillagerType villagerType;

    private TextToSpeech tts;
    private Context context;
    private boolean isTtsInitialized = false;

    private boolean isTalking = false;
    private String conversation;

    private final Paint blackPaint;


    public Villager(Context context, PointF pos, VillagerType villagerType) {
        super(pos, getCharacterType(villagerType));
        this.villagerType = villagerType;
        setStartHealth(200);

        this.context = context;
        blackPaint = Paints.BLACK_PAINT;

        conversation = MainActivity.getGeminiAPI().askGemini("pretend you are a villager and will talk to a player," +
                " make some small talk, no more than 10 words, 2 lines, each max 12 letters.");
        splitConversation();

        initTTS();
    }

    private void changeVoice(String targetVoiceNameContains) {
        if (tts == null || !isTtsInitialized) return;

        var voice = villagerType.getVoiceFromCategory(tts, targetVoiceNameContains);
        if (voice != null) {
            tts.setVoice(voice);
        } else {
            Log.d("Villager", "No voice found for category: " + targetVoiceNameContains);
        }
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

    private void initTTS() {
        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                isTtsInitialized = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED;

                if (isTtsInitialized) {
                    tts.setSpeechRate(0.7f);
                    tts.setPitch(1.0f);
                    changeVoice(villagerType.getRecommendedVoice());
                }
            } else {
                isTtsInitialized = false;
            }
        });
    }



    public void setBuilding(Building building) {
        this.building = building;
    }

    public void startConversation() {
        if (!isTalking) {
            conversation = MainActivity.getGeminiAPI().askGemini("pretend you are a villager and will talk to a player," +
                    "make some small talk, no more than 10 words, 2 lines, each max 12 letters.");
            if (conversation != null && !conversation.contains("Error") && !conversation.contains("error")) {
                isTalking = true;
                aniIndex = 0;
                splitConversation();
                speakConversation();
            }
        }
    }

    private void splitConversation() {
        String rawConversation = MainActivity.getGeminiAPI().askGemini(
                "pretens you are a villager and will talk to a player," +
                        " can you make some small talk with the player, no more then 10 words, just tell them something nice and friendly," +
                        "do it in 2 lines, and every line no more then 12 letters.."
        );

        if (rawConversation != null && rawConversation.contains("!")) {
            int splitIndex = rawConversation.indexOf("!") + 1;
            conversation = rawConversation.substring(0, splitIndex) + "\n" + rawConversation.substring(splitIndex).trim();
        } else
            conversation = rawConversation;
    }

    private void speakConversation() {
        if (isTtsInitialized && tts != null && conversation != null) {
            tts.speak(conversation.replace("\n", " "), TextToSpeech.QUEUE_FLUSH, null, "VillagerSpeech");
        }
    }

    public void endConversation() {
        isTalking = false;
        conversation = null;
        stopSpeaking();
    }

    public boolean isTalking() {
        return isTalking;
    }

    public void drawTalk(Canvas canvas, float cameraX, float cameraY) {
        if (conversation != null && (conversation.contains("Error") ||conversation.contains("failure") )) {
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

    private void stopSpeaking() {
        if (tts != null && tts.isSpeaking()) {
            tts.stop();
        }
    }


    public enum VillagerType {
        VILLAGER_DAD, VILLAGER_MOM, VILLAGER_BOY, VILLAGER_GREEN, VILLAGER_BLACK, VILLAGER_OLIVE;


        public String getRecommendedVoice() {
            return switch (this) {
                case VILLAGER_DAD, VILLAGER_GREEN -> "male";
                case VILLAGER_MOM -> "female";
                case VILLAGER_BOY -> "child";
                case VILLAGER_BLACK -> "deep";
                case VILLAGER_OLIVE -> "older";
            };
        }

        public Voice getVoiceFromCategory(TextToSpeech tts, String category) {
            for (Voice voice : tts.getVoices()) {
                String voiceName = voice.getName().toLowerCase();

                switch (category.toLowerCase()) {
                    case "male":
                        if (voiceName.contains("male") || voiceName.contains("x-") || voiceName.contains("en-us")) {
                            return voice;
                        }
                        break;
                    case "female":
                        if (voiceName.contains("female") || voiceName.contains("en-us")) {
                            return voice;
                        }
                        break;
                    case "child":
                        if (voiceName.contains("child") || voiceName.contains("yue")) {
                            return voice;
                        }
                        break;
                    case "deep":
                        if (voiceName.contains("deep") || voiceName.contains("x-dma")) {
                            return voice;
                        }
                        break;
                    case "older":
                        if (voiceName.contains("older") || voiceName.contains("x-imr")) {
                            return voice;
                        }
                        break;
                    default:
                        Log.d("VoiceSelection", "Unknown category: " + category);
                        return null;
                }
            }
            return null;
        }

    }
}
