package wavelet;

import java.util.stream.IntStream;
import condition.Condition;

/**
 * 離散ウェーブレット変換(discrete wavelet transformation)の抽象クラス。
 * 離散ウェーブレット1次元変換(discrete wavelet 1D transformation)と離散ウェーブレット2次元変換(discrete wavelet 2D transformation)を抽象する。
 * <a href="http://en.wikipedia.org/wiki/Ingrid_Daubechies">イングリッド・ドブシー(Ingrid Daubechies)</a>さんの<a href="http://en.wikipedia.org/wiki/Daubechies_wavelet">ウェーブレット(Daubechies wavelet)</a>を応用する。
 */
public abstract class DiscreteWaveletTransformation extends WaveletTransformation
{
    /**
     * ドブシーさんのN=2〜4のスケーリング数列　Ingrid Daubechies, "Ten lectures on wavelets", Society for Industrial and Applied Mathematics, Philadelphia, PA, USA 1992.
     */
    protected double[] daubechiesScalingSequence;
    /**
     * ドブシーさんのN=2〜4のウェーブレット展開数列　Ingrid Daubechies, "Ten lectures on wavelets", Society for Industrial and Applied Mathematics, Philadelphia, PA, USA 1992.
     */
    protected double[] daubechiesWaveletSequence;

    /**
     * 離散ウェーブレット変換を初期化する。
     */
    protected void initialize()
    {
        this.initialize(2);
    }
    /**
     * <a href="http://en.wikipedia.org/wiki/Ingrid_Daubechies">イングリッド・ドブシー(Ingrid Daubechies)</a>さんの<a href="http://en.wikipedia.org/wiki/Daubechies_wavelet">ウェーブレット列(N=2〜4)</a>を用いて、離散ウェーブレット変換を初期化する。
     * Ingrid Daubechies, "The wavelet transform, time-frequency localization and signal analysis", IEEE Transaction on Information Theory, vol.36, pp.961-1005, Sep 1990.
     */
    protected void initialize(int N)
    {
        super.initialize();

        // N=2のドブシー数列　Ingrid Daubechies, "Ten lectures on wavelets", Society for Industrial and Applied Mathematics, Philadelphia, PA, USA 1992.
        daubechiesScalingSequence = new double[] { 0.4829629131445341d, 0.8365163037378077d, 0.2241438680420134d, (0.0d - 0.1294095225512603d) };

        // N=3のドブシー数列　Ingrid Daubechies, "Ten lectures on wavelets", Society for Industrial and Applied Mathematics, Philadelphia, PA, USA 1992.
        if (N == 3) daubechiesScalingSequence = new double[] { 0.3326705529500825d, 0.8068915093110924d, 0.4598775021184914d, (0.0d - 0.1350110200102546d), (0.0d - 0.0854412738820267d), 0.0352262918857095d };

        // N=4のドブシー数列　Ingrid Daubechies, "Ten lectures on wavelets", Society for Industrial and Applied Mathematics, Philadelphia, PA, USA 1992.
        if (N == 4) daubechiesScalingSequence = new double[] { 0.2303778133088964d, 0.7148465705529154d, 0.6308807679298599d, (0.0d - 0.0279837694168599d), (0.0d - 0.1870348117190931d), 0.0308413818355607d, 0.0328830116668852d, (0.0d - 0.0105974017850690d) };

        int size = daubechiesScalingSequence.length;
        daubechiesWaveletSequence = new double[size];
        IntStream.range(0, size).forEach(n -> {
            daubechiesWaveletSequence[n] = Math.pow(-1, n) * daubechiesScalingSequence[size - 1 - n];});
        
        return;
    }
}
