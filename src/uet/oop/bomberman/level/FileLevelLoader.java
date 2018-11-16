package uet.oop.bomberman.level;

import javafx.scene.layout.CornerRadii;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.lang.invoke.SwitchPoint;
import java.util.Scanner;
import java.util.zip.CheckedOutputStream;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Em tu bo sung
	 */
	public int[] level_row_column_map(String levelFilePath){
		int[] data = new int[3];
		try {
			File file = new File(levelFilePath);
			Scanner reader = new Scanner(file);

			int index = 0;
			while (reader.hasNext() && index <= 2) {
				int i = reader.nextInt();
				data[index] = i;
				index++;
			}

			int _row = data[1];
			int _column = data[2];
			_map = new char[_row][_column];

			String line;
			int check = -1;
			while (reader.hasNextLine() && check <= data[1]){
				line = reader.nextLine();
				if(check >= 0 && check < _row ){
					for (int i = 0;i < _column;i++){
						_map[check][i] = line.charAt(i);
					}
				}
				check++;
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi trong level_row_column_map");
		}

		return data;
	}

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		int[] data;
		String localPath = "res/levels/Level" + level +  ".txt";
		data = level_row_column_map(localPath); // _map duoc cap nhat.

		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		_level = data[0];
		_height = data[1];
		_width = data[2];
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		for(int i = 0;i < _width;i++){
			for (int j = 0;j < _height;j++){
				switch (_map[i][j]){
					case '#':
						addWall(i,j);
						break;

					case '*':
						addBrick(i,j);
						break;

					case 'x':
						addPortal(i,j);
						break;

					case 'p':
						addBomber(i,j);
						break;

					case '1':
						addBalloon(i,j);
						break;

					case 'b':
						addBombItem(i,j);
						break;

					case 'f':
						addFlameItem(i,j);
						break;

					case 's':
						addSpeedItem(i,j);
						break;

					case ' ':
						addGrass(i,j);
						break;
				}
			}
		}
	}

	private void addWall(int x,int y){
		_board.addEntity(
				x + y * _width,
				new Wall(x,y,Sprite.wall)
		);
	}
	private void addBrick(int x,int y){
		int xB = x, yB = y;
		_board.addEntity(xB + yB * _width,
				new LayeredEntity(xB, yB,
						new Grass(xB, yB, Sprite.grass),
						new Brick(xB, yB, Sprite.brick)
				)
		);
	}
	private void addPortal(int x,int y){
		int pos = x + y * _width;
		_board.addEntity(
				pos,
				new LayeredEntity(x,y,
						new Grass(x,y,Sprite.grass),
						new Portal(x,y,Sprite.portal),
						new Brick(x,y,Sprite.brick)
				)
		);
	}
	private void addBomber(int x,int y){
		int xBomber = x, yBomber = y;
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
	}
	private void addBalloon(int x,int y){
		int xBalloon = x, yBalloon = y;
		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xBalloon), Coordinates.tileToPixel(yBalloon) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBalloon + yBalloon * _width, new Grass(xBalloon, yBalloon, Sprite.grass));
	}
	private void addBombItem(int x,int y){
		_board.addEntity(x + y * _width,
				new LayeredEntity(x, y,
						new Grass(x ,y, Sprite.grass),
						new BombItem(x, y, Sprite.powerup_bombs)
				)
		);
	}
	private void addFlameItem(int x,int y){
		_board.addEntity(
				x + y *_width,
				new LayeredEntity(x,y,
						new Grass(x,y,Sprite.grass),
						new FlameItem(x,y,Sprite.powerup_flames)
				)
		);
	}
	private void addSpeedItem(int x,int y){
		_board.addEntity(
				x + y * _width,
				new LayeredEntity(x,y,
						new Grass(x,y,Sprite.grass),
						new SpeedItem(x,y,Sprite.powerup_speed)
				)
		);
	}
	private void addGrass(int x,int y){
		_board.addEntity(
				x + y * _width,
				new Grass(x,y,Sprite.grass)
		);
	}

}
