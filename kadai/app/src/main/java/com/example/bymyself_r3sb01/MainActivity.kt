package com.example.bymyself_r3sb01

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bymyself_r3sb01.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val RECORD_REQUEST_CODE = 1000
    private val REQUEST_GALLERY_TAKE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //メディアへのアクセス許可を取得
        setupPermissions()

        //ラジオボタンが選択された時のテキスト変更
        binding.radioGroup.setOnCheckedChangeListener{group, checkedId ->
            when (checkedId){
                R.id.image -> {
                    binding.startButton.text = "画像を取り込む"
                }
                R.id.photo -> {
                    binding.startButton.text = "写真をとる"
                }
            }
        }

        //選択されたラジオボタンの処理に従って、処理を開始する
        binding.imageView.setOnClickListener{
            when(binding.radioGroup.checkedRadioButtonId){
                R.id.image -> {
                    imageCange()
                }
                R.id.photo -> {
                    photoCange()
                }
            }
        }


    }


    /**
     * 端末内のメディアからの画像取り込み処理を実装する
     */
    private fun imageCange() {
        //ギャラリーに画面を遷移するためのIntent
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        this.startActivityForResult(intent, REQUEST_GALLERY_TAKE)
    }

    /**
     * 写真撮影処理を実装する
     */
    private fun photoCange() {
        TODO("Not yet implemented")
    }

    // onActivityResultにイメージ設定
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    //選択された写真にImageViewを変更
                    binding.imageView.setImageURI(data?.data)
                }
            }
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
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )

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
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
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