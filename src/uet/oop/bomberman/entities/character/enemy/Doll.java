package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Doll extends Enemy {

    public Doll(int x, int y, Board board) {
        super(x, y, board, Sprite.doll_dead, Game.getBomberSpeed() / 2, 100);

        _sprite = Sprite.doll_right1;

        _ai = new AIMedium(_board.getBomber(), this,_board);
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
                break;
            case 1:
                _sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, _animate, 60);
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
