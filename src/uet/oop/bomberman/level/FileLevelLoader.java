package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;


import java.io.*;
import java.net.URL;
import java.util.Scanner;


public class FileLevelLoader extends LevelLoader {

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
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		LOAD_level_height_width_map(level);
	}
	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game
		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		for(int y = 0;y < _height;y++)
			for (int x = 0;x < _width;x++) {
				int pos = x + y * _width;
				switch (_map[y][x]) {
					case '#': //wall
						addWall(x,y,pos);
						break;

					case '*': //brick
						addBrick(x,y,pos);
						break;

					case 'x': //portal
						addPortal(x,y,pos);
						break;

					case 'p': //bomboer
						addBomber(x,y,pos);
						break;

					case '1': //balloon
						addBalloon(x,y,pos);
						break;

					case '2':
						addOnereal(x,y,pos);
						break;
					case '3':
						addDoll(x,y,pos);
						break;
					case '4':
						addMinvo(x,y,pos);
						break;
					case '5':
						addDoria(x,y,pos);
						break;

					case 'b': //bomb item
						addBombItem(x,y,pos);
						break;

					case 'f': //flame item
						addFlameItem(x,y,pos);
						break;

					case 's': //speed item
						addSpeedItem(x,y,pos);
						break;

					default: //grass
						addGrass(x,y,pos);
						break;
				}



			}

	}

	/**
	 * Ham bo sung
	 */
	public void LOAD_level_height_width_map(int level){
		try {

			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("levels/Level"+level+".txt").getFile());

			Scanner reader = new Scanner(file);
			//Read level, height and width of map.
			while (reader.hasNextInt()) {
				_level = reader.nextInt();
				_height = reader.nextInt();
				_width = reader.nextInt();
			}

			// Load map
			_map = new char[_height][_width];
			String line;
			int check = -1;
			while (reader.hasNextLine() && check <= _height) {
				line = reader.nextLine();
				if(check >= 0 && check < _height ){
					for (int i = 0;i < _width;i++){
						_map[check][i] = line.charAt(i);
					}
				}
				check++;
			}

			//Hien thi thong tin man choi tren man hinh console
			System.out.println("level: " + _level);
			System.out.println("height: " + _height);
			System.out.println("width: " + _width);
			for(char[] a : _map){
				System.out.println(a);
			}


			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi trong level_row_column_map");
		}
	}
	private void addWall(int x,int y,int pos){
		_board.addEntity(
				pos,
				new Wall(x,y,Sprite.wall)
		);
	}
	private void addBrick(int x,int y,int pos){
		_board.addEntity(pos,
				new LayeredEntity(x, y,
						new Grass(x, y, Sprite.grass),
						new Brick(x, y, Sprite.brick)
				)
		);
	}
	private void addPortal(int x,int y,int pos){
		_board.addEntity(
				pos,
				new LayeredEntity(x,y,
						new Grass(x,y,Sprite.grass),
						new Portal(x,y,Sprite.portal,_board),
						new Brick(x,y,Sprite.brick)
				)
		);
	}
	private void addBomber(int x,int y,int pos){
		int xBomber = x, yBomber = y;
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity( pos, new Grass(xBomber, yBomber, Sprite.grass));
	}
	private void addBalloon(int x,int y,int pos){
		int xBalloon = x, yBalloon = y;
		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xBalloon), Coordinates.tileToPixel(yBalloon) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(pos, new Grass(xBalloon, yBalloon, Sprite.grass));
	}
	private void addOnereal(int x,int y,int pos){
		int xOnereal = x, yOnereal = y;
		_board.addCharacter( new Oneal(Coordinates.tileToPixel(xOnereal), Coordinates.tileToPixel(yOnereal) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(pos, new Grass(xOnereal, yOnereal, Sprite.grass));
	}

	private void addDoria(int x,int y,int pos){
		int xOnereal = x, yOnereal = y;
		_board.addCharacter( new Doria(Coordinates.tileToPixel(xOnereal), Coordinates.tileToPixel(yOnereal) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(pos, new Grass(xOnereal, yOnereal, Sprite.grass));
	}

	private void addDoll(int x,int y,int pos){
		int xOnereal = x, yOnereal = y;
		_board.addCharacter( new Doll(Coordinates.tileToPixel(xOnereal), Coordinates.tileToPixel(yOnereal) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(pos, new Grass(xOnereal, yOnereal, Sprite.grass));
	}

	private void addMinvo(int x,int y,int pos){
		int xOnereal = x, yOnereal = y;
		_board.addCharacter( new Minvo(Coordinates.tileToPixel(xOnereal), Coordinates.tileToPixel(yOnereal) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(pos, new Grass(xOnereal, yOnereal, Sprite.grass));
	}

	private void addBombItem(int x,int y,int pos){
		_board.addEntity(pos,
				new LayeredEntity(x, y,
						new Grass(x ,y, Sprite.grass),
						new BombItem(x, y, Sprite.powerup_bombs)
				)
		);
	}
	private void addFlameItem(int x,int y,int pos){
		_board.addEntity(
				pos,
				new LayeredEntity(x,y,
						new Grass(x,y,Sprite.grass),
						new FlameItem(x,y,Sprite.powerup_flames)
				)
		);
	}
	private void addSpeedItem(int x,int y,int pos){
		_board.addEntity(pos,
				new LayeredEntity(x,y,
						new Grass(x,y,Sprite.grass),
						new SpeedItem(x,y,Sprite.powerup_speed),
						new Brick(x,y,Sprite.brick)
				)
		);
	}
	private void addGrass(int x,int y,int pos){
		_board.addEntity(
				pos,
				new Grass(x,y,Sprite.grass)
		);
	}

}
