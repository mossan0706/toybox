/** memo
 * 同一資源生成ポリシーがブラウザ上にあるため、.jsファイルから.jsファイルを参照することができない
 * そのため、本ファイル（Valance.js）にメインメソッドからほぼすべてのクラスを並べて記述する
 * 
 * また、測定後の値に関しては、HTMLの値をそのままのこし、変数であるかのような利用方法を用いる
 * そのため、ＨＴＭＬのid要素設定が重要
 */


/**
 * innerHTMLをすっきりさせるために省略する用
 * 
 * id = nullの場合、挿入処理は行わない
 *
 * @param {String} id - 挿入先のid要素の値
 * @param {String} prm - 挿入する実際の値
 */
 function innerHtml(id, prm) {
    if (id != null) {
        document.getElementById(id).innerHTML = prm
    }
}

/**
 * HTML上のラジオボタンを操作する
 *
 * @param {int} radioId - HTML上に存在する同一name要素のラジオボタンの何番目に切り替えたいのかを示す値
 */
function radio(radioId) {

    //HTMLに存在するname = tab_itemラジオボタン要素をすべて保持
    let selectStep = document.querySelectorAll("input[name=tab_item]");

    //引数の順番のラジオボタンを有効化
    selectStep[radioId].checked = true;
    
}




/**
 * 変数監視のクラス<br>
 * 
 * プリミティブ型を監視する際は無理やりオブジェクト型にして、参照引き渡しする必要あり<br>
 * なお、監視のたびにタイマーが生成されるので、数万回以上の呼び出しには注意すること<br>
 *
 * ＜参考＞
 * https://risaki-masa.com/function-for-observing-variable-with-js/
 * @class Observation
 */
class Observation{


    /**
     * Creates an instance of Observation.
     * @param {Object} observation - 監視対象のオブジェクトを保持
     * @param {Function(Object)} [changeFunction=null] - 実行したい処理を引き渡したい場合のみ、引数に指定する
     * @memberof Observation
     */
    constructor(observation, changeFunction = null){

        this.INTERVAL = 1;
        
        /**
         *  監視中の変更前の値を保持
         *
         * @return {object} - 監視中のオブジェクトの変更前の値を返す 
         */
        this.onGet = function() { return observation.authenticity };
        
        /**
         *
         * @type {Function(Object)} - 値が変更されたときの処理を保持
         */
        this.onChanged = function() {

            calc();

        };

        this.observe( this.NTERVAL, this.onGet, this.onChanged );
    }

    /**
     *
     *
     * @param {int} interval - 値の監視間隔（ミリ秒）
     * @param {*} onGet - 現在保持している値
     * @param {*} onChanged - 変更後の値
     * @memberof Observation
     */
    observe( interval, onGet, onChanged ) {
        let previousValue   = onGet();
        const onObserve     = function() {
            const VALUE = onGet();
            if ( previousValue === VALUE ) return;

            onChanged( VALUE );
            previousValue = VALUE;
        };

        setInterval( onObserve, interval );
    }

}




//大域変数領域
/** @type {AudioContext} [] - オーディオコンテキストクラスを保持 */
let audioContext;

/** @type {AudioContext.createMediaStreamSource} - オーディオコンテキストクラスを保持 */
let mediaStreamSource = null;

/** @type {VoluneMath} - 取得した32bit音声情報を10進数に変換した値を保持 */
let meter = null;

/** @type {Observation} - 変数監視クラスのインスタンスを保持 */
let ins;

/** @type {String} - 計算中の結果の挿入先idを保持* /
let innerId = "";

/** @type {Object} - 処理のフラグ用　監視のためオブジェクト型にしているが、要はbooleanを保持したいだけ */
let isConnect = {authenticity: false};

/** @type {int} - 画面遷移等の時間管理用（ミリ秒） */
let timeControl = 0;




/**
 * メインメソッド　
 * 出力先の決定、変数の初期化、測定の開始を行う
 *
 * <参考>
 * @see https://g200kg.github.io/web-audio-api-ja/
 * @see https://tak-taniguchi.hatenablog.com/entry/2018/05/21/230536
 */
function main() {

    //変数監視のためのインスタンスを生成する
    //isConnect.authenticity = true , マイク入力が許可された場合
    //                       = false, マイク入力が拒否された場合
    if (ins == null) {
        ins = new Observation(isConnect);
    }

    maxVolume = 0;


    //オーディオコンテキストオブジェクトの生成と一回目の音声入力
    audioContext = new (window.AudioContext || window.webkitAudioContext)()

    //音声入力が可能かどうかをはんてい
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {

        try {
            //ブラウザ上の音声入力が可能だった場合（マイク入力有効化のポップアップが出る
            navigator.mediaDevices.getUserMedia({audio: true}).then((stream) => {

                console.log(stream);

                //入力処理のステータスをＨＴＭＬ上に挿入記述する
                innerHtml("test1", "音声取得中");

                innerId = "test2";

                //音量メーターを作成する
                meter = createAudioMeter(audioContext);

                //メディアストリームを取得
                mediaStreamSource = audioContext.createMediaStreamSource(stream);
                mediaStreamSource.connect(meter);

                //入力許可を受け取ったフラグを立て、計算処理を開始する
                isConnect.authenticity = true;
        
            }).catch(err => alert(err));
            
        } catch (error) {
            console.log("エラー")
        }
        
    }else {
        console.log("入力ソースが見つからない")
    }

}




/**
 * 計算結果の挿入。タブ変更処理を実装する
 * 変数監視プログラムから実行される
 *
 */
function calc() {

    timeControl = 5000;


    //一回目の音声入力停止処理
    setTimeout(() => {
        innerId = null;

        innerHtml("test1", "処理終了");

        console.log(1)
    }, timeControl);


    timeControl += 1000;

    //二回目の音声入力
    setTimeout(() => {
        maxVolume = 0;
        innerId = "test4";

        innerHtml("test3", "音声取得中");
        radio(2);
        console.log(2)
    }, timeControl);


    timeControl += 5000;

    //二回目の音声入力停止処理
    setTimeout(() => {
        processor.shutdown();

        innerHtml("test3", "処理終了");
        radio(3);
        console.log(3)
    }, timeControl);

    timeControl += 1000;

    //結果表示とアドバイス
    setTimeout(() => {
        result()
    }, timeControl);

    
}


/**
 * 結果表示処理を実装する
 *
 */
function result(){
    let nowVolL = Number(document.getElementById("test2").textContent);
    let nowVolR = Number(document.getElementById("test4").textContent);


    console.log(nowVolL - nowVolR)
    innerHtml("test5", Math.floor(nowVolL - nowVolR));

    if(nowVolL - nowVolR < -1000){
        innerHtml("test6", "右のボリュームが大きいようです");
    }else if(nowVolL - nowVolR > 1000){
        innerHtml("test6", "左のボリュームが大きいようです");
    }else{
        innerHtml("test6", "左右、均等の取れた音量です！");
    }
}



//--------------------------------------------------------------------------------------

/**
 * 音量メーターを生成
 *
 * 受け渡されたメディアストリーム情報を基に、メモリ上に32bitPCMファイル用の領域を確保する<br>
 * 確保後、volumeAudioProcess()を再帰的に呼び出すことで音声入力を継続的に受け取る<br>
 * 
 * <参考>
 * @see https://note.com/npaka/n/n2a6c19e85041
 *
 * @param {*} audioContext
 * @param {*} clipLevel
 * @param {*} averaging
 * @param {*} clipLag
 * @return {AoudioContext} - 変数自身を返すことにより、無限呼び出しを実装する
 */
function createAudioMeter(audioContext, clipLevel, averaging, clipLag) {
    processor = audioContext.createScriptProcessor(1024)
    processor.onaudioprocess = volumeAudioProcess
    processor.clipping = false
    processor.lastClip = 0
    processor.volume = 0
    processor.clipLevel = clipLevel || 0.98
    processor.averaging = averaging || 0.95
    processor.clipLag = clipLag || 750
  
    // chromeのバグ回避用　入力を直接出力する際のコード
    processor.connect(audioContext.destination)
  
    processor.checkClipping = function () {
        if (!this.clipping) {
            return false
        }
        if ((this.lastClip + this.clipLag) < window.performance.now()) {
            this.clipping = false
        }
        return this.clipping
    }
  
    //シャットダウン時に呼び出し
    processor.shutdown = function () {
      this.disconnect()
      this.onaudioprocess = null
    }
  
    return processor
}





/** @type {double} - 測定中の最大音量を保持 */
let maxVolume = 0;

/** @type {double} - 測定中の音量を保持 */
let volume = 0;

/**
 * 入力音声の計算を行う
 * 引数としてお0ディオバッファが必要であり、そこに書き込んだOCMデータを解析して数値化する
 * 
 * 計算結果はHTML上の指定されたid要素内にテキストとして出力される
 * 
 * オーディオコンテキストの仕様上、出力処理もこちらに書かないといけないため、
 * 本JSファイル内に定義された{innerStId}を基に処理を行う
 *
 *
 * @param {AudioBuffer} event - メモリ上に確保されたバッファ
 */
function volumeAudioProcess(event) {
    const buf = event.inputBuffer.getChannelData(0)
    const bufLength = buf.length
    let sum = 0
    let x
    
    //入力値に対して相加平均の2条の平方根をとる
    //まずは相加平均の2乗の処理
    for (var i = 0; i < bufLength; i++) {
        x = buf[i]
        if (Math.abs(x) >= this.clipLevel) {
            this.clipping = true;
            this.lastClip = window.performance.now();
        }
        sum += x * x;
    }
    
    //次に平方根の処理
    // ... then take the square root of the sum.
    const rms = Math.sqrt(sum / bufLength);
    
    //平均化係数により、取得した値を滑らかにする
    //メインスレッドで動作するため、最低限の処理能力hが必要
    //（処理が軽い奴はchromeでしか対応していないらしく、最近のPCなら大体この艇での処理は可能）
    volume = Math.max(rms, volume * this.averaging);


    //最大音量の更新処理
    //ＨＴＭＬ上に挿入記述を行う
    if(volume > maxVolume){
        maxVolume = volume;
        innerHtml(innerId, maxVolume * 100000);

    }

    console.log(volume);    

}

//--------------------------------------------------------------------------------------