package package1;

import java.awt.image.BufferedImage;

/**
 * 変換処理を行うクラスが実装する物を記述したインタフェイス
 * @author あおき
 */
public interface IChangeControl{
	/**
	 * java内へ取り込みの済んだBufferedImageを保持した型
	 */
	public static final BufferedImage img = null;

	/**
	 * 変換後のBufferedImage保持用
	 */
	public static final BufferedImage exchange = null;

	/**
	 * 変換後のBufferedImageゲッター
	 * @return 変換後のBufferedImage型
	 */
	public abstract BufferedImage getChange();

	/**
	 * 変換の成功メッセージ出力用
	 */
	public abstract void successMessage();

	/**
	 * 変換の挑戦メッセージ出力用
	 */
	public abstract void tryMessage();

	/**
	 * 変換処理の実装
	 */
	public abstract void change();
}
