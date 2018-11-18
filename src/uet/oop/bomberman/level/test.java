package uet.oop.bomberman.level;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {

    public static char[][] _map;
    private  int _height,_width,_level;

    public  void level_row_column(String levelFilePath){
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

            this._level = data[0];
            this._height = data[1];
            this._width = data[2];

            int _row = _height;
            int _column = _width;
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
                System.out.println();
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test x = new test();
        String path = "res/levels/Level" + 1 + ".txt";
        x.level_row_column(path);
        for (int i = 0;i< x._height;i++){
            String a = "";
            for(int j = 0;j < x._width;j++)
                a = a + _map[i][j];
            System.out.println(a);
        }
    }
}
