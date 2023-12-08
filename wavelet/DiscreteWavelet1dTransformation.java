package wavelet;

import java.util.stream.IntStream;

/**
 * 離散ウェーブレット1次元変換(discrete wavelet 1D transformation)。
 */
public class DiscreteWavelet1dTransformation extends DiscreteWaveletTransformation {
    /**
     * 元データを保持するフィールド。
     */
    protected double[] sourceCoefficients;

    /**
     * スケーリング係数を保持するフィールド。
     */
    protected double[] scalingCoefficients;

    /**
     * ウェーブレット展開係数を保持するフィールド。
     */
    protected double[] waveletCoefficients;

    /**
     * スケーリング係数とウェーブレット展開係数から逆ウェーブレット変換を施して再構成したデータ(元データと同等となるデータ)を保持するフィールド。
     */
    protected double[] recomposedCoefficients;

    /**
     * ユーザーが操作するためのウェーブレット展開係数を保持するフィールド。
     */
    protected double[] interactiveCoefficients;

    /**
     * 元データを指定して離散ウェーブレット1次元変換のインスタンスを作るコンストラクタ。
     * 通常、順ウェーブレット変換(元データからスケーリング係数とウェーブレット展開係数を求めるため)に用いる。
     */
    public DiscreteWavelet1dTransformation(double[] sourceCollection) {
        super();
        this.sourceCoefficients(sourceCollection);
    }

    /**
     * スケーリング係数とウェーブレット展開係数を指定して離散ウェーブレット1次元変換のインスタンスを作るコンストラクタ。
     * 通常、逆ウェーブレット変換(スケーリング係数とウェーブレット展開係数から元データを求めるため)に用いる。
     */
    public DiscreteWavelet1dTransformation(double[] scalingCollection, double[] waveletCollection) {
        super();
        
        this.scalingCoefficients(scalingCollection);
        this.waveletCoefficients(waveletCollection);
        this.interactiveCoefficients();
    }

    /**
     * <a href="http://en.wikipedia.org/wiki/Ingrid_Daubechies">イングリッド・ドブシー(Ingrid
     * Daubechies)</a>さんの<a href=
     * "http://en.wikipedia.org/wiki/Daubechies_wavelet">ウェーブレット列(N=2〜4)</a>を用いて、離散ウェーブレット変換を初期化する。
     * Ingrid Daubechies, "The wavelet transform, time-frequency localization and
     * signal analysis", IEEE Transaction on Information Theory, vol.36,
     * pp.961-1005, Sep 1990.
     */
    protected void initialize() {
        super.initialize();
        sourceCoefficients = null;
        scalingCoefficients = null;
        waveletCoefficients = null;
        recomposedCoefficients = null;
        interactiveCoefficients = null;
    }

    /**
     * スケーリング係数とウェーブレット展開係数から逆ウェーブレット変換を施して再構成したデータを応答する。
     */
    public double[] recomposedCoefficients() {
        if (recomposedCoefficients == null) {
            this.computeRecomposedCoefficients();
        } else {
            this.reComputeRecomposedCoefficients();
        }
        return recomposedCoefficients;
    }

    /**
     * スケーリング係数を応答する。
     */
    public double[] scalingCoefficients() {
        if (scalingCoefficients == null) {
            this.computeScalingAndWaveletCoefficients();
        }
        return scalingCoefficients;
    }

    /**
     * スケーリング係数を設定する。
     */
    public void scalingCoefficients(double[] scalingCollection) {
        scalingCoefficients = scalingCollection;
        recomposedCoefficients = null;
    }

    /**
     * 元データを応答する。
     */
    public double[] sourceCoefficients() {
        return sourceCoefficients;
    }

    /**
     * ユーザーが操作するための係数を応答する。
     * もし、interactiveCoefficientsの値が設定されていなければ初期値を設定する。
     * 
     * @return ユーザーが操作するための係数
     */
    public double[] interactiveCoefficients() {
        if (interactiveCoefficients == null) {
            this.computeInteractiveCoefficients();
        }
        return interactiveCoefficients;
    }

    /**
     * ユーザーが操作するための係数を設定する。
     * @param interactiveCollection 対象配列
     * @return 計算後の配列
     */
    public double[] interactiveCoefficients(double[] interactiveCollection) {
        interactiveCoefficients = interactiveCollection;
        reComputeRecomposedCoefficients();
        return interactiveCoefficients;
    }

    /**
     * 元データを設定する。
     * @param valueCollection 
     */
    public void sourceCoefficients(double[] valueCollection) {
        sourceCoefficients = valueCollection;
        scalingCoefficients = null;
        recomposedCoefficients = null;
        interactiveCoefficients = null;
    }

    /**
     * ウェーブレット展開係数を応答する。
     */
    public double[] waveletCoefficients() {
        if (waveletCoefficients == null) {
            this.computeScalingAndWaveletCoefficients();
        }
        return waveletCoefficients;
    }

    /**
     * ウェーブレット展開係数を設定する。
     * @param waveletCollection 
     */
    public void waveletCoefficients(double[] waveletCollection) {
        waveletCoefficients = waveletCollection;
        recomposedCoefficients = null;
    }

    /**
     * あるオブジェクト(anObject)に対して、この離散ウェーブレット変換を適用する。
     * @param anObject 対象オブジェクト
     */
    public WaveletTransformation applyTo(Object anObject) {
        if (anObject instanceof double[] == false) {
            throw new IllegalArgumentException("anObject must be a double[].");
        }
        this.sourceCoefficients((double[]) anObject);
        this.scalingCoefficients();
        this.waveletCoefficients();
        return this;
    }

    /**
     * あるウェーブレット変換(waveletTransformation)に対して、この離散ウェーブレット変換を施す。
     * @param waveletTransformation 対象ウェーブレット変換
     * @return 計算後のウェーブレット変換
     */
    public WaveletTransformation transform(WaveletTransformation waveletTransformation) {
        if (waveletTransformation instanceof DiscreteWavelet1dTransformation == false) {
            throw new IllegalArgumentException("waveletTransformation must be a DiscreteWavelet1dTransformation.");
        }
        DiscreteWavelet1dTransformation discreteWavelet1dTransformation = (DiscreteWavelet1dTransformation) waveletTransformation;
        double[] coefficients = discreteWavelet1dTransformation.sourceCoefficients();
        if (coefficients == null) {
            coefficients = discreteWavelet1dTransformation.recomposedCoefficients();
        }
        return new DiscreteWavelet1dTransformation(coefficients);
    }

    /**
     * スケーリング係数とウェーブレット展開係数から再構成したデータ(元データと同等となるデータ)を計算する。
     */
    protected void computeRecomposedCoefficients()
	{
		if (scalingCoefficients == null) { return; }
		if (waveletCoefficients == null) { return; }
		int size = scalingCoefficients.length;
		recomposedCoefficients = new double[size * 2];
		int offset = Math.max(1024, size);
		/*for (int index = 0; index < size; index++)
		{
			int n = index;
			int nn = n * 2;
			recomposedCoefficients[nn] = 0.0d;
			recomposedCoefficients[nn + 1] = 0.0d;
			for (int i = 0; i < daubechiesScalingSequence.length / 2; i++)
			{
				int k = i;
				int kk = k * 2;
				int j = (n - k + offset) % size;
				double s = scalingCoefficients[j];
				double w = waveletCoefficients[j];
				recomposedCoefficients[nn] = recomposedCoefficients[nn] + daubechiesScalingSequence[kk] * s + daubechiesWaveletSequence[kk] * w;
				recomposedCoefficients[nn + 1] = recomposedCoefficients[nn + 1] + daubechiesScalingSequence[kk + 1] * s + daubechiesWaveletSequence[kk + 1] * w;
			}
		}*/
		
		IntStream.range(0, size).forEach(index -> {
			int n = index;
			int nn = n * 2;
			recomposedCoefficients[nn] = 0.0d;
			recomposedCoefficients[nn + 1] = 0.0d;
			IntStream.range(0, daubechiesScalingSequence.length / 2).forEach(i -> {
				int k = i;
				int kk = k * 2;
				int j = (n - k + offset) % size;
				double s = scalingCoefficients[j];
				double w = waveletCoefficients[j];
				recomposedCoefficients[nn] = recomposedCoefficients[nn] + daubechiesScalingSequence[kk] * s + daubechiesWaveletSequence[kk] * w;
				recomposedCoefficients[nn + 1] = recomposedCoefficients[nn + 1] + daubechiesScalingSequence[kk + 1] * s + daubechiesWaveletSequence[kk + 1] * w;});});
		
	}

    /**
     * スケーリング係数とウェーブレット展開係数から再構成したデータ(元データと同等となるデータ)を再計算する。
     */
    protected void reComputeRecomposedCoefficients(){
        if (scalingCoefficients == null) { return; }
		if (interactiveCoefficients == null) { return; }
		int size = scalingCoefficients.length;
		recomposedCoefficients = new double[size * 2];
		int offset = Math.max(1024, size);
		/*for (int index = 0; index < size; index++)
		{
			int n = index;
			int nn = n * 2;
			recomposedCoefficients[nn] = 0.0d;
			recomposedCoefficients[nn + 1] = 0.0d;
			for (int i = 0; i < daubechiesScalingSequence.length / 2; i++)
			{
				int k = i;
				int kk = k * 2;
				int j = (n - k + offset) % size;
				double s = scalingCoefficients[j];
				double w = interactiveCoefficients[j];
				recomposedCoefficients[nn] = recomposedCoefficients[nn] + daubechiesScalingSequence[kk] * s + daubechiesWaveletSequence[kk] * w;
				recomposedCoefficients[nn + 1] = recomposedCoefficients[nn + 1] + daubechiesScalingSequence[kk + 1] * s + daubechiesWaveletSequence[kk + 1] * w;
			}
		}
		*/
		IntStream.range(0, size).forEach(index -> {
			int n = index;
			int nn = n * 2;
			recomposedCoefficients[nn] = 0.0d;
			recomposedCoefficients[nn + 1] = 0.0d;
			IntStream.range(0, daubechiesScalingSequence.length / 2).forEach(i -> {
				int k = i;
				int kk = k * 2;
				int j = (n - k + offset) % size;
				double s = scalingCoefficients[j];
				double w = interactiveCoefficients[j];
				recomposedCoefficients[nn] = recomposedCoefficients[nn] + daubechiesScalingSequence[kk] * s + daubechiesWaveletSequence[kk] * w;
				recomposedCoefficients[nn + 1] = recomposedCoefficients[nn + 1] + daubechiesScalingSequence[kk + 1] * s + daubechiesWaveletSequence[kk + 1] * w;});});
		
    }

    /**
     * 元データからスケーリング係数とウェーブレット展開係数を計算する。
     */
    protected void computeScalingAndWaveletCoefficients() {
        if (sourceCoefficients == null) {
            return;
        }
        int length = sourceCoefficients.length;
        int size = length / 2;
        scalingCoefficients = new double[size];
        waveletCoefficients = new double[size];
        IntStream.range(0, size).forEach(index -> {
            scalingCoefficients[index] = 0.0d;
            waveletCoefficients[index] = 0.0d;
            IntStream.range(0, daubechiesScalingSequence.length).forEach(i -> {
                int j = (i + (2 * index)) % length;
                double v = sourceCoefficients[j];
                scalingCoefficients[index] = scalingCoefficients[index] + daubechiesScalingSequence[i] * v;
                waveletCoefficients[index] = waveletCoefficients[index] + daubechiesWaveletSequence[i] * v;
            });
        });
    }

    /**
     * ユーザーが操作するための係数を計算する。初期値は0.0d。データ数は32個。
     */
    protected void computeInteractiveCoefficients() {
        interactiveCoefficients = new double[32];
        IntStream.range(0, interactiveCoefficients.length).forEach(i -> {
            interactiveCoefficients[i] = 0.0d;
        });
    }
}
