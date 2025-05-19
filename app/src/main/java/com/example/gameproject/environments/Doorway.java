package com.example.gameproject.environments;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameproject.entities.Entity;

public class Doorway extends Entity {

    private final GameMap gameMapLocatedIn;
    private boolean active = true;
    private Doorway doorwayConnectedTo;
    private final PointF doorwayPoint;

    public Doorway(PointF doorwayPoint, GameMap gameMapLocatedIn) {
        super(doorwayPoint,1,1);
        this.doorwayPoint = doorwayPoint;
        this.gameMapLocatedIn = gameMapLocatedIn;
        gameMapLocatedIn.addDoorway(this);
    }

    public void connectDoorway(Doorway destinationDoorway) {
        this.doorwayConnectedTo = destinationDoorway;
    }

    public Doorway getDoorwayConnectedTo() {
        return doorwayConnectedTo;
    }

    public boolean isPlayerInsideDoorway(RectF playerHitbox, float cameraX, float cameraY) {
        return playerHitbox.contains(doorwayPoint.x + cameraX, doorwayPoint.y + cameraY);
    }

    public boolean isDoorwayActive() {
        return active;
    }

    public void setDoorwayActive(boolean active) {
        this.active = active;
    }

    public PointF getPosOfDoorway() {
        return doorwayPoint;
    }

    public GameMap getGameMapLocatedIn() {
        return gameMapLocatedIn;
    }
}
