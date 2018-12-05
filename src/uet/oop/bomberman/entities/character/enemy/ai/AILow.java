package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;

import java.util.Random;

public class AILow extends AI {
	Board board;
	public AILow(Board b) {
		board = b;
	}
	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi

		if(board.getTime() % 2 ==0) {
			return 3;
		}
		return 1;

	}

}
