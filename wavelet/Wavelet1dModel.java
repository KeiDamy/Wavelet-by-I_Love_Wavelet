package wavelet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;

import condition.Condition;
import utility.ImageUtility;

/**
 * 離散ウェーブレット1次元変換のモデル。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Wavelet1dModel extends WaveletModel {
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
    public double[] interactiveCoefficients;

    /**
     * データの拡大率。
     */
    private static Point scaleFactor = new Point(10, 100);

    /**
     * データのレンジ。
     */
    private static double rangeValue = 2.8d;

    /**
     * 元データを保持するフィールド。
     */
    private WaveletPaneModel sourceCoefficientsPaneModel = null;

    /**
     * スケーリング係数を保持するフィールド。
     */
    private WaveletPaneModel scalingCoefficientsPaneModel = null;

    /**
     * ウェーブレット展開係数を保持するフィールド。
     */
    private WaveletPaneModel waveletCoefficientsPaneModel = null;

    /**
     * 
     */
    private WaveletPaneModel interactiveCoefficientsPaneModel = null;

    private WaveletPaneModel recomposedCoefficientsPaneModel = null;

    
    /**
     * 離散ウェーブレット1次元変換のための元データ。
     */
    public static double[] dataSampleCoefficients() {
        double[] anArray = new double[64];
        Arrays.fill(anArray, 0.0d);
        IntStream.range(0, 16).forEach(i -> anArray[i] = Math.pow((double) (i + 1), 2.0d) / 256.0d);
        IntStream.range(16, 32).forEach(i -> anArray[i] = 0.2d);
        IntStream.range(32, 48).forEach(i -> anArray[i] = Math.pow((double) (48 - (i + 1)), 2.0d) / 256.0d - 0.5d);
        return anArray;
    }

    /**
     * 1次元配列の中を指定された値で初期化する。
     * @param anArray 対象配列
     * @param aValue 初期化する値
     */
    public static void fill(double[] anArray, double aValue) {
        Arrays.fill(anArray, aValue);
        return;
    }

    /**
     * アクションイベントが発生した際の動作を受け取る。
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param anActionEvent アクションイベント
     */
    public void actionPerformed() {
        interactiveCoefficientsPaneModel.picture(ImageUtility.readImage("ResultImages/Wavelet009.jpg"));
        recomposedCoefficientsPaneModel.picture(ImageUtility.readImage("ResultImages/Wavelet008.jpg"));
        interactiveCoefficientsPaneModel.changed();
        recomposedCoefficientsPaneModel.changed();
    }


    /**
     * 離散ウェーブレット1次元変換のためのデータ値(valueCollection)を画像に変換して応答する。
     * @param valueCollection データ値
     * @return 画像
     */
    public static BufferedImage generateImage(double[] valueCollection) {
        int size = valueCollection.length;
        int width = (int) Math.round((double) (size) * (double) (scaleFactor.x));
        int height = (int) Math.round(rangeValue * (double) (scaleFactor.y));
        BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D aGraphics = anImage.createGraphics();
        aGraphics.setColor(Color.white);
        aGraphics.fillRect(0, 0, width, height);
        aGraphics.setColor(Color.gray);
        aGraphics.setStroke(new BasicStroke(1));
        aGraphics.drawLine(0, height / 2, width, height / 2);

        IntStream.range(0, size).forEach(n -> {
            double v = valueCollection[n];
            int x = (int) Math.round((double) n * (double) (scaleFactor.x) + (double) (scaleFactor.x) / 2.0d);
            int y = (int) Math.round((0.0d - v) * (double) (scaleFactor.y) + (double) height / 2.0d);
            Rectangle box = new Rectangle(x, y, 1, 1);
            box.grow(2, 2);
            aGraphics.setColor(Color.black);
            aGraphics.fill(box);
        });
        return anImage;
    }

    /**
     * 可変のウィンドウを出す。
     * 
     * @author Yokokawa,Shibata
     * @version 1.0
     * @date 2023/7/7
     */
    public void open() {
        doAllCoefficients();

        GridLayout aLayout = new GridLayout(2, 3);
        JPanel aPanel = new JPanel(aLayout);
        JPanel imagePanel = new JPanel(new BorderLayout());

        WaveletPaneController aWaveletPaneController = new WaveletPaneController();

        this.sourceCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet005.jpg", "Source Coefficient", this);
        WaveletPaneView aView = new WaveletPaneView(this.sourceCoefficientsPaneModel, new WaveletPaneController());
        
        imagePanel.add(aView, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Source Coefficient");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        this.scalingCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet006.jpg", "Scaling & Wavelet Coefficients", this);
        aView = new WaveletPaneView(this.scalingCoefficientsPaneModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(aView, BorderLayout.CENTER);
		titleLabel = new JLabel("Scaling & Wavelet Coefficients");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		imagePanel.add(titleLabel, BorderLayout.NORTH);
		imagePanel.setBackground(Color.WHITE);
		aPanel.add(imagePanel);

        this.waveletCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet007.jpg", "Wavelet Coefficients", this);
        aView = new WaveletPaneView(this.waveletCoefficientsPaneModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Wavelet Coefficients");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        this.recomposedCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet008.jpg", "Recomposed Coefficient", this);
        aView = new WaveletPaneView(this.recomposedCoefficientsPaneModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(aView, BorderLayout.CENTER);
		titleLabel = new JLabel("Recomposed Coefficient");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		imagePanel.add(titleLabel, BorderLayout.SOUTH);
		imagePanel.setBackground(Color.WHITE);
		aPanel.add(imagePanel);

        aView = new WaveletPaneView(this.scalingCoefficientsPaneModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
		imagePanel.add(aView, BorderLayout.CENTER);
		titleLabel = new JLabel("Scaling & Wavelet Coefficients");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
		imagePanel.setBackground(Color.WHITE);
		aPanel.add(imagePanel);

        this.interactiveCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet009.jpg", "Interactive Scaling & Wavelet Coefficients", this);
        aView = new WaveletPaneView(this.interactiveCoefficientsPaneModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Interactive Scaling & Wavelet Coefficients");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        JFrame aWindow = new JFrame("Wavelet Example (1D)");
        aWindow.getContentPane().add(aPanel);
        aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aWindow.addNotify();
        int titleBarHeight = aWindow.getInsets().top;
        aWindow.setMinimumSize(new Dimension(400, 200 + titleBarHeight));
        aWindow.setResizable(true);
        aWindow.setSize(800, 400 + titleBarHeight);
        aWindow.setLocation(100, 100);
        aWindow.setVisible(true);
        aWindow.toFront();
        return;
    }

    /**
     * データの初期化
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/7/7
     */
    public void doClearCoeffients() {
        System.out.println("yes");
        Arrays.fill(interactiveCoefficients, 0.0d);
        computeRecomposedCoefficients();
        actionPerformed();
    }

    /**
     * 再計算及び画像の出力
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/7/17
     */
    public void doAllCoefficients() {
        sourceCoefficients = Wavelet1dModel.dataSampleCoefficients();
        DiscreteWavelet1dTransformation wavelet = new DiscreteWavelet1dTransformation(sourceCoefficients);
        scalingCoefficients = wavelet.scalingCoefficients();
        waveletCoefficients = wavelet.waveletCoefficients();
        interactiveCoefficients = wavelet.interactiveCoefficients();// 動く画像
        recomposedCoefficients = wavelet.recomposedCoefficients();
        recomposedCoefficients = wavelet.recomposedCoefficients();

        BufferedImage imageSourceCoefficients = Wavelet1dModel.generateImage(sourceCoefficients);
        BufferedImage imageScalingCoefficients = Wavelet1dModel.generateImage(scalingCoefficients);
        BufferedImage imageWaveletCoefficients = Wavelet1dModel.generateImage(waveletCoefficients);
        BufferedImage imageRecomposedCoefficients = Wavelet1dModel.generateImage(recomposedCoefficients);
        BufferedImage imageInteractiveCoefficients = Wavelet1dModel.generateImage(interactiveCoefficients);

        Example1d.write(imageSourceCoefficients);
        Example1d.write(imageScalingCoefficients);
        Example1d.write(imageWaveletCoefficients);
        Example1d.write(imageRecomposedCoefficients);
        Example1d.write(imageInteractiveCoefficients);

        return;
    }

    /**
     * マウスクリックイベントが発生した際の動作を受け取る。
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/7/7
     * @param aPoint      マウスの座標
     * @param aMouseEvent マウスイベント
     */
    public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
        int whichPoint=0;
        
        whichPoint=whereClicked(aPoint);
        interactiveCoefficients[whichPoint] = waveletCoefficients[whichPoint];
        
        computeRecomposedCoefficients();
        System.out.println(whichPoint);
        actionPerformed();
    }

    /**
     * マウスクリックイベントが発生した際の動作を受け取る。
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/7/7
     * @param aPoint      マウスの座標
     * @param aMouseEvent マウスイベント
     */
    public void mouseClickeAndKey(Point aPoint, MouseEvent aMouseEvent) {
        int whichPoint=0;
        System.out.println("aaa");
        
        whichPoint=whereClicked(aPoint);

        interactiveCoefficients[whichPoint] = 0.0d;
        computeRecomposedCoefficients();
        System.out.println(whichPoint);
        actionPerformed();
    }

    // public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
    //     int whichPoint=0;
        
    //     whichPoint=whereClicked(aPoint);
    //     if(interactiveCoefficients[whichPoint]==0.0d){
    //         interactiveCoefficients[whichPoint] = waveletCoefficients[whichPoint];
    //     }else{
    //         interactiveCoefficients[whichPoint] = 0.0d;
    //     }
    //     computeRecomposedCoefficients();
    //     System.out.println(whichPoint);
    //     actionPerformed();
    // }

    /**
     * クリックされた座標からどの配列を変更するか決める
     * @author Shibata
     * @version 1.0
     * @date 2023/7/7
     * @param aPoi
     * @return int型
     */
    public int whereClicked(Point aPoint) {
        int whichPoint = 0; // どこをクリックしたか
        int imageWidth = 0; // 画像の横幅
        int imageWidthDivisionLength = 0;   // 画像サイズ/配列数
        BufferedImage image = null; //画像
        try {
            image = ImageIO.read(new File("ResultImages/Wavelet009.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading Wavelet009");

        }
        imageWidth = image.getWidth();
        imageWidthDivisionLength = imageWidth / interactiveCoefficients.length;
        whichPoint = (int) aPoint.getX() / imageWidthDivisionLength;
        return whichPoint;
    }


    /**
     * スケーリング係数とウェーブレット展開係数から逆ウェーブレット変換を施して再構成したデータ(元データと同等となるデータ)を保持するフィールドを計算する。
     */
    private void computeRecomposedCoefficients() {

        int size = scalingCoefficients.length;
        DiscreteWavelet1dTransformation wavelet = new DiscreteWavelet1dTransformation(sourceCoefficients);
        recomposedCoefficients = new double[size * 2];
        int offset = Math.max(1024, size);
        for (int index = 0; index < size; index++) {
            int n = index;
            int nn = n * 2;
            recomposedCoefficients[nn] = 0.0d;
            recomposedCoefficients[nn + 1] = 0.0d;
            for (int i = 0; i < wavelet.daubechiesScalingSequence.length / 2; i++) {
                int k = i;
                int kk = k * 2;
                int j = (n - k + offset) % size;
                double s = scalingCoefficients[j];
                double w = interactiveCoefficients[j];
                recomposedCoefficients[nn] = recomposedCoefficients[nn] + wavelet.daubechiesScalingSequence[kk] * s
                        + wavelet.daubechiesWaveletSequence[kk] * w;
                recomposedCoefficients[nn + 1] = recomposedCoefficients[nn + 1] + wavelet.daubechiesScalingSequence[kk + 1] * s
                        + wavelet.daubechiesWaveletSequence[kk + 1] * w;
            }
        }
        BufferedImage imageRecomposedCoefficients = Wavelet1dModel.generateImage(recomposedCoefficients);
        BufferedImage imageInteractiveCoefficients = Wavelet1dModel.generateImage(interactiveCoefficients);

        File aDirectory = new File("ResultImages");
		new Condition(() -> aDirectory.exists() == false).ifTrue(() -> {
			aDirectory.mkdir();
		});
		ImageUtility.writeImage(imageRecomposedCoefficients, aDirectory.getName() + "/Wavelet008" + ".jpg");
		ImageUtility.writeImage(imageInteractiveCoefficients, aDirectory.getName() + "/Wavelet009" + ".jpg");
        return;
    }

}
