package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {
	Board board;

	public Portal(int x, int y, Sprite sprite, Board b) {
		super(x, y, sprite);
		board = b;
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
		if(isRemoved()) {
			return true;
		}

		if(e instanceof Bomber && board.detectNoEnemies()) {
			remove();
			board.nextLevel();
		}
		return false;
	}

}
