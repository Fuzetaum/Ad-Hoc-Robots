package com.turningpoint.adhocbots.engine.render.model;

public class Sprite {
    private float leftCoordinate;
    private float rightCoordinate;
    private float topCoordinate;
    private float bottomCoordinate;
    private String textureName;

    public Sprite(float leftCoordinate, float rightCoordinate, float topCoordinate, float bottomCoordinate, String textureName) {
        this.leftCoordinate = leftCoordinate;
        this.rightCoordinate = rightCoordinate;
        this.topCoordinate = topCoordinate;
        this.bottomCoordinate = bottomCoordinate;
        this.textureName = textureName;
    }

    public float getLeftCoordinate() {
        return leftCoordinate;
    }

    public void setLeftCoordinate(float leftCoordinate) {
        this.leftCoordinate = leftCoordinate;
    }

    public float getRightCoordinate() {
        return rightCoordinate;
    }

    public void setRightCoordinate(float rightCoordinate) {
        this.rightCoordinate = rightCoordinate;
    }

    public float getTopCoordinate() {
        return topCoordinate;
    }

    public void setTopCoordinate(float topCoordinate) {
        this.topCoordinate = topCoordinate;
    }

    public float getBottomCoordinate() {
        return bottomCoordinate;
    }

    public void setBottomCoordinate(float bottomCoordinate) {
        this.bottomCoordinate = bottomCoordinate;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }
}
