package wavelet;

/**
 * ウェーブレット変換の抽象クラス。
 * 連続ウェーブレット変換(continuous wavelet tansformation)と離散ウェーブレット変換(discrete wavelet transformation)を抽象する。
 */
public abstract class WaveletTransformation extends Object
{
	/**
	 * ウェーブレット変換のインスタンスを作るコンストラクタ。
	 */
	public WaveletTransformation()
	{
		super();
		this.initialize();
	}

	/**
	 * ウェーブレット変換を初期化する。
	 */
	protected void initialize()
	{
		return;
	}

	/**
	 * あるオブジェクト(anObject)に対して、このウェーブレット変換を適用するための抽象メソッド。
	 */
	public abstract WaveletTransformation applyTo(Object anObject);

	/**
	 * あるウェーブレット変換(waveletTransformation)に対して、このウェーブレット変換を施すための抽象メソッド。
	 */
	public abstract WaveletTransformation transform(WaveletTransformation waveletTransformation);
}
