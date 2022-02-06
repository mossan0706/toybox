package com.example.delayimgprc

import android.graphics.Bitmap
import android.graphics.Color
import android.icu.number.IntegerWidth
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import com.example.delayimgprc.databinding.ActivityBitmapResBinding
import kotlin.properties.Delegates
import kotlin.collections.ArrayList as ArrayList1
import kotlin.lazy as lazy

class BitmapResActivity : AppCompatActivity() {
    /** バインディング */
    private lateinit var binding: ActivityBitmapResBinding

    //null許容だけど、インテント受け渡し前にnull判定しているので、nullはあり得ない
    /** メインアクティビティから受け取ったURIを保持 */
    private var  uri: Uri? = null

    /** 変換前の画像を保持 */
    private lateinit var originImg: Bitmap
    /** 変換後の画像を保持 */
    private lateinit var changeImg: Bitmap

    /** 遅延処理のためのハンドラーを保持 */
    private val handler = Handler()

    /** 分割数を保持 */
    private var split: Int = 10

    /** 処理中のy座標を保持 */
    private  var spList: MutableList<Int> = mutableListOf()


    /**
     * 遅延処理を実装する
     */
    private val runnable: Runnable by lazy {
        object: Runnable{
            override fun run() {

                for (y in 0 until spList.size){
                    //指定された部うかつ行だけのグレースケール化を実行
                    grayScale(originImg, spList[y])
                    spList[y]++
                }

                //イメージビューに挿入
                binding.imageView3.setImageBitmap(changeImg)

                //処理中のx座標が画像の高さ以下の場合、１ミリ秒ずらして上記処理を続ける
                if(spList[spList.size - 1] < changeImg.height){
                    handler.postDelayed(this, 1)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //バインディングの設定
        binding = ActivityBitmapResBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //受け取ったURIをBitmapに
        this.uri = intent.getParcelableExtra("uri")
        this.originImg = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

        //変換後の画像を保持するためのBitmapを生成
        this.changeImg = Bitmap.createBitmap(
            originImg.width,
            originImg.height,
            Bitmap.Config.ARGB_8888)

        //分割数を取得


        //画像高さを分割数で割り、処理開始位置を指定
        spList.add(0)

        for (i in 1 until split){
            spList.add( ( (originImg.height / split ) * i ).toInt() )
        }


        //ハンドラーに遅延処理させたいやつをセット
        handler.post(runnable)

        //メインアクティビティに戻るボタン
        binding.returnButton.setOnClickListener{
            finish()
        }
    }


    /**
     * Bitmap中、1行分のグレースケール化を行う
     *
     * @param originImg 変換前のBitmap画像
     * @param y 処理中のBitmapのx座標を保持
     */
    private fun grayScale(originImg: Bitmap, y: Int ) {

        val width: Int = changeImg.width
        var calcPix: Int
        var grayAve: Int

        //画像のx座標ループ
        for (x in 0 until width step 1) {
            calcPix = originImg.getPixel(x, y)
            grayAve = (Color.red(calcPix) + Color.green(calcPix) + Color.blue(calcPix)) / 3

            changeImg.setPixel(x, y, Color.rgb(grayAve, grayAve, grayAve))
        }

    }
}