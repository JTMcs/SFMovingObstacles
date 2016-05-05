//https://www.youtube.com/watch?v=ygpglRYcNS4&list=PLvnEciYMRSk-l5oKsIsbrBbP2ZJFSZLns
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen extends ApplicationAdapter implements InputProcessor{

	OrthographicCamera ocCam;
	Rectangle recBDown, recBUp, recBLeft, recBRight;
	SpriteBatch sbChar;
	Character character;
	Obstacle obstacle;
	int picID = 1, gdxW, gdxH;
	BitmapFont bmFont;
    boolean isTouchingL = false, isTouchingR = false;

	@Override
	public void create() {
		gdxW = Gdx.graphics.getWidth();
		gdxH = Gdx.graphics.getHeight();
		ocCam = new OrthographicCamera();
		ocCam.setToOrtho(false);
		sbChar = new SpriteBatch();
		character = new Character();
		character.setPosition(200, 100);
		obstacle = new Obstacle();
		System.out.println(gdxW + "" + gdxH);
		bmFont = new BitmapFont();
		bmFont.setColor(Color.WHITE);
		Gdx.input.setInputProcessor(this);

        recBDown = new Rectangle(0, 0, gdxW, 30);
        recBUp = new Rectangle(0, gdxH - 10, gdxW, 10);
        recBLeft = new Rectangle(0, 0, 10, gdxH);
        recBRight = new Rectangle(gdxW - 10, 0, 10, gdxH);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sbChar.setProjectionMatrix(ocCam.combined);
		sbChar.begin();
		if (picID == 1) {
			character.draw1(sbChar);
		} else {
			character.draw2(sbChar);
		}
		obstacle.draw(sbChar);
		bmFont.draw(sbChar, "Lives: " + Integer.toString(obstacle.nLives), gdxW - 100, gdxH - 50);
		sbChar.end();

		//Updates
		character.update(Gdx.graphics.getDeltaTime());

		//Boundaries
		if (character.bounds(recBDown) == 1) {
			character.action(1, 0, 30);
            character.isGrounded = true;
            character.nSpeed = 300; //ground speed
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                character.jump();
            }
		}

		else if (character.bounds(recBUp) == 1) {
			character.action(4, 0, gdxH - 10);
		}

		if (character.bounds(recBLeft) == 1) {
			character.action(2, 10, 0);
		}

		if (character.bounds(recBRight) == 1) {
			character.action(3, gdxW - 10, 0);
		}

		//Android Controls
        if (isTouchingL) {
            character.moveLeft(Gdx.graphics.getDeltaTime());
            picID = 2;
        }
        if (isTouchingR) {
            character.moveRight(Gdx.graphics.getDeltaTime());
            picID = 1;
        }
        
        //Keyboard Controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            character.moveLeft(Gdx.graphics.getDeltaTime());
            picID = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            character.moveRight(Gdx.graphics.getDeltaTime());
            picID = 1;
        }
        
		//Obstacle
		if (obstacle.bounds(character.recHB)) {
            System.out.println("fdgsdfgsdfg");
        }
	}

	@Override
	public void dispose() {
		sbChar.dispose();
		bmFont.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("1");
		if (screenX < gdxW / 2 && screenY > gdxH / 2) {
            isTouchingL = true;
		}
		if (screenX > gdxW / 2 && screenY > gdxH / 2) {
            isTouchingR = true;
		}
        if (screenY < gdxH / 2 && character.isGrounded == true) {
            character.jump();
        }

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		isTouchingL = false;
        isTouchingR = false;
        return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
