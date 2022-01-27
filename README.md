# toybox
## 経緯
onedriveで同期させてやってたけど、
就活で提出が必要なところが案外多かったのと、せっかくなら授業のチーム以外でも使ってみよう、
ということで、今まで作ったやつ、かつ、ギリギリ動くやつをのっける！  

ローカルにあるプログラムも順次上げていきたい！

## ソーベルフィルタで画像の輪郭を抽出したいプログラム（Java）
オブジェクト指向を無理やり理解するためにがんばった。  
正直必要ないクラスも多いけど、ほへぇー、となったのでおっけーだと思う。  
  
アルゴリズム自体は配列の中から１個を選び、その周辺の３×３の９マスに対して順に演算をしてるだけなので簡単なもの。
　　
- [javadoc](https://mossan0706.github.io/toybox/image_exchange/javadoc/image_exchange/module-summary.html)  
- [ソースコード](https://github.com/mossan0706/toybox/tree/main/image_exchange/src/package1)  

<やりたいこと>
- コマンドラインだと味気ないのでアプリ化し、実際にピクセルの色が変わっていくところを動的に表示したい！
  
  
## FizzBuzzに少し柔軟性を持たせたプログラム（Java）
動作確認兼暇つぶしに作った。  
わかりやすさ重視のため、少しループに冗長的なところがある（(ﾟ∀ﾟ)）  
  
HashMapを使って３の倍数＝Fizz、５の倍数＝Buzzだけじゃなく、自由に設定できるようになってる。  
  
- [javadoc](https://mossan0706.github.io/toybox/java%E3%81%AE%E3%81%94%E3%81%A1%E3%82%83%E3%81%94%E3%81%A1%E3%82%83%E3%83%A1%E3%83%A2/%E5%8B%95%E4%BD%9C%E7%A2%BA%E8%AA%8D%E7%94%A8/Test/doc/main.html)
- [ソースコード](https://github.com/mossan0706/toybox/tree/main/java%E3%81%AE%E3%81%94%E3%81%A1%E3%82%83%E3%81%94%E3%81%A1%E3%82%83%E3%83%A1%E3%83%A2/%E5%8B%95%E4%BD%9C%E7%A2%BA%E8%AA%8D%E7%94%A8/Test/src)  

<やりたいこと>
- 無駄にマルチスレッドに対応させたい！
  
  
## マイク入力を受け取って音量を使って加工したいプログラム（JavaScript + HTML）
グループワーク中のものなので問題ない部分だけ！！！！  
完成形は左スピーカーから音を出す→音量をキャッチ→右スピーカーから音を出す→音量をキャッチ→音量差を測定する、といったもの。  
  
開発の都合上、同一生成ポリシーに引っかかるので、クラス分けされていないぐちゃぐちゃコードになっている_(:3」∠)_  
  
- [HTMLのデモ（動作確認ができるだけの簡単なもの）](https://mossan0706.github.io/toybox/volume_measurement/動作確認のためのHTML.html)
- [ソースコード](https://github.com/mossan0706/toybox/tree/main/volume_measurement)



