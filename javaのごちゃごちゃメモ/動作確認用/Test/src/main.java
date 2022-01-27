import java.util.HashMap;
import java.util.Map;

/**
 * <p>動作確認用にFizzBuzzをちょっと拡張したものを実装しておく<br>
 * 学校PCのVScodeで書いたのでEclipceと文字の感じが違って新鮮だった<br>
 * プラグインが多くて感動</p>
 * 
 * @author mossan
 * @version 1.0
 */



public class main {

	/**
	 * 割りたい値と。割り切れたときに出力したい値を格納
	 */
	private static Map<Integer, String>wordMap = new HashMap();

	/**
	 * 結果を格納（｛値：結果｝
	 */
	private static Map<Integer, String>ans = new HashMap<>();

	/**
	 * メインメソッド
	 * 
	 * @param args 未使用
	 */
	public static void main(String[] args) {

		//割りたい値と出力文字列wセット
		wordMap.put(3, "Fizz");
		wordMap.put(5, "Buzz");
		wordMap.put(6, "Foo");
		wordMap.put(7, "Ber");


		//計算の開始
		calcFizzBuzz(1, 100);

		
		//結果をすべて出力
		for(Integer key: ans.keySet()){
			System.out.println(key + ":" + ans.get(key));

		}

	}

	/**
	 * <p>FizzBuzzを実際に計算するメソッド</p>
	 * <p>あらかじめwordMapにキャッチする値とそれに充てる文字列を格納する必要がある<br>
	 * 解答はansに保存される</p>
	 * 
	 * @param START 計算を始める値を保持
	 * @param END どこまで計算するかの値を保持
	 */
	public static void calcFizzBuzz(final int START ,final int END) {
		
		for(int i = START ; i <= END; i++){
			
			//セットされたキー(Integer)をすべて引っ張ってくる
			for(Integer key: wordMap.keySet()){
				
				//割り切れた場合
				if (i % key == 0) {
					//ans.put(i)がnullの場合はnullをつぶして直接値を代入する
					//非nullの場合は前回の結果を結合して代入
					if(ans.get(i) == null){
						ans.put(i, wordMap.get(key));

					}else{
						ans.put(i, ans.get(i) + wordMap.get(key));

					}	
					
				//割り切れない場合
				}else{
					//ans.put(i)がnullの場合はつぶしておく
					if(ans.get(i) == null){
						ans.put(i, "");
					}

				}

			}

		}

	}

}
