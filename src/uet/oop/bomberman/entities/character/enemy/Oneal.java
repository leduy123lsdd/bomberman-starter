package uet.oop.bomberman.entities.character.enemy;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Oneal extends Enemy {
	
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed(), 200);
		
		_sprite = Sprite.oneal_left1;
		
		_ai = new AIMedium(_board.getBomber(), this,_board);
		_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
		}
	}
	@Override
	public void calculateMove(){
		int xa = 0, ya = 0;
		if(_ai.calculateDirection() == 1) { //right
			xa++;
		} else if (_ai.calculateDirection() == 3) { //left
			xa--;
		} else if (_ai.calculateDirection() == 2) { //down
			ya++;
		} else if(_ai.calculateDirection() == 0) { //up
			ya--;
		}
		if(xa != 0 || ya != 0)  {
			move(xa , ya);
			_moving = true;
		} else {
			_moving = false;
		}

		if(_board.getTime() % 4 == 0) {
			int xPixel = Coordinates.tileToPixel(getXTile());
			int yPixel = Coordinates.tileToPixel(getYTile() + 1);

			_x = xPixel;
			_y = yPixel;
		}

	}
}
