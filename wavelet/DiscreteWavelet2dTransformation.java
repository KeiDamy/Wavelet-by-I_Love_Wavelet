package wavelet;

import java.util.stream.IntStream;
/**
 * 離散ウェーブレット2次元変換(discrete wavelet 2D transformation)。
 */
public class DiscreteWavelet2dTransformation extends DiscreteWaveletTransformation
{
	/**
	 * 元データを保持するフィールド。
	 */
	protected double[][] sourceCoefficients;
	
	/**
	 * スケーリング係数を保持するフィールド。
	 */
	protected double[][] scalingCoefficients;
	
	/**
	 * ウェーブレット展開係数を保持するフィールド。水平[0][][]と垂直[1][][]と対角[2][][]。
	 */
	protected double[][][] waveletCoefficients;
	
	/**
	 * スケーリング係数とウェーブレット展開係数から逆ウェーブレット変換を施して再構成したデータ(元データと同等となるデータ)を保持するフィールド。
	 */
	protected double[][] recomposedCoefficients;

	/**
	 * 元データを指定して離散ウェーブレット2次元変換のインスタンスを作るコンストラクタ。
	 * 通常、順ウェーブレット変換(元データからスケーリング係数とウェーブレット展開係数を求めるため)に用いる。
	 */
	public DiscreteWavelet2dTransformation(double[][] sourceCollection)
	{
		super();
		this.sourceCoefficients(sourceCollection);
	}

	/**
	 * スケーリング係数とウェーブレット展開係数を指定して離散ウェーブレット2次元変換のインスタンスを作るコンストラクタ。
	 * 通常、逆ウェーブレット変換(スケーリング係数とウェーブレット展開係数から元データを求めるため)に用いる。
	 */
	public DiscreteWavelet2dTransformation(double[][] scalingCollection, double[][][] waveletCollection)
	{
		super();
		this.scalingCoefficients(scalingCollection);
		this.waveletCoefficients(waveletCollection);
	}

	/**
	 * <a href="http://en.wikipedia.org/wiki/Ingrid_Daubechies">イングリッド・ドブシー(Ingrid Daubechies)</a>さんの<a href="http://en.wikipedia.org/wiki/Daubechies_wavelet">ウェーブレット列(N=2〜4)</a>を用いて、離散ウェーブレット変換を初期化する。
	 * Ingrid Daubechies, "The wavelet transform, time-frequency localization and signal analysis", IEEE Transaction on Information Theory, vol.36, pp.961-1005, Sep 1990.
	 */
	protected void initialize()
	{
		super.initialize();
		sourceCoefficients = null;
		scalingCoefficients = null;
		waveletCoefficients = null;
		recomposedCoefficients = null;
	}

	/**
	 * スケーリング係数とウェーブレット展開係数から逆ウェーブレット変換を施して再構成したデータを応答する。
	 */
	public double[][] recomposedCoefficients()
	{
		if (recomposedCoefficients == null) { this.computeRecomposedCoefficients(); }
		return recomposedCoefficients;
	}

	/**
	 * スケーリング係数を応答する。
	 */
	public double[][] scalingCoefficients()
	{
		if (scalingCoefficients == null) { this.computeScalingAndWaveletCoefficients(); }
		return scalingCoefficients;
	}

	/**
	 * スケーリング係数を設定する。
	 */
	public void scalingCoefficients(double[][] scalingCollection)
	{
		scalingCoefficients = scalingCollection;
		recomposedCoefficients = null;
	}

	/**
	 * 元データを応答する。
	 */
	public double[][] sourceCoefficients()
	{
		return sourceCoefficients;
	}

	/**
	 * 元データを設定する。
	 */
	public void sourceCoefficients(double[][] valueCollection)
	{
		sourceCoefficients = valueCollection;
		scalingCoefficients = null;
		recomposedCoefficients = null;
	}

	/**
	 * ウェーブレット展開係数を応答する。
	 */
	public double[][][] waveletCoefficients()
	{
		if (waveletCoefficients == null) { this.computeScalingAndWaveletCoefficients(); }
		return waveletCoefficients;
	}

	/**
	 * ウェーブレット展開係数を設定する。
	 */
	public void waveletCoefficients(double[][][] waveletCollection)
	{
		waveletCoefficients = waveletCollection;
		recomposedCoefficients = null;
	}

	/**
	 * 対角ウェーブレット展開係数を応答する。
	 */
	public double[][] diagonalWaveletCoefficients()
	{
		return this.waveletCoefficients()[2];
	}

	/**
	 * 水平ウェーブレット展開係数を応答する。
	 */
	public double[][] horizontalWaveletCoefficients()
	{
		return this.waveletCoefficients()[0];
	}

	/**
	 * 垂直ウェーブレット展開係数を応答する。
	 */
	public double[][] verticalWaveletCoefficients()
	{
		return this.waveletCoefficients()[1];
	}

	/**
	 * あるオブジェクト(anObject)に対して、この離散ウェーブレット変換を適用する。
	 */
	public WaveletTransformation applyTo(Object anObject)
	{
		if (anObject instanceof double[][] == false)
		{
			throw new IllegalArgumentException("anObject must be a double[][].");
		}
		this.sourceCoefficients((double[][]) anObject);
		this.scalingCoefficients();
		this.waveletCoefficients();
		return this;
	}

	/**
	 * あるウェーブレット変換(waveletTransformation)に対して、この離散ウェーブレット変換を施す。
	 */
	public WaveletTransformation transform(WaveletTransformation waveletTransformation)
	{
		if (waveletTransformation instanceof DiscreteWavelet2dTransformation == false)
		{
			throw new IllegalArgumentException("waveletTransformation must be a DiscreteWavelet2dTransformation.");
		}
		DiscreteWavelet2dTransformation discreteWavelet2dTransformation = (DiscreteWavelet2dTransformation)waveletTransformation;
		double[][] coefficients = discreteWavelet2dTransformation.sourceCoefficients();
		if (coefficients == null) { coefficients = discreteWavelet2dTransformation.recomposedCoefficients(); }
		return new DiscreteWavelet2dTransformation(coefficients);
	}

	/**
	 * スケーリング係数とウェーブレット展開係数から再構成したデータ(元データと同等となるデータ)を計算する。
	 */
	protected void computeRecomposedCoefficients()
	{
		if (scalingCoefficients == null) { return; }
		if (waveletCoefficients == null) { return; }
		int halfRowSize = this.rowSize(scalingCoefficients);
		int rowSize = halfRowSize * 2;
		int halfColumnSize = this.columnSize(scalingCoefficients);
		int columnSize = halfColumnSize * 2;
		double[][] targetScalingCoefficients = this.transpose(scalingCoefficients);
		double[][] horizontalWaveletCoefficients = this.transpose(this.horizontalWaveletCoefficients());
		double[][] verticalWaveletCoefficients = this.transpose(this.verticalWaveletCoefficients());
		double[][] diagonalWaveletCoefficients = this.transpose(this.diagonalWaveletCoefficients());
		double[][] xScalingCoefficients = new double[halfColumnSize][rowSize];
		double[][] xWaveletCoefficients = new double[halfColumnSize][rowSize];

		for (int index = 0; index < halfColumnSize; index++)
		{
			DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(targetScalingCoefficients, index), this.atRow(horizontalWaveletCoefficients, index));
			this.atRowPut(xScalingCoefficients, index, waveletTransformation.recomposedCoefficients());
			waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(verticalWaveletCoefficients, index), this.atRow(diagonalWaveletCoefficients, index));
			this.atRowPut(xWaveletCoefficients, index, waveletTransformation.recomposedCoefficients());
		}
		xScalingCoefficients = this.transpose(xScalingCoefficients);
		xWaveletCoefficients = this.transpose(xWaveletCoefficients);
		double[][] targetRecomposedCoefficients = new double[rowSize][columnSize];
		for (int index = 0; index < rowSize; index++)
		{
			DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(xScalingCoefficients, index), this.atRow(xWaveletCoefficients, index));
			this.atRowPut(targetRecomposedCoefficients, index, waveletTransformation.recomposedCoefficients());
		}
		/*/以下の全てで、実質的finalではないとしてerrorが出る
		 * IntStream.range(0, rowSize).forEach(index -> {
		    DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(xScalingCoefficients, index), this.atRow(xWaveletCoefficients, index));
			this.atRowPut(targetRecomposedCoefficients, index, waveletTransformation.recomposedCoefficients());});
		 */
		recomposedCoefficients = targetRecomposedCoefficients;
	}

	/**
	 * 元データからスケーリング係数とウェーブレット展開係数を計算する。
	 */
	protected void computeScalingAndWaveletCoefficients()
	{
		if (sourceCoefficients == null) { return; }
		int rowSize = this.rowSize(sourceCoefficients);
		int halfRowSize = rowSize / 2;
		int columnSize = this.columnSize(sourceCoefficients);
		int halfColumnSize = columnSize / 2;
		double[][] xScalingCoefficients = new double[rowSize][halfColumnSize];
		double[][] xWaveletCoefficients = new double[rowSize][halfColumnSize];
		
		for (int index = 0; index < rowSize; index++)
		{
			DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(sourceCoefficients, index));
			this.atRowPut(xScalingCoefficients, index, waveletTransformation.scalingCoefficients());
			this.atRowPut(xWaveletCoefficients, index, waveletTransformation.waveletCoefficients());
		}
		/*
		IntStream.range(0, rowSize).forEach(index -> {
			//以下の全てで、実質的finalではないとしてerrorが出る
			DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(sourceCoefficients, index));
			this.atRowPut(xScalingCoefficients, index, waveletTransformation.scalingCoefficients());
			this.atRowPut(xWaveletCoefficients, index, waveletTransformation.waveletCoefficients());});
		*/
		xScalingCoefficients = this.transpose(xScalingCoefficients);
		xWaveletCoefficients = this.transpose(xWaveletCoefficients);
		double[][] targetScalingCoefficients = new double[halfColumnSize][halfRowSize];
		double[][] horizontalWaveletCoefficients = new double[halfColumnSize][halfRowSize];
		double[][] verticalWaveletCoefficients = new double[halfColumnSize][halfRowSize];
		double[][] diagonalWaveletCoefficients = new double[halfColumnSize][halfRowSize];
		for (int index = 0; index < halfColumnSize; index++)
		{
			DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(xScalingCoefficients, index));
			this.atRowPut(targetScalingCoefficients, index, waveletTransformation.scalingCoefficients());
			this.atRowPut(horizontalWaveletCoefficients, index, waveletTransformation.waveletCoefficients());
			waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(xWaveletCoefficients, index));
			this.atRowPut(verticalWaveletCoefficients, index, waveletTransformation.scalingCoefficients());
			this.atRowPut(diagonalWaveletCoefficients, index, waveletTransformation.waveletCoefficients());
		}
		/*
		IntStream.range(0, halfColumnSize).forEach(index -> {
			//以下の全てで、実質的finalではないとしてerrorが出る
			DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(xScalingCoefficients, index));
			this.atRowPut(targetScalingCoefficients, index, waveletTransformation.scalingCoefficients());
			this.atRowPut(horizontalWaveletCoefficients, index, waveletTransformation.waveletCoefficients());
			waveletTransformation = new DiscreteWavelet1dTransformation(this.atRow(xWaveletCoefficients, index));
			this.atRowPut(verticalWaveletCoefficients, index, waveletTransformation.scalingCoefficients());
			this.atRowPut(diagonalWaveletCoefficients, index, waveletTransformation.waveletCoefficients());});
		*/
		targetScalingCoefficients = this.transpose(targetScalingCoefficients);
		horizontalWaveletCoefficients = this.transpose(horizontalWaveletCoefficients);
		verticalWaveletCoefficients = this.transpose(verticalWaveletCoefficients);
		diagonalWaveletCoefficients = this.transpose(diagonalWaveletCoefficients);
		scalingCoefficients = targetScalingCoefficients;
		waveletCoefficients = new double[][][] { horizontalWaveletCoefficients, verticalWaveletCoefficients, diagonalWaveletCoefficients };
	}

	/**
	 * 指定された2次元配列の指定された行を応答する。
	 */
	private double[] atRow(double[][] coefficients, int index)
	{
		return coefficients[index];
	}
	
	/**
	 * 指定された2次元配列の指定された行を設定する。
	 */
	private void atRowPut(double[][] coefficients, int index, double[] values)
	{
		/*
		for (int x = 0; x < coefficients[index].length; x++)
		{
			coefficients[index][x] = values[x];
		}
		*/
		IntStream.range(0, coefficients[index].length).forEach(x -> {coefficients[index][x] = values[x];});
		
		return;
	}

	/**
	 * 指定された2次元配列の列数(カラム数)を応答する。
	 */
	private int columnSize(double[][] coefficients)
	{
		return coefficients[0].length;
	}

	/**
	 * 指定された2次元配列の行数(ロウ数)を応答する。
	 */
	private int rowSize(double[][] coefficients)
	{
		return coefficients.length;
	}
	
	/**
	 * 指定された2次元配列を転置した2次元配列を応答する。
	 */
	private double[][] transpose(double[][] coefficients)
	{
		int rowSize = this.rowSize(coefficients);
		int columnSize = this.columnSize(coefficients);
		double[][] transposedCoefficients = new double[columnSize][rowSize];
		
		IntStream.range(0, coefficients.length).forEach(y -> {IntStream.range(0, coefficients[y].length).forEach(x -> {
            transposedCoefficients[x][y] = coefficients[y][x];
        });});
		return transposedCoefficients;
	}
}
