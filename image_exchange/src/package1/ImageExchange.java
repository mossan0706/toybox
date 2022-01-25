package package1;

/**
 * メインプログラムです　こちらを実行して、画像処理を開始します。<br>
 *
 * <br>
 *  実行時は各クラスをパッケージ[package1]直下に配置するか、
 *  各クラスのパッケージ文を削除してからコンパイル、実行してください。<br>
 *  実行後、コマンドラインに入力の指示が出ますので、それに従って入力を完了しください。<br>
 * <br>
 *
 * 実行するためには以下のクラスが必要です<br>
 *
 * ・GetImage.java<br>
 * ・SavePng.java<br>
 * ・DisPixel.java<br>
 * ・IChangeControl.java<br>
 * ・GrayScale.java<br>
 * ・SobelFilter.java<br>
 * <br>
 * ＜参考＞<br>
 * クラス BufferedImage<br>
 *   https://docs.oracle.com/javase/jp/7/api/java/awt/image/BufferedImage.html
 * @author あおき
 *
 */
public class ImageExchange {

	/**
	 * 変換処理を行うメインプログラム
	 * @param args 使用なし
	 */
	public static void main(String args[]) {

		//画像ファイルの取り込み
		GetImage img = new GetImage();
		img.inputImage();

		//グレースケール化の実行
		GrayScale g= new GrayScale(img.getImage());
		g.change();
		//ソーベルフィルターの実行,グレースケール後の画像を放り込む
		SobelFilter s = new SobelFilter(g.getChange());
		s.change();


		SavePng saveG = new SavePng(g.getChange());
		saveG.save("グレースケール");

		SavePng saveS = new SavePng(s.getChange());
		saveS.save("ソーベルフィルタリング");

		System.out.println("プログラムを終了します");

	}
}
