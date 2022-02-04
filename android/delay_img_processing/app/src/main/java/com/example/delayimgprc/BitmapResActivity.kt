package com.example.delayimgprc

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.delayimgprc.databinding.ActivityBitmapResBinding

class BitmapResActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBitmapResBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBitmapResBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //null許容だけど、インテント受け渡し前にnull判定しているので、nullはあり得ない
        var uri: Uri? = intent.getParcelableExtra("uri")
        var originImg: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        var changeImg: Bitmap = Bitmap.createBitmap(originImg)

        binding.imageView3.setImageBitmap(originImg)

        grayScale(originImg)
    }

    private fun grayScale(originImg: Bitmap) {
        var changeImg: Bitmap = Bitmap.createBitmap(
            originImg.width,
            originImg.height,
            Bitmap.Config.ARGB_8888)


        val width: Int = changeImg.width
        val height: Int = changeImg.height


        var calcPix: Int
        var grayAve: Int

        //画像の高さループ
        for(j in 0..height - 1 step 1) {
            //画像の幅ループ
            for (i in 0..width - 1 step 1) {
                calcPix = originImg.getPixel(i, j)
                grayAve = (Color.red(calcPix) + Color.green(calcPix) + Color.blue(calcPix)) / 3

                changeImg.setPixel(i, j, Color.rgb(grayAve, grayAve, grayAve))

                binding.imageView3.setImageBitmap(changeImg)
            }
        }
    }
}