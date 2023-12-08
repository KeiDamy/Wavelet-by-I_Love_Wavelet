package wavelet;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utility.ImageUtility;

import java.util.stream.IntStream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import condition.Condition;
import javax.swing.SwingUtilities;
import pane.PaneModel;
import pane.PaneView;

/**
 * 離散ウェーブレット1次元変換の例題プログラム。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Example1d extends Object {
	/**
	 * 画像をファイルに書き出す際の番号。
	 */
	private static int fileNo = 0;

	/**
	 * ウィンドウの表示位置。
	 */
	private static Point displayPoint = new Point(30, 50);

	/**
	 * ウィンドウをずらして表示してゆく際の支距。
	 */
	private static Point offsetPoint = new Point(25, 25);

	/**
	 * 離散ウェーブレット1次元変換の例題プログラム群を実行する。
	 * @param arguments おまじない
	 */
	public static void main(String[] arguments) {
		// 離散ウェーブレット1次元変換の例題プログラムを実行する。
		Example1d.example1();

		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);

		WaveletPaneModel aModel = new WaveletPaneModel("ResultImages/Wavelet000.jpg");
		WaveletPaneView aView = new WaveletPaneView(aModel, new WaveletPaneController());
		
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(aView, BorderLayout.CENTER);

		JLabel titleLabel = new JLabel("SourceCoefficients");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		imagePanel.add(titleLabel, BorderLayout.NORTH);
		imagePanel.setBackground(Color.WHITE);
		aPanel.add(imagePanel);

		aModel = new WaveletPaneModel("ResultImages/Wavelet001.jpg");
		aView = new WaveletPaneView(aModel, new WaveletPaneController());

		imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(aView, BorderLayout.CENTER);
		titleLabel = new JLabel("Scaling Coefficients");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		imagePanel.add(titleLabel, BorderLayout.NORTH);
		imagePanel.setBackground(Color.WHITE);
		aPanel.add(imagePanel);

		aModel = new WaveletPaneModel("ResultImages/Wavelet000.jpg");
		aView = new WaveletPaneView(aModel, new WaveletPaneController());

		imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(aView, BorderLayout.CENTER);
		titleLabel = new JLabel("Recomposed Coefficients");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		imagePanel.add(titleLabel, BorderLayout.SOUTH);
		imagePanel.setBackground(Color.WHITE);
		aPanel.add(imagePanel);

		aModel = new WaveletPaneModel("ResultImages/Wavelet002.jpg");
		aView = new WaveletPaneView(aModel, new WaveletPaneController());

		imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(aView, BorderLayout.CENTER);
		titleLabel = new JLabel("Wavelet Coefficients");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		imagePanel.add(titleLabel, BorderLayout.SOUTH);
		imagePanel.setBackground(Color.WHITE);
		aPanel.add(imagePanel);

		/*
		 * // Windowsizeを取得して、ターミナルの座標出力が反応する範囲を限定する方法
		 * // 恐らくこの手法は厳しい by Sugiyama
		 * Component component = GridLayout.getComponent(2, 3);
		 * 
		 * // コンポーネントのサイズを取得
		 * int width = component.getWidth();
		 * int height = component.getHeight();
		 * 
		 * onewidth = width / 3;
		 * oneheight = height / 2;
		 * // by Suzuki, Sugiyama
		 */

		/*
		 * わからない。こんなんでは動いたりしない from:Kino
		 * IntStream.rangeClosed(0, 3).forEach(i -> {WaveletPaneModel aModel =
		 * WaveletPaneModel("ResultImages/Wavelet00"+i+".jpg");
		 * WaveletPaneView aView = WaveletPaneView(aModel, WaveletPaneController());
		 * aPanel.add(aView);});
		 */

		open(aPanel);
		// System.out.println(aPanel.getSize());

		// popupMenuMain();
		return;
	}



	/**
	 * 離散ウェーブレット1次元変換の例題プログラム。
	 */
	protected static void example1() {
		double[] sourceData = Wavelet1dModel.dataSampleCoefficients();
		Example1d.perform(sourceData);
		return;
	}

	/**
	 * 元データ(sourceData)に離散ウェーブレット1次元変換を施して結果を表示する。
	 * @param sourceData 元データ
	 */
	protected static void perform(double[] sourceData) {
		double[] sourceCoefficients = sourceData;
		DiscreteWavelet1dTransformation waveletTransformation = new DiscreteWavelet1dTransformation(sourceCoefficients);
		double[] scalingCoefficients = waveletTransformation.scalingCoefficients();
		double[] waveletCoefficients = waveletTransformation.waveletCoefficients();
		double[] interactiveCoefficients = waveletTransformation.interactiveCoefficients();// 動く画像
		double[] recomposedCoefficients = waveletTransformation.recomposedCoefficients();
		recomposedCoefficients = waveletTransformation.recomposedCoefficients();

		BufferedImage imageSourceCoefficients = Wavelet1dModel.generateImage(sourceCoefficients);
		BufferedImage imageScalingCoefficients = Wavelet1dModel.generateImage(scalingCoefficients);
		BufferedImage imageWavelstCoefficients = Wavelet1dModel.generateImage(waveletCoefficients);
		BufferedImage imageRecomposedCoefficients = Wavelet1dModel.generateImage(recomposedCoefficients);
		BufferedImage imageInteractiveCoefficients = Wavelet1dModel.generateImage(interactiveCoefficients);

		Example1d.write(imageSourceCoefficients);
		Example1d.write(imageScalingCoefficients);
		Example1d.write(imageWavelstCoefficients);
		Example1d.write(imageRecomposedCoefficients);
		Example1d.write(imageInteractiveCoefficients);

		return;
	}

	/**
	 * レイアウトされたパネル(aPanel)を受け取り、それをウィンドウに乗せて開く。
	 * @param aPanel パネルの情報
	 */
	protected static void open(JPanel aPanel) {
		JFrame aWindow = new JFrame("Wavelet Example (1D)");
		aWindow.getContentPane().add(aPanel);
		aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aWindow.addNotify();
		int titleBarHeight = aWindow.getInsets().top;
		aWindow.setMinimumSize(new Dimension(400, 200 + titleBarHeight));
		aWindow.setResizable(true);
		aWindow.setSize(800, 400 + titleBarHeight);
		aWindow.setLocation(displayPoint.x, displayPoint.y);
		aWindow.setVisible(true);
		aWindow.toFront();
		displayPoint = new Point(displayPoint.x + offsetPoint.x, displayPoint.y + offsetPoint.y);
		return;
	}

	/**
	 * 画像(anImage)をファイルに書き出す。
	 * @param animage 画像について
	 */
	protected static void write(BufferedImage anImage) {
		File aDirectory = new File("ResultImages");
		new Condition(() -> aDirectory.exists() == false).ifTrue(() -> {
			aDirectory.mkdir();
		});
		// if (aDirectory.exists() == false) { aDirectory.mkdir(); }
		String aString = Integer.toString(fileNo++);
		while (aString.length() < 3) {
			aString = "0" + aString;
		}
		ImageUtility.writeImage(anImage, aDirectory.getName() + "/Wavelet" + aString + ".jpg");
		return;
	}
}
