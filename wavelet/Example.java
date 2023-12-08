package wavelet;

/**
 * ウェーブレット変換の例題プログラム。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Example extends Object
{
	/**
	 * 離散ウェーブレット変換の例題プログラム群を実行する。
	 */
	public static void main(String[] arguments)
	{
		// 離散ウェーブレット1次元変換の例題プログラムを実行する。
		Example1d.main(arguments);

		// 離散ウェーブレット2次元変換の例題プログラムを実行する。
		Example2d.main(arguments);

		// 離散ウェーブレット1次元変換のモデルを開く。
		(new Wavelet1dModel()).open();

		// 離散ウェーブレット2次元変換のモデルを開く。
		(new Wavelet2dModel()).open();

		return;
	}
}
