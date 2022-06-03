package com.proyectoxcliente.game.animaciones;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animacion {

	    private Array<TextureRegion> frames;
	    private float maxFrameTime;
	    private float estadoActual;
	    private int frameCount;
	    private int frame = 0;

	    public Animacion(TextureRegion region, int frameCount, float tiempoTotal) {
	        frames = new Array<TextureRegion>();
	        int frameWidth = region.getRegionWidth() / frameCount;

	        for (int i = 0; i < frameCount; i++) {
	            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
	        }
	        this.frameCount = frameCount;
	        maxFrameTime = tiempoTotal / frameCount;
	    }



	    public void update(float dt) {
	        estadoActual += dt;
	        if (estadoActual > maxFrameTime) {
	            frame++;
	            estadoActual = 0;
	        }
	        if (frame >= frameCount) {
	            frame = 0;
	        }
	    }

	    public TextureRegion getFrame() {
	        return frames.get(frame);
	    }
	}

