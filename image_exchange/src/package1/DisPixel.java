package package1;

/**
 * 画像のINT_RGG形式をR（レット）、G（グリーン）、B（ブルー）に分割
 * <br><br>
 * ＜参考＞<br>
 * TYPE_INT_RGBの取扱い方<br>
 * 　https://nodamushi.hatenablog.com/entry/20111012/1318436587
 * @author あおき
 */
public class DisPixel {
	/**
	 * TYPE_INT_RGBの画像から1画素のRを抜き出す
	 * @param request_bit 指定された1画素のRの画素値
	 * @return 1画素のRGBのR　int型
	 */
	public int r(int request_bit){
        return (request_bit >> 16) & 0xff;
    }

	/**
	 * TYPE_INT_RGBの画像から1画素のGを抜き出す
	 * @param request_bit 指定された1画素のGの画素値
	 * @return 1画素のRGBのG　int型
	 */
    public int g(int request_bit){
        return (request_bit >> 8) & 0xff;
    }

    /**
	 * TYPE_INT_RGBの画像から1画素のBを抜き出す
	 * @param request_bit 指定された1画素のBの画素値
	 * @return 1画素のRGBのB　int型
	 */
    public int b(int request_bit){
        return request_bit & 0xff;
    }

    /**
     * グレースケール化の値を求める
     * @param r 1画素のRの画素値
     * @param g 1画素のGの画素値
     * @param b 1画素のBの画素値
     * @return 1画素のRGBの平均値
     */
    public int pix_ave(int r, int g, int b) {
    	return (r + g + b)/3;
    }
}
