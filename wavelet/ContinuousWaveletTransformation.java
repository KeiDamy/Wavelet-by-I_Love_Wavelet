package wavelet;

/**
 * 連続ウェーブレット変換(continuous wavelet tansformation)。ただし、未完成。
 */
public class ContinuousWaveletTransformation extends WaveletTransformation {
    /**
     * 連続ウェーブレット変換のインスタンスを作るコンストラクタ。
     */
    public ContinuousWaveletTransformation() {
        super();
    }

    /**
     * あるオブジェクト(anObject)に対して、この連続ウェーブレット変換を適用する。
     */
    public WaveletTransformation applyTo(Object anObject) {
        return this;
    }

    /**
     * あるウェーブレット変換(waveletTransformation)に対して、この連続ウェーブレット変換を施す。
     */
    public WaveletTransformation transform(WaveletTransformation waveletTransformation) {
        return this;
    }
}
