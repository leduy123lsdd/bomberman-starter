package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.level.Coordinates;

public class AIMedium extends AI {
	/**
	 * _bomber: đối tượng bomber trên bản đồ.
	 * _e : đối tượng enemy trên bản do
	 */
	Bomber _bomber;
	Enemy _e;
	Board b;
	
	public AIMedium(Bomber bomber, Enemy e, Board board) {
		_bomber = bomber;
		_e = e;
		b = board;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi


		/**
		 * Nếu hàm constructor truyền vào bomber null,
		 * tức là không có bomber thì sẽ trả về vị trí của enemy là null.
		 */
		if (_bomber == null) return random.nextInt(4);

		double xB = _bomber.getXTile(), yB = _bomber.getYTile();
		double xE = _e.getXTile(), yE = _e.getYTile();

		int randomNumber = random.nextInt(2);

		if(randomNumber == 1) {
			if(rowDirect() != -1) return rowDirect();
			else return columnDirect();
		} else {
			if(columnDirect() != -1) return columnDirect();
			return rowDirect();
		}
	}
	private int columnDirect() {
		if(_bomber.getXTile() < _e.getXTile()) {
			return 3;
		}
		if (_bomber.getXTile() > _e.getXTile()) {
			return 1;
		}
		return -1;
	}

	private int rowDirect() {
		if(_bomber.getYTile() < _e.getYTile())
			return 0;
		if(_bomber.getYTile() > _e.getYTile())
			return 2;
		return -1;
	}


}
