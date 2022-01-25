package package1;

import java.awt.image.BufferedImage;

/**
 * 指定された画像のグレースケール化を行う<br>
 * <br>
 * ＜参考＞<br>
 * 画像情報の取得<br>
 * 　https://talavax.com/imagesize.html
 * <br>
 * グレースケール化<br>
 * 　https://mementoo.info/archives/1617
 * @author あおき
 */
public class GrayScale extends DisPixel implements IChangeControl{

	/**
	 * 変換前のBufferedImage型
	 */
	private BufferedImage img;

	/**
	 * 変換後のBufferedImage型
	 */
	private BufferedImage exchange;

	/**
	 * 引数の変換前の画像を格納する
	 * @param img 変換前のBufferedImage型
	 */
	GrayScale(BufferedImage img) {
		this.img = img;
	}

	/**
	 * 処理後の画像ゲッター
	 * @return exchange グレースケール化後のBufferedImage型
	 */
	@Override
	public BufferedImage getChange() {
		return this.exchange;
	}

	/**
	 * グレースケール化の処理を行う
	 */
	@Override
	public void change() {
		//変換後の画像保存用
		this.exchange = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

		//グレースケール化処理の開始
		tryMessage();

		for(int y = 0; y < img.getHeight(); y++) {
			for(int x = 0; x < img.getWidth(); x++) {

				//操作するピクセルを指定
				int select_onepix = img.getRGB(x, y);

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

		successMessage();

	}

	/**
	 * 処理の成功メッセージをコマンドライン上に行う
	 */
	@Override
	public void successMessage() {
		System.out.println("グレースケール化に成功しました\n");
	}

	/**
	 * 処理の開始メッセージをコマンドライン上に行う
	 */
	@Override
	public void tryMessage() {
		System.out.println("グレースケール化を開始します");
	}

}
