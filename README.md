# Wavelet-by-I_Love_Wavelet
Java program that performs wavelet transforms. It is implemented by the MVC model.

# 実行方法
make testで実行してください。
M1 macのみ実行可能であることを確認しています。
また、テストを最後まで行えていないので実行できない可能性もあります。

# 実行内容
実行するとまずウェーブレット変換をお行った結果が出力される。
最初に表示される1Dは2次元上の点に対して左上が元データ、右上が圧縮結果、右下がスケーリング係数、左下が復元した結果となっている。

<img width="798" alt="1次元のウェーブレット変換" src="https://github.com/KeiDamy/Wavelet-by-I_Love_Wavelet/assets/65896722/4f0ce826-8985-4559-ae80-e5ae28cbaa93">
実際の出力画像

次に2Dが複数表示される、これらはそれぞれの画像の明るさ、赤青緑、それらを合わせた正しい色合いが出力されている。また、二次元上での場合をわかりやすく示すために点の集合の画像も出力される。これも1次元と同じように出力される

<img width="506" alt="2次元のウェーブレット変換（点の集合）" src="https://github.com/KeiDamy/Wavelet-by-I_Love_Wavelet/assets/65896722/6856596e-befb-4912-90ff-306f01448c3a">

<img width="509" alt="2次元のウェーブレット変換(赤色)" src="https://github.com/KeiDamy/Wavelet-by-I_Love_Wavelet/assets/65896722/c8bd47c4-765a-429c-9ae8-f10055fc0024">

<img width="506" alt="2次元のウェーブレット変換（正しい色）" src="https://github.com/KeiDamy/Wavelet-by-I_Love_Wavelet/assets/65896722/1e505e70-c14b-4a9d-80fe-2f41e848969b">

実行した時に最前面に出てくる2枚は自身でスケーリング係数を変更できるようになっている。
右下のスケーリング係数が操作できるパネルとなっている。付け加えたい部分をクリック、もしくは長押しすることによって適応される。その結果を左下のパネルが反映する。また、コントロールを押しながら実行した場合適応が解除され、０（初期値）となる。
また、それぞれ右クリックすることによってスケーリング係数を全て適応、全て初期化の操作を行うことができる（1Dは初期化のみ対応）。
さらに２Dは複数の画像に対応しており、右クリックで切り替えることができる。

<img width="797" alt="1Dの操作可能なパネル" src="https://github.com/KeiDamy/Wavelet-by-I_Love_Wavelet/assets/65896722/bd1bc303-f87c-4f6d-931b-45f954906c0d">

<img width="394" alt="2Dの操作可能なパネル" src="https://github.com/KeiDamy/Wavelet-by-I_Love_Wavelet/assets/65896722/31ea74ed-8935-4e0a-a558-3307309412ac">

