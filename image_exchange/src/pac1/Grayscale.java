/*動作方法
 * 以下のプログラムは選択した画像のグレースケール化である。
 *
 * 1.このファイルをパッケージ[pac1]直下に配置、
 * 	 もしくはpackage文を削除して、コンパイル、実行
 * 2.コマンドライン上で画像ファイルのパスを入力
 * 3.[./new.jpg]として保存される
 */

package pac1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Grayscale {

    private static File input_image;	//選択したファイルを格納（変換したい画像データ
    private static int width;			//正常に読み取った画像ファイルの幅（ｘ）を保持
    private static int height;			//正常に読み取った画像ファイルの高さ（ｙ)を保持
    //分割していたものをべた張りしたので、クラス変数の使い方が不気味だけどいったん無視

    //画像のINT_RGG形式への変換後、R（レット）、G（グリーン）、B（ブルー）に分割する
    public static int r(int request_bit){
        return (request_bit >> 16) & 0xff;
    }

    public static int g(int request_bit){
        return (request_bit >> 8) & 0xff;
    }

    public static int b(int request_bit){
        return request_bit & 0xff;
    }

    //グレースケール化の値を求める
    public static int pix_ave(int r, int g, int b) {
    	return (r + g + b)/3;
    }


	public static void main(String[] args) throws IOException{

		//画像読み取り処理の開始
		System.out.print("変換を行いたい画像のファイルパスを入力（相対パス可）"
				+ "\n入力：");

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
        String filepass = scan.next();

		input_image = new File(filepass);
		width = 0;
		height = 0;
		BufferedImage read = null;

		try {
			read = ImageIO.read(input_image);
			width = read.getWidth();
			height = read.getHeight();
		} catch (IOException e) {
			System.out.println("ファイルの読み取りが正常に完了しませんでした");
			System.exit(0);
		} catch (NullPointerException e) {
			System.out.println("無効なファイル形式です");
			System.exit(0);
		}

		BufferedImage exchange = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		//グレースケール化処理の開始
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {

				//操作するピクセルを指定
				int select_onepix = read.getRGB(x, y);

				//グレーの色を確定、代入
				int rgb_average = pix_ave(r(select_onepix),g(select_onepix),b(select_onepix));
				int red = rgb_average;
				int green = rgb_average;
				int blue = rgb_average;


				//1pixのINT_RGB形式に変換、代入
				int newcolor = (red << 16) + (green << 8) + blue;
				exchange.setRGB(x, y, newcolor);
			}
		}

		File output_image = new File("new.jpg");
		ImageIO.write(exchange, "jpg", output_image);

		System.out.println("new.jpgに保存しました");
	}
}
