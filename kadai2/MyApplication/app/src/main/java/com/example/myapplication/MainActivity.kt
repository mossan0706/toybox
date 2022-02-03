package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.myapplication.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val RECORD_REQUEST_CODE = 1000
    private val REQUEST_GALLERY_TAKE = 2

    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //メディアへのアクセス許可を取得
        setupPermissions()

        //ラジオボタンが選択された時のテキスト変更
        binding.radioGroup.setOnCheckedChangeListener{ _, checkedId ->
            when (checkedId){
                R.id.image -> {
                    binding.startButton.text = "画像を取り込む"
                }
                R.id.photo -> {
                    binding.startButton.text = "写真をとる"
                }
                R.id.startExchange -> {
                    binding.startButton.text = "加工開始！！"
                }
            }
        }

        //選択されたラジオボタンの処理に従って、処理を開始する
        binding.startButton.setOnClickListener{
            when(binding.radioGroup.checkedRadioButtonId){
                R.id.image -> {
                    imageChange()
                }
                R.id.photo -> {
                    photoChange()
                }
                R.id.startExchange -> {
                    startChange()
                }
            }
        }

    }

    /**
     * 画像加工のため、別画面に遷移する
     */
    private fun startChange() {
        if (::uri.isInitialized){
            val intent = Intent(applicationContext, BitmapResActivity::class.java)
            intent.putExtra("uri", uri)
            startActivity(intent)
        } else {
            //エラーメッセージ
            Toast.makeText(
                applicationContext,
                "まず、加工したい画像を選ぼう！！！",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    /**
     * 端末内のメディアからの画像取り込み処理を実装する
     */
    private fun imageChange() {
        //ギャラリーに画面を遷移するためのIntent
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        this.startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }

    /**
     * 写真撮影処理を実装する
     */
    private fun photoChange() {
        TODO("Not yet implemented")
    }

    /**
     * 帰ってきたインテントからの画像をイメージビューに挿入
     * bitmapに保持
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            uri = data?.data!!

            when (requestCode) {
                2 -> {
                    if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY_TAKE) {
                        //選択された写真にImageViewを変更
                        binding.imageView.setImageURI(uri)
                    }

                }
            }
        } catch (e: Exception) {
            //エラーメッセージ
            Toast.makeText(
                applicationContext,
                "画像読み込みが完了しませんでした。${e}",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    //-----------------------------------------------------------
    //メディア権限許可系！

    /**
     * パーミッションのチェックを設定するためのメソッド
     * @see
     */
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    /**
     * パーミッションのリクエストを投げるメソッド
     */
    private fun makeRequest(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE
        )
    }

    /**
     *
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            RECORD_REQUEST_CODE ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "アクセスが許可されませんでした。", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(applicationContext, "アクセスが許可されました。", Toast.LENGTH_SHORT).show()

                }
                return
            }
        }
    }
}