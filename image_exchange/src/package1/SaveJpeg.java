package package1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * BufferedImageをjpegで保存する<br>
 * ファイルパスをコマンドライン上に入力する形で実施する
 * @author mossan
 */
public class SaveJpeg {

	/**
	 * 保存したいBufferedImage
	 */
	private BufferedImage img;

	/**
	 * @param img 保存したいBufferedImage型
	 */
	SaveJpeg(BufferedImage img) {
		this.img = img;
	}

	/**
	 * 保存を実行する
	 * @param control_name コマンドライン上にどの処理画像を保存するのかを出力するための文字列
	 */
	public void save (String control_name) {

		System.out.print(control_name + "画像の保存先を入力（相対パス可,拡張子入力不要）\n"
				+ "例）../output ⇒ 一つ上のディレクトリに「output.jpg」として保存\n"
				+ "入力＞");

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
	    String filepass = scan.next();

		File output_image = new File(filepass + ".png");

		try {
			ImageIO.write(img, "png", output_image);
			System.out.println("保存に成功しました\n");
		} catch (IOException e) {
			System.out.println("保存が正常に完了しませんでした");
		}

	}
}
