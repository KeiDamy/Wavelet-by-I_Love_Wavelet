package wavelet;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.util.stream.IntStream;
import utility.ColorUtility;
import utility.ImageUtility;

/**
 * 離散ウェーブレット2次元変換の例題プログラム。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Example2d extends Object
{
    /**
     * 画像をファイルに書き出す際の番号。
     */
    private static int fileNo = 100;

    /**
     * ウィンドウの表示位置。
     */
    private static Point displayPoint = new Point(130, 50);

    /**
     * ウィンドウをずらして表示してゆく際の支距。
     */
    private static Point offsetPoint = new Point(25, 25);

    /**
     * 離散ウェーブレット2次元変換の例題プログラム群を実行する。
     * @param arguments おまじない
     */
    public static void main(String[] arguments)
    {
        // 離散ウェーブレット2次元変換の例題プログラムを実行する。
        Example2d.example1();

        GridLayout aLayout = new GridLayout(2, 2);
        JPanel aPanel = new JPanel(aLayout);
        JPanel imagePanel = new JPanel(new BorderLayout());

        WaveletPaneModel aModel = new WaveletPaneModel("ResultImages/Wavelet100.jpg");
        WaveletPaneView aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel.add(aView, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet105.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet113.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet111.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //離散ウェーブレット2次元変換の例題プログラム(SmalltalkBalloon)を実行する。
        Example2d.example2();

        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);

        aModel = new WaveletPaneModel("ResultImages/Wavelet256.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet205.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet256.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet205.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //赤

        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);

        aModel = new WaveletPaneModel("ResultImages/Wavelet214.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet219.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet227.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet224.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //緑
        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);
        
        aModel = new WaveletPaneModel("ResultImages/Wavelet228.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet233.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet241.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet238.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //青
        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);
        
        aModel = new WaveletPaneModel("ResultImages/Wavelet242.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet247.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet255.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet252.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //離散ウェーブレット2次元変換の例題プログラム(Earth)を実行する。
        Example2d.example3();

        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);

        aModel = new WaveletPaneModel("ResultImages/Wavelet356.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet305.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet356.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet305.jpg");
        
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //赤
        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);

        aModel = new WaveletPaneModel("ResultImages/Wavelet314.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet319.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet327.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet324.jpg");
        
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //緑
        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);

        aModel = new WaveletPaneModel("ResultImages/Wavelet328.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet333.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet341.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet338.jpg");
        
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        //青

        aLayout = new GridLayout(2, 2);
        aPanel = new JPanel(aLayout);

        aModel = new WaveletPaneModel("ResultImages/Wavelet342.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Image before compression");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet347.jpg");
        // ここはカラー画像に置き換える
        aView = new WaveletPaneView(aModel, new WaveletPaneController());
        
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        aModel = new WaveletPaneModel("ResultImages/Wavelet355.jpg");
        aView = new WaveletPaneView(aModel, new WaveletPaneController());

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("Compressed image");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);
        

        aModel = new WaveletPaneModel("ResultImages/Wavelet352.jpg");
        
        aView = new WaveletPaneView(aModel, new WaveletPaneController());

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(aView, BorderLayout.CENTER);
        titleLabel = new JLabel("transformation process");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imagePanel.add(titleLabel, BorderLayout.SOUTH);
        imagePanel.setBackground(Color.WHITE);
        aPanel.add(imagePanel);

        open(aPanel);

        return;
    }

    /**
     * 離散ウェーブレット2次元変換の例題プログラム。
     */
    protected static void example1()
    {
        fileNo = 100;
        double[][] sourceDataMatrix = Wavelet2dModel.dataSampleCoefficients();
        Example2d.perform(sourceDataMatrix, new Point(4, 4), Constants.Luminance);
        return;
    }

    /**
     * 離散ウェーブレット2次元変換の例題プログラム(SmalltalkBalloon)。
     */
    protected static void example2()
    {
        fileNo = 200;
        double[][][] lrgbSourceCoefficients = Wavelet2dModel.dataSmalltalkBalloon();
        Example2d.perform(lrgbSourceCoefficients, "Smalltalk Balloon");
        return;
    }

    /**
     * 離散ウェーブレット2次元変換の例題プログラム(Earth)。
     */
    protected static void example3()
    {
        fileNo = 300;
        double[][][] lrgbSourceCoefficients = Wavelet2dModel.dataEarth();
        Example2d.perform(lrgbSourceCoefficients, "Earth");
        return;
    }



    /**
     * レイアウトされたパネル(aPanel)を受け取り、それをウィンドウに乗せて開く。
     * @param aPanel パネル情報
     */
    private static void open(JPanel aPanel)
    {
        Example2d.open(aPanel, 512, 512);
        return;
    }

    /**
     * レイアウトされたパネル(aPanel)を受け取り、それをウィンドウに乗せて開く。
     * @param aPanel パネル情報
     * @param width 横
     * @param height 縦
     */
    protected static void open(JPanel aPanel, int width, int height)
    {
        JFrame aWindow = new JFrame("Wavelet Example (2D)");
        aWindow.getContentPane().add(aPanel);
        aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aWindow.addNotify();
        int titleBarHeight = aWindow.getInsets().top;
        aWindow.setMinimumSize(new Dimension(width / 2, height / 2 + titleBarHeight));
        aWindow.setResizable(true);
        aWindow.setSize(width, height + titleBarHeight);
        aWindow.setLocation(displayPoint.x, displayPoint.y);
        aWindow.setVisible(true);
        aWindow.toFront();
        displayPoint = new Point(displayPoint.x + offsetPoint.x, displayPoint.y + offsetPoint.y);
        return;
    }

    /**
     * 元データ(sourceDataMatrix)に離散ウエーブレット2次元変換を施して結果を表示する。
     * @param sourceDataMatrix 元データ
     * @param scaleFactor ポイント情報
     * @param rgbFlag どの色を表示するか
     */
    protected static double[][] perform(double[][] sourceDataMatrix, Point scaleFactor, int rgbFlag)
    {
        double[][] sourceCoefficients = sourceDataMatrix;

        // 元データからネストされたスケーリング係数とウェーブレット展開係数を順変換で求める。
        DiscreteWavelet2dTransformation aTransformation = new DiscreteWavelet2dTransformation(sourceCoefficients);
        double[][] scalingCoefficients = aTransformation.scalingCoefficients();
        double[][] horizontalWaveletCoefficients = aTransformation.horizontalWaveletCoefficients();
        double[][] verticalWaveletCoefficients = aTransformation.verticalWaveletCoefficients();
        double[][] diagonalWaveletCoefficients = aTransformation.diagonalWaveletCoefficients();

        // それぞれの画像を作る。
        BufferedImage imageSourceCoefficients = Wavelet2dModel.generateImage(sourceCoefficients, scaleFactor, rgbFlag);
        BufferedImage imageScalingCoefficients = Wavelet2dModel.generateImage(scalingCoefficients, scaleFactor, rgbFlag);
        BufferedImage imageHorizontalWaveletCoefficients = Wavelet2dModel.generateImage(horizontalWaveletCoefficients, scaleFactor, Constants.Luminance);
        BufferedImage imageVerticalWaveletCoefficients = Wavelet2dModel.generateImage(verticalWaveletCoefficients, scaleFactor, Constants.Luminance);
        BufferedImage imageDiagonalWaveletCoefficients = Wavelet2dModel.generateImage(diagonalWaveletCoefficients, scaleFactor, Constants.Luminance);

        // それぞれの画像をファイルに書く。
        Example2d.write(imageSourceCoefficients);
        Example2d.write(imageScalingCoefficients);
        Example2d.write(imageHorizontalWaveletCoefficients);
        Example2d.write(imageVerticalWaveletCoefficients);
        Example2d.write(imageDiagonalWaveletCoefficients);

        // スケーリング係数の画像と水平・垂直・対角のウェーブレット展開係数の画像群を一つの画像にまとめる。
        BufferedImage imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(imageScalingCoefficients,
                                                                                                 imageHorizontalWaveletCoefficients,
                                                                                     imageVerticalWaveletCoefficients,
                                                                                     imageDiagonalWaveletCoefficients);
        // その画像をファイルに書く。
        Example2d.write(imageScalingWaveletCoefficients);

        // スケーリング係数を元データにして、ネストされたスケーリング係数とウェーブレット展開係数を順変換で求める。
        aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficients);
        double[][] nestScalingCoefficients = aTransformation.scalingCoefficients();
        double[][] nestHorizontalWaveletCoefficients = aTransformation.horizontalWaveletCoefficients();
        double[][] nestVerticalWaveletCoefficients = aTransformation.verticalWaveletCoefficients();
        double[][] nestDiagonalWaveletCoefficients = aTransformation.diagonalWaveletCoefficients();

        // それぞれの画像を作る。
        BufferedImage imageNestScalingCoefficients = Wavelet2dModel.generateImage(nestScalingCoefficients, scaleFactor, rgbFlag);
        BufferedImage imageNestHorizontalWaveletCoefficients = Wavelet2dModel.generateImage(nestHorizontalWaveletCoefficients, scaleFactor, Constants.Luminance);
        BufferedImage imageNestVerticalWaveletCoefficients = Wavelet2dModel.generateImage(nestVerticalWaveletCoefficients, scaleFactor, Constants.Luminance);
        BufferedImage imageNestDiagonalWaveletCoefficients = Wavelet2dModel.generateImage(nestDiagonalWaveletCoefficients, scaleFactor, Constants.Luminance);

        // それぞれの画像をファイルに書く。
        Example2d.write(imageNestScalingCoefficients);
        Example2d.write(imageNestHorizontalWaveletCoefficients);
        Example2d.write(imageNestVerticalWaveletCoefficients);
        Example2d.write(imageNestDiagonalWaveletCoefficients);

        // スケーリング係数の画像と水平・垂直・対角のウェーブレット展開係数の画像群を一つの画像にまとめる。
        BufferedImage imageNestScalingWaveletCoefficients = (Wavelet2dModel.generateImage(imageNestScalingCoefficients,
                                                                                          imageNestHorizontalWaveletCoefficients,
                                                                                          imageNestVerticalWaveletCoefficients,
                                                                                          imageNestDiagonalWaveletCoefficients));
        // その画像をファイルに書く。
        Example2d.write(imageNestScalingWaveletCoefficients);

        // スケーリング係数の画像と水平・垂直・対角のウェーブレット展開係数の画像群を一つの画像にまとめる。
        imageNestScalingWaveletCoefficients = Wavelet2dModel.generateImage(imageNestScalingWaveletCoefficients,
                                                                           imageHorizontalWaveletCoefficients,
                                                                           imageVerticalWaveletCoefficients,
                                                                           imageDiagonalWaveletCoefficients);
        // その画像をファイルに書く。
        Example2d.write(imageNestScalingWaveletCoefficients);

        // ネストされたスケーリング係数とウェーブレット展開係数から元データ(元のスケーリング係数)を逆変換で求める。
        double[][][] nestWaveletCoefficients = new double[][][] { nestHorizontalWaveletCoefficients, nestVerticalWaveletCoefficients, nestDiagonalWaveletCoefficients };
        aTransformation = new DiscreteWavelet2dTransformation(nestScalingCoefficients, nestWaveletCoefficients);
        double[][] nestRecomposedCoefficients = aTransformation.recomposedCoefficients();

        // 再構成された元データを画像にし、その画像をファイルに書く。
        BufferedImage imageNestRecomposedCoefficients = Wavelet2dModel.generateImage(nestRecomposedCoefficients, scaleFactor, rgbFlag);
        Example2d.write(imageNestRecomposedCoefficients);

        // スケーリング係数とウェーブレット展開係数から元データを逆変換で求める。
        double[][][] waveletCoefficients = new double[][][] { horizontalWaveletCoefficients, verticalWaveletCoefficients, diagonalWaveletCoefficients };
        aTransformation = new DiscreteWavelet2dTransformation(nestRecomposedCoefficients, waveletCoefficients);
        double[][] recomposedCoefficients = aTransformation.recomposedCoefficients();

        // 再構成された元データを画像にし、その画像をファイルに書く。
        BufferedImage imageRecomposedCoefficients = Wavelet2dModel.generateImage(recomposedCoefficients, scaleFactor, rgbFlag);
        Example2d.write(imageRecomposedCoefficients);

        return recomposedCoefficients;
    }

    /**
     * 元データ(lrgbSourceCoefficients)に離散ウエーブレット2次元変換を施して結果を表示する。
     * @param lrgbSourceCoefficients 元データ
     * @param labelString ラベル情報
     */
    protected static void perform(double[][][] lrgbSourceCoefficients, String labelString)
    {
        double[][] luminanceSourceCoefficients = lrgbSourceCoefficients[0];
        double[][] redSourceCoefficients = lrgbSourceCoefficients[1];
        double[][] greenSourceCoefficients = lrgbSourceCoefficients[2];
        double[][] blueSourceCoefficients = lrgbSourceCoefficients[3];

        Point scaleFactor = new Point(1, 1);

        double[][] luminanceRecomposedCoefficients = Example2d.perform(luminanceSourceCoefficients, scaleFactor, Constants.Luminance);
        double[][] redRecomposedCoefficients = Example2d.perform(redSourceCoefficients, scaleFactor, Constants.Red);
        double[][] greenRecomposedCoefficients = Example2d.perform(greenSourceCoefficients, scaleFactor, Constants.Green);
        double[][] blueRecomposedCoefficients = Example2d.perform(blueSourceCoefficients, scaleFactor, Constants.Blue);

        int width = luminanceRecomposedCoefficients.length;
        int height = luminanceRecomposedCoefficients[0].length;
        BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        IntStream.range(0, height).forEach(y -> {IntStream.range(0, width).forEach(x -> {
            double r = redRecomposedCoefficients[x][y];
            double g = greenRecomposedCoefficients[x][y];
            double b = blueRecomposedCoefficients[x][y];
            int aRGB = ColorUtility.convertRGBtoINT(r, g, b);
            anImage.setRGB(x, y, aRGB);});
        });
        Example2d.write(anImage);

        return;
    }

    /**
     * 画像(anImage)をファイルに書き出す。
     * @param anImage 画像情報
     */
    protected static void write(BufferedImage anImage)
    {
        File aDirectory = new File("ResultImages");
        if (aDirectory.exists() == false) { aDirectory.mkdir(); }
        String aString = Integer.toString(fileNo++);
        while (aString.length() < 3) { aString = "0" + aString; }
        ImageUtility.writeImage(anImage, aDirectory.getName() + "/Wavelet" + aString + ".jpg");
        return;
    }
}


