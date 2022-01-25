package package1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * 画像処理を行いたいファイルを読み込むためのクラスです。<br>
 * BufferedImage型が対応するすべてのファイルを読み込みます。<br>
 *<br>
 *＜参考＞<br>
 *Imageクラスの読み込み<br>
 *　https://www.ne.jp/asahi/hishidama/home/tech/java/image.html
 *<br>
 *BufferedImageの使い方<br>
 *　http://kamifuji.dyndns.org/JSupport/JAVA_Java2D/Java2DTest/index.html
 *
 * @author あおき
 */
public class GetImage {

	/**
	 * 読み込んだ画像ファイルを格納
	 */
	private BufferedImage read = null;

	/**
	 * 読み込んだ画像ファイルのゲッター
	 * @return read 読み込んだ画像ファイル
	 */
	public BufferedImage getImage() {
		return this.read;
	}


	/**
	 * 画像ファイルの読み込みを行う
	 * コマンドライン上ににファイルパスを入力する形で実施する
	 */
	public void inputImage() {
		//画像読み取り処理の開始
		System.out.print("変換を行いたい画像のファイルパスを入力（相対パス可）\n"
				+ "例）../input.jpg ⇒ 一つ上のディレクトリの「input.jpg」を読込\n"
				+ "入力＞");

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
	    String filepass = scan.next();

		File input_image = new File(filepass);

		try {
			read = ImageIO.read(input_image);
		} catch (IOException e) {
			System.out.println("ファイルの読み取りが正常に完了しませんでした\n"
					+ "存在しないファイルパスである可能性が高いです");
			System.exit(0);
		} catch (NullPointerException e) {
			System.out.println("無効なファイル形式です");
			System.exit(0);
		}

		System.out.println();

	}
}
