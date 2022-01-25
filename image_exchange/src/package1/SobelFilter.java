package package1;

import java.awt.image.BufferedImage;

/**
 * 指定された画像のソーベルフィルタによるエッジ検出を行う<br>
 * <br>
 * ＜参考＞<br>
 * ソーベルフィルタ<br>
 * 　https://pgsample.info/image/filter/sobel.html
 * @author mossan
 */
public class SobelFilter extends DisPixel implements IChangeControl {

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
	SobelFilter(BufferedImage img) {
		this.img = img;
	}

	/**
	 * 処理後の画像ゲッター
	 * @return exchange ソーベルフィルタリング後のBufferedImage型
	 */
	@Override
	public BufferedImage getChange() {
		return this.exchange;
	}

	/**
	 * ソーベルフィルタの処理を行う
	 */
	@Override
	public void change() {
		//変換後の画像保存用
		this.exchange = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

		//ソーベルフィルターの開始
		tryMessage();

		//x軸方向のフィルター
		int x_Filter[][] = {{ -1,  0,  1 },
						    { -2,  0,  2 },
						    { -1,  0,  1 }};
		//y軸方向のフィルター
		int y_Filter[][] = {{ -1, -2, -1 },
							{  0,  0,  0 },
							{  1,  2,  1 }};

		//注目画素の取得用ループ
		for(int y = 0; y < img.getHeight(); y ++) {
			for(int x = 0; x < img.getWidth(); x++) {

				//外周は演算の対象外とする
				if(y == 0 || y == img.getHeight() - 1 || x == 0 || x == img.getWidth() - 1) {
					int newcolor = (255 << 16) + (255 << 8) + 255;
					exchange.setRGB(x, y, newcolor);
					continue;
				}

				//積和の初期化
				int x_multiply = 0;
				int y_multiply = 0;

				//注目画素周辺の３×３の取得、積和計算のループ
				for(int k = -1; k <= 1; k++ ) {
					for (int l = -1; l <= 1; l++) {

						//x軸のフィルタリング
						x_multiply += g(img.getRGB(x + l, y + k)) * x_Filter[l + 1][k + 1];
						//y軸のフィルタリング
						y_multiply += g(img.getRGB(x + l, y + k)) * y_Filter[l + 1][k + 1];

					}
				}

				//閾値として画素値を設定する（１～２５４）
				//ここの値を変えると、エッジ検出の感度が変わる
				if(x_multiply > 25 || y_multiply > 25) {
					int newcolor = (255 << 16) + (255 << 8) + 255;
					exchange.setRGB(x, y, newcolor);
				}else {
					int newcolor = (0 << 16) + (0 << 8) + 0;
					exchange.setRGB(x, y, newcolor);
				}

			}
		}

		successMessage();

	}

	/**
	 * 処理の成功メッセージをコマンドライン上に行う
	 */
	@Override
	public void successMessage() {
		System.out.println("ソーベルフィルタリングに成功しました\n");
	}

	/**
	 * 処理の開始メッセージをコマンドライン上に行う
	 */
	@Override
	public void tryMessage() {
		System.out.println("ソーベルフィルタリングを開始します");
	}

}
