package com.example.delayimgprc

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import com.example.delayimgprc.databinding.ActivityBitmapResBinding

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

    /** 処理中の画像のy座標を保持 */
    private var calcH = 0

    /**
     * 遅延処理を実装する
     */
    private val runnable = object: Runnable{
        override fun run() {

            grayScale(originImg, calcH)

            binding.imageView3.setImageBitmap(changeImg)

            if(changeImg.height > calcH){
                handler.postDelayed(this, 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapResBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.uri = intent.getParcelableExtra("uri")
        this.originImg = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)


        this.changeImg = Bitmap.createBitmap(
            originImg.width,
            originImg.height,
            Bitmap.Config.ARGB_8888)

        binding.imageView3.setImageBitmap(originImg)


        grayScale(originImg, this.calcH)
        this.calcH++

        handler.post(runnable)

        //メインアクティビティに戻るボタン
        binding.returnButton.setOnClickListener{
            finish()
        }
    }


    /**
     * グレースケール化を行う
     *
     * @param originImg 変換前のBitmap画像
     * @param y 処理中のBitmapのx座標を保持
     */
    private fun grayScale(originImg: Bitmap, y: Int ) {

        val width: Int = changeImg.width
        var calcPix: Int
        var grayAve: Int


        //画像の幅ループ
        for (x in 0 until width step 1) {
            calcPix = originImg.getPixel(x, y)
            grayAve = (Color.red(calcPix) + Color.green(calcPix) + Color.blue(calcPix)) / 3

            changeImg.setPixel(x, y, Color.rgb(grayAve, grayAve, grayAve))
        }

        this.calcH++
    }
}