

public class main {

	public static void main(String[] args) {
		
		//どの数まで実行するか
		int end = 100;
		
		for(int i = 1; i <= end; i++) {
			
			//連結文字列
			String answer = "";
			
			//３で割り切れる場合
			if (i % 3 == 0) {
				answer += "Fizz";
				
			}
			
			//５で割り切れる場合
			if (i % 5 == 0) {
				answer += "Buzz";
				
			}
			
			System.out.println(i + answer);
		}

	}

}
