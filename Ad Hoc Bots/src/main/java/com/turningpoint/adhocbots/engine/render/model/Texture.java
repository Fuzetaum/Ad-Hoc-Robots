package com.turningpoint.adhocbots.engine.render.model;

import java.nio.ByteBuffer;

public class Texture {
    private int id;
    private int width;
    private int height;
    private ByteBuffer byteBuffer;

    public Texture(int id, int width, int height, ByteBuffer byteBuffer) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.byteBuffer = byteBuffer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
}
