package wavelet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Color;
import java.io.File;
import java.awt.Point;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
//import java.util.concurrent.atomic.AtomicReference;!!!!!!!!
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JLabel;
import utility.ColorUtility;
import utility.ImageUtility;

/**
 * 離散ウェーブレット2次元変換のモデル。
 * オブザーバ・デザインパターン(MVC: Model-View-Controller)を用いた典型的(模範的)なプログラム。
 */
public class Wavelet2dModel extends WaveletModel
{

	protected double maximumAbsoluteSourceCoefficient = Double.MIN_VALUE;

	protected double maximumAbsoluteScalingCoefficient = Double.MIN_VALUE;

	protected double maximumAbsoluteWaveletCoefficient = Double.MIN_VALUE;

	protected double maximumAbsoluteRecomposedCoefficient = Double.MIN_VALUE;

	protected double[][][] sourceCoefficientsArray;

	protected double[][][] scalingCoefficientsArray = new double[4][][];

	protected double[][][] horizontalWaveletCoefficientsArray = new double[4][][];

	protected double[][][] verticalWaveletCoefficientsArray = new double[4][][];

	protected double[][][] diagonalWaveletCoefficientsArray = new double[4][][];

	protected double[][][] interactiveHorizontalWaveletCoefficientsArray = new double[4][][];

	protected double[][][] interactiveVerticalWaveletCoefficientsArray = new double[4][][];

	protected double[][][] interactiveDiagonalWaveletCoefficientsArray = new double[4][][];

	protected double[][][] recomposedCoefficientsArray = new double[4][][];

    protected double[][] scalingCoefficients;

    protected double[][][] waveletCoefficients;

    protected double[][][] interactiveWaveletCoefficients;


	protected WaveletPaneModel sourceCoefficientsPaneModel = null;

	protected WaveletPaneModel scalingAndWaveletCoefficientsPaneModel = null;

	protected WaveletPaneModel interactiveScalingAndWaveletCoefficientsPaneModel = null;

	protected WaveletPaneModel recomposedCoefficientsPaneModel = null;

    protected int flag = 1;

    public Wavelet2dModel(){
        doSampleCoefficients();
        computeRecomposedCoefficients();
    }

    public void open(){
        GridLayout aLayout = new GridLayout(2, 2);
        JPanel aPanel = new JPanel(aLayout);
        // JPanel imagePanel = new JPanel(new BorderLayout());

        this.sourceCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet901.jpg", "Source Coefficient", this);
        WaveletPaneView aView = new WaveletPaneView(sourceCoefficientsPaneModel, new WaveletPaneController());
        aPanel.add(aView);
        
        // JLabel titleLabel = new JLabel("Source Coefficient");
        // titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        // imagePanel.add(titleLabel, BorderLayout.NORTH);
        // imagePanel.setBackground(Color.WHITE);
        // aPanel.add(imagePanel);

        this.scalingAndWaveletCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet906.jpg", "Scaling & Wavelet Coefficients", this);
        aView = new WaveletPaneView(scalingAndWaveletCoefficientsPaneModel, new WaveletPaneController());
        aPanel.add(aView);

        // imagePanel.add(aView, BorderLayout.CENTER);
		// titleLabel = new JLabel("Scaling & Wavelet Coefficients");
		// titleLabel.setHorizontalAlignment(JLabel.CENTER);
		// titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		// imagePanel.add(titleLabel, BorderLayout.NORTH);
		// imagePanel.setBackground(Color.WHITE);
		// aPanel.add(imagePanel);

        this.recomposedCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet907.jpg", "Recomposed Coefficient", this);
        aView = new WaveletPaneView(recomposedCoefficientsPaneModel, new WaveletPaneController());
        aPanel.add(aView);
        
        // imagePanel.add(aView, BorderLayout.CENTER);
		// titleLabel = new JLabel("Recomposed Coefficient");
		// titleLabel.setHorizontalAlignment(JLabel.CENTER);
		// titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		// imagePanel.add(titleLabel, BorderLayout.NORTH);
		// imagePanel.setBackground(Color.WHITE);
		// aPanel.add(imagePanel);

        this.interactiveScalingAndWaveletCoefficientsPaneModel = new WaveletPaneModel("ResultImages/Wavelet908.jpg", "Interactive Scaling & Wavelet Coefficients", this);
        aView = new WaveletPaneView(interactiveScalingAndWaveletCoefficientsPaneModel, new WaveletPaneController());
        aPanel.add(aView);
        
        // imagePanel.add(aView, BorderLayout.CENTER);
        // titleLabel = new JLabel("Interactive Scaling & Wavelet Coefficients");
        // titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        // imagePanel.add(titleLabel, BorderLayout.NORTH);
        // imagePanel.setBackground(Color.WHITE);
        // aPanel.add(imagePanel);

        JFrame aWindow = new JFrame("Wavelet Example (2D)");
        aWindow.getContentPane().add(aPanel);
        aWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aWindow.addNotify();
        int titleBarHeight = aWindow.getInsets().top;
        aWindow.setMinimumSize(new Dimension(400, 400 + titleBarHeight));
        aWindow.setResizable(true);
        aWindow.setSize(400, 400 + titleBarHeight);
        aWindow.setLocation(100, 100);
        aWindow.setVisible(true);
        aWindow.toFront();
        return;
    }

    public void doSampleCoefficients(){
        
        double[][] sourceCoefficients = Wavelet2dModel.dataSampleCoefficients();
        DiscreteWavelet2dTransformation aTransformation = new DiscreteWavelet2dTransformation(sourceCoefficients);
        this.scalingCoefficients = aTransformation.scalingCoefficients();
        double[][] horizontalWaveletCoefficients = aTransformation.horizontalWaveletCoefficients();
        double[][] verticalWaveletCoefficients = aTransformation.verticalWaveletCoefficients();
        double[][] diagonalWaveletCoefficients = aTransformation.diagonalWaveletCoefficients();

        waveletCoefficients = new double[][][] {horizontalWaveletCoefficients, verticalWaveletCoefficients, diagonalWaveletCoefficients};

        BufferedImage imageCoefficients = generateImage(sourceCoefficients, new Point(4, 4), Constants.Luminance);
        BufferedImage imageScalingCoefficients = generateImage(scalingCoefficients, new Point(4, 4), Constants.Luminance);
        BufferedImage imageHorizontalWaveletCoefficients = generateImage(horizontalWaveletCoefficients, new Point(4, 4), Constants.Luminance);
        BufferedImage imageVerticalWaveletCoefficients = generateImage(verticalWaveletCoefficients, new Point(4, 4), Constants.Luminance);
        BufferedImage imageDiagonalWaveletCoefficients = generateImage(diagonalWaveletCoefficients, new Point(4, 4), Constants.Luminance);

        BufferedImage imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(imageScalingCoefficients,
                                                                                     imageHorizontalWaveletCoefficients,
                                                                                     imageVerticalWaveletCoefficients,
                                                                                     imageDiagonalWaveletCoefficients);

        File aDirectory = new File("ResultImages");
        ImageUtility.writeImage(imageCoefficients, aDirectory.getName() + "/Wavelet901.jpg");
        ImageUtility.writeImage(imageScalingCoefficients, aDirectory.getName() + "/Wavelet902.jpg");
        ImageUtility.writeImage(imageHorizontalWaveletCoefficients, aDirectory.getName() + "/Wavelet903.jpg");
        ImageUtility.writeImage(imageVerticalWaveletCoefficients, aDirectory.getName() + "/Wavelet904.jpg");
        ImageUtility.writeImage(imageDiagonalWaveletCoefficients, aDirectory.getName() + "/Wavelet905.jpg");
        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet906.jpg");

        // スケーリング係数を元データにして、ネストされたスケーリング係数とウェーブレット展開係数を順変換で求める。
        aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficients);
        double[][] nestScalingCoefficients = aTransformation.scalingCoefficients();
        double[][] nestHorizontalWaveletCoefficients = aTransformation.horizontalWaveletCoefficients();
        double[][] nestVerticalWaveletCoefficients = aTransformation.verticalWaveletCoefficients();
        double[][] nestDiagonalWaveletCoefficients = aTransformation.diagonalWaveletCoefficients();

        double[][][] nestWaveletCoefficients = new double[][][] {nestHorizontalWaveletCoefficients, nestVerticalWaveletCoefficients, nestDiagonalWaveletCoefficients};
        aTransformation = new DiscreteWavelet2dTransformation(nestScalingCoefficients, nestWaveletCoefficients);
        double[][] nestRecomposedCoefficients = aTransformation.recomposedCoefficients();

        double[][] interactiveHorizontalWaveletCoefficients = new double[horizontalWaveletCoefficients.length][];


        for(int index = 0; index < horizontalWaveletCoefficients.length; index++)
        {
            interactiveHorizontalWaveletCoefficients[index] = horizontalWaveletCoefficients[index].clone();
        }
        double[][] interactiveVerticalWaveletCoefficients = new double[verticalWaveletCoefficients.length][];
        for(int index = 0; index < horizontalWaveletCoefficients.length; index++){
            interactiveVerticalWaveletCoefficients[index] = verticalWaveletCoefficients[index].clone();
        }
        double[][] interactiveDiagonalWaveletCoefficients = new double[diagonalWaveletCoefficients.length][];
        for(int index = 0; index < horizontalWaveletCoefficients.length; index++){
            interactiveDiagonalWaveletCoefficients[index] = diagonalWaveletCoefficients[index].clone();
        }
        for (double[] row : interactiveHorizontalWaveletCoefficients) {
            Arrays.fill(row, 0.0d);
        }
        for (double[] row : interactiveVerticalWaveletCoefficients) {
            Arrays.fill(row, 0.0d);
        }
        for (double[] row : interactiveDiagonalWaveletCoefficients) {
            Arrays.fill(row, 0.0d);
        }

        imageHorizontalWaveletCoefficients = generateImage(interactiveHorizontalWaveletCoefficients, new Point(4, 4), Constants.Luminance);
        imageVerticalWaveletCoefficients = generateImage(interactiveVerticalWaveletCoefficients, new Point(4, 4), Constants.Luminance);
        imageDiagonalWaveletCoefficients = generateImage(interactiveDiagonalWaveletCoefficients, new Point(4, 4), Constants.Luminance);

        imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(imageScalingCoefficients,
                                                                                     imageHorizontalWaveletCoefficients,
                                                                                     imageVerticalWaveletCoefficients,
                                                                                     imageDiagonalWaveletCoefficients);

        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet908.jpg");


        interactiveWaveletCoefficients = new double[][][] {interactiveHorizontalWaveletCoefficients, interactiveVerticalWaveletCoefficients,interactiveDiagonalWaveletCoefficients};

        aTransformation = new DiscreteWavelet2dTransformation(nestRecomposedCoefficients, interactiveWaveletCoefficients);
        double[][] recomposedCoefficients = aTransformation.recomposedCoefficients();
        BufferedImage imageRecomposedCoefficients = generateImage(recomposedCoefficients, new Point(4, 4), Constants.Luminance);
        ImageUtility.writeImage(imageRecomposedCoefficients, aDirectory.getName() + "/Wavelet907.jpg");
        

    }

    public void doEarth(){
        File aDirectory = new File("ResultImages");
        if (aDirectory.exists() == false) { aDirectory.mkdir(); }
        this.sourceCoefficientsArray = Wavelet2dModel.dataEarth();
        

        double[][] luminanceSourceCoefficients = sourceCoefficientsArray[0];
        double[][] redSourceCoefficients = sourceCoefficientsArray[1];
        double[][] greenSourceCoefficients = sourceCoefficientsArray[2];
        double[][] blueSourceCoefficients = sourceCoefficientsArray[3];

        Point scaleFactor = new Point(2, 2);
        
        recomposedCoefficientsArray[0] = perform(luminanceSourceCoefficients, scaleFactor, Constants.Luminance);
        recomposedCoefficientsArray[1] = perform(redSourceCoefficients, scaleFactor, Constants.Red);
        recomposedCoefficientsArray[2] = perform(greenSourceCoefficients, scaleFactor, Constants.Green);
        recomposedCoefficientsArray[3] = perform(blueSourceCoefficients, scaleFactor, Constants.Blue);

        int width = recomposedCoefficientsArray[0].length;
        int height = recomposedCoefficientsArray[0][0].length;

        BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = recomposedCoefficientsArray[1][x][y];
                double g = recomposedCoefficientsArray[2][x][y];
                double b = recomposedCoefficientsArray[3][x][y];
                int aRGB = ColorUtility.convertRGBtoINT(r, g, b);
                anImage.setRGB(x, y, aRGB);
            }
        }
        ImageUtility.writeImage(anImage, aDirectory.getName() + "/Wavelet901.jpg");
        

        
        BufferedImage imageHorizontalWaveletCoefficientsArray = generateImage(horizontalWaveletCoefficientsArray[0], scaleFactor, 0);
        BufferedImage imageVerticalWaveletCoefficientsArray = generateImage(verticalWaveletCoefficientsArray[0], scaleFactor, 0);
        BufferedImage imageDiagonalWaveletCoefficientsArray = generateImage(diagonalWaveletCoefficientsArray[0], scaleFactor, 0);
        
        BufferedImage imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(anImage,
                                                                                     imageHorizontalWaveletCoefficientsArray,
                                                                                     imageVerticalWaveletCoefficientsArray,
                                                                                     imageDiagonalWaveletCoefficientsArray);

        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet906.jpg");
        
        recomposedCoefficientsArray[0] = perform(luminanceSourceCoefficients, scaleFactor, Constants.Luminance);
        recomposedCoefficientsArray[1] = perform(redSourceCoefficients, scaleFactor, Constants.Red);
        recomposedCoefficientsArray[2] = perform(greenSourceCoefficients, scaleFactor, Constants.Green);
        recomposedCoefficientsArray[3] = perform(blueSourceCoefficients, scaleFactor, Constants.Blue);
        
        return;

    }

    public void doSmalltalkBalloon(){
        File aDirectory = new File("ResultImages");
        if (aDirectory.exists() == false) { aDirectory.mkdir(); }
        this.sourceCoefficientsArray = Wavelet2dModel.dataSmalltalkBalloon();

        double[][] luminanceSourceCoefficients = sourceCoefficientsArray[0];
        double[][] redSourceCoefficients = sourceCoefficientsArray[1];
        double[][] greenSourceCoefficients = sourceCoefficientsArray[2];
        double[][] blueSourceCoefficients = sourceCoefficientsArray[3];

        Point scaleFactor = new Point(2, 2);

        recomposedCoefficientsArray[0] = perform(luminanceSourceCoefficients, scaleFactor, Constants.Luminance);
        recomposedCoefficientsArray[1] = perform(redSourceCoefficients, scaleFactor, Constants.Red);
        recomposedCoefficientsArray[2] = perform(greenSourceCoefficients, scaleFactor, Constants.Green);
        recomposedCoefficientsArray[3] = perform(blueSourceCoefficients, scaleFactor, Constants.Blue);
        
        int width = recomposedCoefficientsArray[0].length;
        int height = recomposedCoefficientsArray[0][0].length;

        BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = recomposedCoefficientsArray[1][x][y];
                double g = recomposedCoefficientsArray[2][x][y];
                double b = recomposedCoefficientsArray[3][x][y];
                int aRGB = ColorUtility.convertRGBtoINT(r, g, b);
                anImage.setRGB(x, y, aRGB);
            }
        }
        ImageUtility.writeImage(anImage, aDirectory.getName() + "/Wavelet901.jpg");



        BufferedImage imageHorizontalWaveletCoefficientsArray = generateImage(horizontalWaveletCoefficientsArray[0], scaleFactor, 0);
        BufferedImage imageVerticalWaveletCoefficientsArray = generateImage(verticalWaveletCoefficientsArray[0], scaleFactor, 0);
        BufferedImage imageDiagonalWaveletCoefficientsArray = generateImage(diagonalWaveletCoefficientsArray[0], scaleFactor, 0);

        BufferedImage imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(anImage,
                                                                                     imageHorizontalWaveletCoefficientsArray,
                                                                                     imageVerticalWaveletCoefficientsArray,
                                                                                     imageDiagonalWaveletCoefficientsArray);

        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet906.jpg");
        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet999.jpg");
        System.out.println("aaa");

        recomposedCoefficientsArray[0] = perform(luminanceSourceCoefficients, scaleFactor, Constants.Luminance);
        recomposedCoefficientsArray[1] = perform(redSourceCoefficients, scaleFactor, Constants.Red);
        recomposedCoefficientsArray[2] = perform(greenSourceCoefficients, scaleFactor, Constants.Green);
        recomposedCoefficientsArray[3] = perform(blueSourceCoefficients, scaleFactor, Constants.Blue);

        return;
    }


    protected double[][] perform(double[][] sourceDataMatrix, Point scaleFactor, int rgbFlag){
        double[][] sourceCoefficients = sourceDataMatrix;
        DiscreteWavelet2dTransformation aTransformation = new DiscreteWavelet2dTransformation(sourceCoefficients);
        this.scalingCoefficientsArray[rgbFlag] = aTransformation.scalingCoefficients();
        horizontalWaveletCoefficientsArray[rgbFlag] = aTransformation.horizontalWaveletCoefficients();
        verticalWaveletCoefficientsArray[rgbFlag] = aTransformation.verticalWaveletCoefficients();
        diagonalWaveletCoefficientsArray[rgbFlag] = aTransformation.diagonalWaveletCoefficients();
        waveletCoefficients = new double[][][] {horizontalWaveletCoefficientsArray[rgbFlag], verticalWaveletCoefficientsArray[rgbFlag], diagonalWaveletCoefficientsArray[rgbFlag]};

        BufferedImage imageSourceCoefficients = Wavelet2dModel.generateImage(sourceCoefficients, scaleFactor, rgbFlag);
        BufferedImage imageScalingCoefficients = Wavelet2dModel.generateImage(scalingCoefficientsArray[rgbFlag], scaleFactor, rgbFlag);
        BufferedImage imageHorizontalWaveletCoefficients = Wavelet2dModel.generateImage(horizontalWaveletCoefficientsArray[rgbFlag], scaleFactor, Constants.Luminance);
        BufferedImage imageVerticalWaveletCoefficients = Wavelet2dModel.generateImage(verticalWaveletCoefficientsArray[rgbFlag], scaleFactor, Constants.Luminance);
        BufferedImage imageDiagonalWaveletCoefficients = Wavelet2dModel.generateImage(diagonalWaveletCoefficientsArray[rgbFlag], scaleFactor, Constants.Luminance);

        BufferedImage imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(imageScalingCoefficients,
                                                                                                 imageHorizontalWaveletCoefficients,
                                                                                     imageVerticalWaveletCoefficients,
                                                                                     imageDiagonalWaveletCoefficients);
        // その画像をファイルに書く。
        File aDirectory = new File("ResultImages");

        aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficientsArray[rgbFlag]);
        double[][] nestScalingCoefficients = aTransformation.scalingCoefficients();
        double[][] nestHorizontalWaveletCoefficients = aTransformation.horizontalWaveletCoefficients();
        double[][] nestVerticalWaveletCoefficients = aTransformation.verticalWaveletCoefficients();
        double[][] nestDiagonalWaveletCoefficients = aTransformation.diagonalWaveletCoefficients();

        double[][][] nestWaveletCoefficients = new double[][][] {nestHorizontalWaveletCoefficients, nestVerticalWaveletCoefficients, nestDiagonalWaveletCoefficients};
        aTransformation = new DiscreteWavelet2dTransformation(nestScalingCoefficients, nestWaveletCoefficients);
        double[][] nestRecomposedCoefficients = aTransformation.recomposedCoefficients();

        interactiveHorizontalWaveletCoefficientsArray[rgbFlag] = new double[horizontalWaveletCoefficientsArray[rgbFlag].length][];
        for(int index = 0; index < horizontalWaveletCoefficientsArray[rgbFlag].length; index++)
        {
            interactiveHorizontalWaveletCoefficientsArray[rgbFlag][index] = horizontalWaveletCoefficientsArray[rgbFlag][index].clone();
        }
        interactiveVerticalWaveletCoefficientsArray[rgbFlag] = new double[verticalWaveletCoefficientsArray[rgbFlag].length][];
        for(int index = 0; index < horizontalWaveletCoefficientsArray[rgbFlag].length; index++){
            interactiveVerticalWaveletCoefficientsArray[rgbFlag][index] = verticalWaveletCoefficientsArray[rgbFlag][index].clone();
        }
        interactiveDiagonalWaveletCoefficientsArray[rgbFlag] = new double[diagonalWaveletCoefficientsArray[rgbFlag].length][];
        for(int index = 0; index < horizontalWaveletCoefficientsArray[rgbFlag].length; index++){
            interactiveDiagonalWaveletCoefficientsArray[rgbFlag][index] = diagonalWaveletCoefficientsArray[rgbFlag][index].clone();
        }

        for (double[] row : interactiveHorizontalWaveletCoefficientsArray[rgbFlag]) { 
            Arrays.fill(row, 0.0d);
        }
        for (double[] row : interactiveVerticalWaveletCoefficientsArray[rgbFlag]) {
            Arrays.fill(row, 0.0d);
        }
        for (double[] row : interactiveDiagonalWaveletCoefficientsArray[rgbFlag]) {
            Arrays.fill(row, 0.0d);
        }

        imageHorizontalWaveletCoefficients = generateImage(interactiveHorizontalWaveletCoefficientsArray[rgbFlag], new Point(4, 4), Constants.Luminance);
        imageVerticalWaveletCoefficients = generateImage(interactiveVerticalWaveletCoefficientsArray[rgbFlag], new Point(4, 4), Constants.Luminance);
        imageDiagonalWaveletCoefficients = generateImage(interactiveDiagonalWaveletCoefficientsArray[rgbFlag], new Point(4, 4), Constants.Luminance);

        imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(imageScalingCoefficients,
                                                                                     imageHorizontalWaveletCoefficients,
                                                                                     imageVerticalWaveletCoefficients,
                                                                                     imageDiagonalWaveletCoefficients);

        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet9"+ rgbFlag +"8.jpg");

        interactiveWaveletCoefficients = new double[][][] {horizontalWaveletCoefficientsArray[rgbFlag], verticalWaveletCoefficientsArray[rgbFlag],diagonalWaveletCoefficientsArray[rgbFlag]};

        aTransformation = new DiscreteWavelet2dTransformation(nestRecomposedCoefficients, interactiveWaveletCoefficients);
        double[][] recomposedCoefficients = aTransformation.recomposedCoefficients();
        BufferedImage imageRecomposedCoefficients = generateImage(recomposedCoefficients, new Point(4, 4), Constants.Luminance);
        ImageUtility.writeImage(imageRecomposedCoefficients, aDirectory.getName() + "/Wavelet9"+rgbFlag+"7.jpg");

        return recomposedCoefficients;
    }

    public void openEarth(){
        flag = 2;
        doEarth();
        computeRecomposed();
        actionPerformed();

        return;
    }

    public void openSampleCoefficients(){
        flag = 1;
        doSampleCoefficients();
        computeRecomposedCoefficients();
        actionPerformed();
        return;
    }

    public void openSmalltalkBalloon(){
        flag = 3;
        doSmalltalkBalloon();
        computeRecomposed();
        actionPerformed();

        return;
    }

    public void AllCoefficients(){
        if(flag == 1){
            double[][] sourceCoefficients = Wavelet2dModel.dataSampleCoefficients();
            DiscreteWavelet2dTransformation aTransformation = new DiscreteWavelet2dTransformation(sourceCoefficients);
            this.scalingCoefficients = aTransformation.scalingCoefficients();
            double[][] horizontalWaveletCoefficients = aTransformation.horizontalWaveletCoefficients();
            double[][] verticalWaveletCoefficients = aTransformation.verticalWaveletCoefficients();
            double[][] diagonalWaveletCoefficients = aTransformation.diagonalWaveletCoefficients();

            for(int i = 0; i < horizontalWaveletCoefficients.length; i++){
                for(int j = 0; j < horizontalWaveletCoefficients[0].length; j++){
                    interactiveWaveletCoefficients[0][i][j] = horizontalWaveletCoefficients[i][j];
                    interactiveWaveletCoefficients[1][i][j] = verticalWaveletCoefficients[i][j];
                    interactiveWaveletCoefficients[2][i][j] = diagonalWaveletCoefficients[i][j];
                }
            }
            computeRecomposedCoefficients();
        }
        if(flag == 2 || flag == 3){
            for(int i = 0; i < horizontalWaveletCoefficientsArray[0].length; i++){
                for(int j = 0; j < horizontalWaveletCoefficientsArray[0][0].length; j++){
                    interactiveHorizontalWaveletCoefficientsArray[0][i][j] = horizontalWaveletCoefficientsArray[0][i][j];
                    interactiveHorizontalWaveletCoefficientsArray[1][i][j] = horizontalWaveletCoefficientsArray[1][i][j];
                    interactiveHorizontalWaveletCoefficientsArray[2][i][j] = horizontalWaveletCoefficientsArray[2][i][j];
                    interactiveHorizontalWaveletCoefficientsArray[3][i][j] = horizontalWaveletCoefficientsArray[3][i][j];
                    interactiveVerticalWaveletCoefficientsArray[0][i][j] = verticalWaveletCoefficientsArray[0][i][j];
                    interactiveVerticalWaveletCoefficientsArray[1][i][j] = verticalWaveletCoefficientsArray[1][i][j];
                    interactiveVerticalWaveletCoefficientsArray[2][i][j] = verticalWaveletCoefficientsArray[2][i][j];
                    interactiveVerticalWaveletCoefficientsArray[3][i][j] = verticalWaveletCoefficientsArray[3][i][j];
                    interactiveDiagonalWaveletCoefficientsArray[0][i][j] = diagonalWaveletCoefficientsArray[0][i][j];
                    interactiveDiagonalWaveletCoefficientsArray[1][i][j] = diagonalWaveletCoefficientsArray[1][i][j];
                    interactiveDiagonalWaveletCoefficientsArray[2][i][j] = diagonalWaveletCoefficientsArray[2][i][j];
                    interactiveDiagonalWaveletCoefficientsArray[3][i][j] = diagonalWaveletCoefficientsArray[3][i][j];
                }
            }
            computeRecomposed();
        }
        actionPerformed();
    }

    public void doClearCoeffients(){
        if(flag == 1){
            for (double[] row : interactiveWaveletCoefficients[0]) { 
                Arrays.fill(row, 0.0d);
            }
            for (double[] row : interactiveWaveletCoefficients[1]) {
                Arrays.fill(row, 0.0d);
            }
            for (double[] row : interactiveWaveletCoefficients[2]) {
                Arrays.fill(row, 0.0d);
            }
            computeRecomposedCoefficients();
        }
        if(flag == 2 || flag == 3){
            for(int i = 0; i < interactiveDiagonalWaveletCoefficientsArray.length; i++){
                for (double[] row : interactiveHorizontalWaveletCoefficientsArray[i]) { 
                    Arrays.fill(row, 0.0d);
                }
                for (double[] row : interactiveVerticalWaveletCoefficientsArray[i]) {
                    Arrays.fill(row, 0.0d);
                }
                for (double[] row : interactiveDiagonalWaveletCoefficientsArray[i]) {
                    Arrays.fill(row, 0.0d);
                }
            }
            
            computeRecomposed();
        }

        actionPerformed();

    }

    /**
     * マウスクリックイベントが発生した際の動作を受け取る。
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/7/14
     * @param aPoint      マウスの座標
     * @param aMouseEvent マウスイベント
     */
    public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
        
        int[] whichPoint = new int[] {0, 0};
        
        whichPoint=whereClicked(aPoint);
        
        int range = 5;


        if(flag ==1 ){
            range = 1;
            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    int x = whichPoint[0] + dx;
                    int y = whichPoint[1] + dy;

                    // 境界チェック：xとyが配列の範囲内にあるか確認
                    // ここで sizeX と sizeY は配列のサイズを示す変数と仮定しています
                    if (x >= 0 && y >= 0 && x < interactiveWaveletCoefficients[0].length && y < interactiveWaveletCoefficients[0][0].length) {
                        // 各配列に対する変更
                        for (int i = 0; i < 3; i++) {
                            interactiveWaveletCoefficients[i][x][y] = waveletCoefficients[i][x][y];
                        }
                    }
                }
            }
            computeRecomposedCoefficients();
        }else {
            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    int x = whichPoint[0] + dx;
                    int y = whichPoint[1] + dy;

                    // 境界チェック：xとyが配列の範囲内にあるか確認
                    // ここで sizeX と sizeY は配列のサイズを示す変数と仮定しています
                    if (x >= 0 && y >= 0 && x < interactiveDiagonalWaveletCoefficientsArray[0].length && y < interactiveDiagonalWaveletCoefficientsArray[0][0].length) {
                        // 各配列に対する変更
                        for (int i = 0; i < 3; i++) {
                            interactiveDiagonalWaveletCoefficientsArray[i][x][y] = diagonalWaveletCoefficientsArray[i][x][y];
                            interactiveVerticalWaveletCoefficientsArray[i][x][y] = verticalWaveletCoefficientsArray[i][x][y];
                            interactiveHorizontalWaveletCoefficientsArray[i][x][y] = horizontalWaveletCoefficientsArray[i][x][y];
                        }
                    }
                }
            }
            computeRecomposed();
        }
        

        
        actionPerformed();
    }

    /**
     * マウスクリックイベントが発生した際の動作を受け取る。
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/7/14
     * @param aPoint      マウスの座標
     * @param aMouseEvent マウスイベント
     */
    public void mouseClickeAndKey(Point aPoint, MouseEvent aMouseEvent) {
        
        int[] whichPoint = new int[] {0, 0};
        
        whichPoint=whereClicked(aPoint);
        
        int range = 5;


        if(flag ==1 ){
            range = 1;
            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    int x = whichPoint[0] + dx;
                    int y = whichPoint[1] + dy;

                    // 境界チェック：xとyが配列の範囲内にあるか確認
                    // ここで sizeX と sizeY は配列のサイズを示す変数と仮定しています
                    if (x >= 0 && y >= 0 && x < interactiveWaveletCoefficients[0].length && y < interactiveWaveletCoefficients[0][0].length) {
                        // 各配列に対する変更
                        for (int i = 0; i < 3; i++) {
                            interactiveWaveletCoefficients[i][x][y] = 0.0d;
                        }
                    }
                }
            }
            computeRecomposedCoefficients();
        }else {
            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    int x = whichPoint[0] + dx;
                    int y = whichPoint[1] + dy;

                    // 境界チェック：xとyが配列の範囲内にあるか確認
                    // ここで sizeX と sizeY は配列のサイズを示す変数と仮定しています
                    if (x >= 0 && y >= 0 && x < interactiveDiagonalWaveletCoefficientsArray[0].length && y < interactiveDiagonalWaveletCoefficientsArray[0][0].length) {
                        // 各配列に対する変更
                        for (int i = 0; i < 3; i++) {
                            interactiveDiagonalWaveletCoefficientsArray[i][x][y] = 0.0d;
                            interactiveVerticalWaveletCoefficientsArray[i][x][y] = 0.0d;
                            interactiveHorizontalWaveletCoefficientsArray[i][x][y] = 0.0d;
                        }
                    }
                }
            }
            computeRecomposed();
        }
        

        
        actionPerformed();
    }

    /**
     * クリックされた座標からどの配列を変更するか決める
     * @author Shibata
     * @version 1.0
     * @date 2023/7/7
     * @param aPoi
     * @return int型
     */
    public int[] whereClicked(Point aPoint) {
        int[] whichPoint = new int[] {0, 0}; // どこをクリックしたか
        int imageWidth = 0; // 画像の横幅
        int imageHeight = 0; // 画像の縦幅
        int imageWidthDivisionLengthX = 0;   // 画像サイズ/配列数
        int imageWidthDivisionLengthY = 0;   // 画像サイズ/配列数
        int wLength = 0;
        int hLength = 0;

        BufferedImage image = null; //画像
        try {
            image = ImageIO.read(new File("ResultImages/Wavelet908.jpg"));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading Wavelet908.jpg");

        }
        if(flag == 1){
            wLength = interactiveWaveletCoefficients[0].length;
            hLength = interactiveWaveletCoefficients[0][0].length;
        }
        if(flag == 2 || flag == 3){
            wLength = interactiveHorizontalWaveletCoefficientsArray[0].length;
            hLength = interactiveHorizontalWaveletCoefficientsArray[0][0].length;
        }
        imageWidth = image.getWidth()/2;
        imageHeight = image.getHeight()/2;
        if(aPoint.getX() > imageWidth){
            int p =(int)aPoint.getX() - imageWidth;
            imageWidthDivisionLengthX = imageWidth / wLength;
            whichPoint[0] = p / imageWidthDivisionLengthX;
        }
        else{
            imageWidthDivisionLengthX = imageWidth / wLength;
            whichPoint[0] = (int)aPoint.getX() / imageWidthDivisionLengthX;
        }
        
        if(aPoint.getY() > imageHeight){
            int p =(int)aPoint.getY() - imageHeight;
            imageWidthDivisionLengthY = imageHeight / hLength;
            whichPoint[1] = p / imageWidthDivisionLengthY;
        }
        else{
            imageWidthDivisionLengthY = imageHeight / hLength;
            whichPoint[1] = (int)aPoint.getY() / imageWidthDivisionLengthY;
        }
        
        return whichPoint;
    }

    public void computeRecomposedCoefficients(){
        BufferedImage imageScalingCoefficients = generateImage(scalingCoefficients, new Point(4, 4), Constants.Luminance);
        BufferedImage imageHorizontalWaveletCoefficients = generateImage(interactiveWaveletCoefficients[0], new Point(4, 4), Constants.Luminance);
        BufferedImage imageVerticalWaveletCoefficients = generateImage(interactiveWaveletCoefficients[1], new Point(4, 4), Constants.Luminance);
        BufferedImage imageDiagonalWaveletCoefficients = generateImage(interactiveWaveletCoefficients[2], new Point(4, 4), Constants.Luminance);

        File aDirectory = new File("ResultImages");
        BufferedImage imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(imageScalingCoefficients,
                                                                                     imageHorizontalWaveletCoefficients,
                                                                                     imageVerticalWaveletCoefficients,
                                                                                     imageDiagonalWaveletCoefficients);

        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet908.jpg");
        
        DiscreteWavelet2dTransformation aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficients, interactiveWaveletCoefficients);
        double[][] recomposedCoefficients = aTransformation.recomposedCoefficients();
        BufferedImage imageRecomposedCoefficients = generateImage(recomposedCoefficients, new Point(4, 4), Constants.Luminance);
        ImageUtility.writeImage(imageRecomposedCoefficients, aDirectory.getName() + "/Wavelet907.jpg");

    }


    public void computeRecomposed(){
        int width = recomposedCoefficientsArray[0].length;
        int height = recomposedCoefficientsArray[0][0].length;
        BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = recomposedCoefficientsArray[1][x][y];
                double g = recomposedCoefficientsArray[2][x][y];
                double b = recomposedCoefficientsArray[3][x][y];
                int aRGB = ColorUtility.convertRGBtoINT(r, g, b);
                anImage.setRGB(x, y, aRGB);
            }
        }
        BufferedImage imageHorizontalWaveletCoefficients = generateImage(interactiveHorizontalWaveletCoefficientsArray[0], new Point(2, 2), Constants.Luminance);
        BufferedImage imageVerticalWaveletCoefficients = generateImage(interactiveVerticalWaveletCoefficientsArray[0], new Point(2, 2), Constants.Luminance);
        BufferedImage imageDiagonalWaveletCoefficients = generateImage(interactiveDiagonalWaveletCoefficientsArray[0], new Point(2, 2), Constants.Luminance);

        File aDirectory = new File("ResultImages");
        BufferedImage imageScalingWaveletCoefficients = Wavelet2dModel.generateImage(anImage,
                                                                                     imageHorizontalWaveletCoefficients,
                                                                                     imageVerticalWaveletCoefficients,
                                                                                     imageDiagonalWaveletCoefficients);

        ImageUtility.writeImage(imageScalingWaveletCoefficients, aDirectory.getName() + "/Wavelet908.jpg");
        
        interactiveWaveletCoefficients = new double[][][] {interactiveHorizontalWaveletCoefficientsArray[0], interactiveVerticalWaveletCoefficientsArray[0],interactiveDiagonalWaveletCoefficientsArray[0]};
        DiscreteWavelet2dTransformation aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficientsArray[0], interactiveWaveletCoefficients);
        double[][] luminanceRecomposedCoefficients = aTransformation.recomposedCoefficients();

        interactiveWaveletCoefficients = new double[][][] {interactiveHorizontalWaveletCoefficientsArray[1], interactiveVerticalWaveletCoefficientsArray[1],interactiveDiagonalWaveletCoefficientsArray[1]};
        aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficientsArray[1], interactiveWaveletCoefficients);
        double[][] redRecomposedCoefficients = aTransformation.recomposedCoefficients();

        interactiveWaveletCoefficients = new double[][][] {interactiveHorizontalWaveletCoefficientsArray[2], interactiveVerticalWaveletCoefficientsArray[2],interactiveDiagonalWaveletCoefficientsArray[2]};
        aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficientsArray[2], interactiveWaveletCoefficients);
        double[][] greenRecomposedCoefficients = aTransformation.recomposedCoefficients();
        
        interactiveWaveletCoefficients = new double[][][] {interactiveHorizontalWaveletCoefficientsArray[3], interactiveVerticalWaveletCoefficientsArray[3],interactiveDiagonalWaveletCoefficientsArray[3]};
        aTransformation = new DiscreteWavelet2dTransformation(scalingCoefficientsArray[3], interactiveWaveletCoefficients);
        double[][] blueRecomposedCoefficients = aTransformation.recomposedCoefficients();

        width = luminanceRecomposedCoefficients.length;
        height = luminanceRecomposedCoefficients[0].length;
        BufferedImage aImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = redRecomposedCoefficients[x][y];
                double g = greenRecomposedCoefficients[x][y];
                double b = blueRecomposedCoefficients[x][y];
                int aRGB = ColorUtility.convertRGBtoINT(r, g, b);
                aImage.setRGB(x, y, aRGB);
            }
        }
        ImageUtility.writeImage(aImage, aDirectory.getName() + "/Wavelet907.jpg");
        return;

    }

    /**
     * アクションイベントが発生した際の動作を受け取る。
     * 
     * @author
     * @version 1.0
     * @date 2023/7/14
     * @param anActionEvent アクションイベント
     */
    public void actionPerformed() {
        sourceCoefficientsPaneModel.picture(ImageUtility.readImage("ResultImages/Wavelet901.jpg"));
        scalingAndWaveletCoefficientsPaneModel.picture(ImageUtility.readImage("ResultImages/Wavelet906.jpg"));
        interactiveScalingAndWaveletCoefficientsPaneModel.picture(ImageUtility.readImage("ResultImages/Wavelet908.jpg"));
        recomposedCoefficientsPaneModel.picture(ImageUtility.readImage("ResultImages/Wavelet907.jpg"));
        sourceCoefficientsPaneModel.changed();
        scalingAndWaveletCoefficientsPaneModel.changed();
        interactiveScalingAndWaveletCoefficientsPaneModel.changed();
        recomposedCoefficientsPaneModel.changed();
    }


    /**
     * 離散ウェーブレット2次元変換のための元データ。
     */
    public static double[][] dataSampleCoefficients()
    {
        int size = 64;
        double[][] aMatrix = new double[size][size];
        for (int index = 0; index < aMatrix.length; index++)
        {
            Arrays.fill(aMatrix[index], 0.2d);
        }
        ///////////////Arrays.stream(aMatrix).forEach(row -> Arrays.fill(row, 0.2d));
        for (int index = 5; index < size - 5; index++)
        {
            aMatrix[5][index] = 1.0d;
            aMatrix[size - 6][index] = 1.0d;
            aMatrix[index][5] = 1.0d;
            aMatrix[index][size - 6] = 1.0d;
            aMatrix[index][index] = 1.0d;
            aMatrix[index][size - index - 1] = 1.0d;
        }
    /*	IntStream.range(5, size - 5).forEach(index -> {
        aMatrix[5][index] = 1.0d;
        aMatrix[size - 6][index] = 1.0d;
        aMatrix[index][5] = 1.0d;
        aMatrix[index][size - 6] = 1.0d;
        aMatrix[index][index] = 1.0d;
        aMatrix[index][size - index - 1] = 1.0d;
        });*////////////////////////////////////////////////////////
        return aMatrix;
    }

    /**
     * 離散ウェーブレット2次元変換のための元データ(Earth)。
     */
    public static double[][][] dataEarth()
    {
        BufferedImage anImage = Wavelet2dModel.imageEarth();
        return Wavelet2dModel.lrgbMatrixes(anImage);
    }

    /**
     * 離散ウェーブレット2次元変換のための元データ(SmalltalkBalloon)。
     */
    public static double[][][] dataSmalltalkBalloon()
    {
        BufferedImage anImage = Wavelet2dModel.imageSmalltalkBalloon();
        return Wavelet2dModel.lrgbMatrixes(anImage);
    }

    /**
     * 2次元配列の中を指定された値で初期化する。
     */
    public static void fill(double[][] aMatrix, double aValue)
    {
        for (int index = 0; index < aMatrix.length; index++)
        {
            double[] anArray = aMatrix[index];
            Arrays.fill(anArray, aValue);
        }
        //////////////////////////////Arrays.stream(aMatrix).forEach(anArray -> Arrays.fill(anArray, aValue));
        return;
    }

    /**
     * 離散ウェーブレット2次元変換のためのデータ値(valueMatrix)を画像に変換して応答する。
     */
    public static BufferedImage generateImage(double[][][] valueMatrixArray, double maxValue)
    {
        double[][] valueMatrix = valueMatrixArray[0];
        int width = valueMatrix.length;
        int height = valueMatrix[0].length;
        BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D aGraphics = anImage.createGraphics();

        if (valueMatrixArray[1] == null || valueMatrixArray[2] == null || valueMatrixArray[3] == null)
        {
            // Grayscale
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double aValue = Math.abs(valueMatrix[x][y]);
                    int brightness = (int)Math.round((aValue / maxValue) * 255.0d);
                    Color aColor = new Color(brightness, brightness, brightness);
                    aGraphics.setColor(aColor);
                    aGraphics.fillRect(x, y, 1, 1);
                }
            }
            /* 
            IntStream.range(0, height).forEach(y -> {
                IntStream.range(0, width).forEach(x -> {
                    double aValue = Math.abs(valueMatrix[x][y]);
                    int brightness = (int) Math.round((aValue / maxValue) * 255.0d);
                    Color aColor = new Color(brightness, brightness, brightness);
                    aGraphics.setColor(aColor);
                    aGraphics.fillRect(x, y, 1, 1);
                });
            });
            *///////////////////////////////////////////////////////////////
        }
        else
        {
            // Color
            int[][] redMatrix = new int[width][height];
            int[][] greenMatrix = new int[width][height];
            int[][] blueMatrix = new int[width][height];
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double redValue = Math.abs(valueMatrixArray[1][x][y]);
                    int red = (int)Math.round((redValue / maxValue) * 255.0d);
                    double greenValue = Math.abs(valueMatrixArray[2][x][y]);
                    int green = (int)Math.round((greenValue / maxValue) * 255.0d);
                    double blueValue = Math.abs(valueMatrixArray[3][x][y]);
                    int blue = (int)Math.round((blueValue / maxValue) * 255.0d);
                    Color aColor = new Color(red, green, blue);
                    aGraphics.setColor(aColor);
                    aGraphics.fillRect(x, y, 1, 1);
                }
            }
            /*
            IntStream.range(0, height).forEach(y -> {
                IntStream.range(0, width).forEach(x -> {
                    double redValue = Math.abs(valueMatrixArray[1][x][y]);
                    int red = (int) Math.round((redValue / maxValue) * 255.0d);
                    double greenValue = Math.abs(valueMatrixArray[2][x][y]);
                    int green = (int) Math.round((greenValue / maxValue) * 255.0d);
                    double blueValue = Math.abs(valueMatrixArray[3][x][y]);
                    int blue = (int) Math.round((blueValue / maxValue) * 255.0d);
                    Color aColor = new Color(red, green, blue);
                    aGraphics.setColor(aColor);
                    aGraphics.fillRect(x, y, 1, 1);
                });
            });
            *//////////////////////////////////////////////
        }

        return anImage;
    }

    /**
     * 離散ウェーブレット2次元変換のためのデータ値(valueMatrix)を画像に変換して応答する。
     */
    public static BufferedImage generateImage(double[][] valueMatrix, Point scaleFactor, int rgbFlag)
    {
        int width = valueMatrix.length;
        int height = valueMatrix[0].length;
        int w = width * scaleFactor.x;
        int h = height * scaleFactor.y;
        BufferedImage anImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D aGraphics = anImage.createGraphics();

        double maxValue = Double.MIN_VALUE;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                double aValue = Math.abs(valueMatrix[x][y]);
                maxValue = Math.max(aValue, maxValue);
            }
        }
        /*
        AtomicReference<Double> atomicMaxValue = new AtomicReference<>(Double.NEGATIVE_INFINITY);

        IntStream.range(0, height).forEach(y ->
            IntStream.range(0, width).forEach(x -> {
                double aValue = Math.abs(valueMatrix[y][x]);
                atomicMaxValue.updateAndGet(currentMax -> Math.max(aValue, currentMax));
            })
        );

        double maxValue = atomicMaxValue.get();
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                double aValue = Math.abs(valueMatrix[x][y]);
                int luminance = (int)Math.round((aValue / maxValue) * 255.0d);
                Color aColor = new Color(luminance, luminance, luminance);
                if (rgbFlag == Constants.Red  ) { aColor = new Color(luminance, 0, 0); }
                if (rgbFlag == Constants.Green) { aColor = new Color(0, luminance, 0); }
                if (rgbFlag == Constants.Blue ) { aColor = new Color(0, 0, luminance); }
                aGraphics.setColor(aColor);
                aGraphics.fillRect(x * scaleFactor.x, y * scaleFactor.y, scaleFactor.x, scaleFactor.y);
            }
        }


        /*
        IntStream.range(0, height).forEach(y -> {
            IntStream.range(0, width).forEach(x -> {
                double aValue = Math.abs(valueMatrix[x][y]);
                int luminance = (int) Math.round((aValue / maxValue) * 255.0d);
                Color aColor;
                if (rgbFlag == Constants.Red) {
                    aColor = new Color(luminance, 0, 0);
                } else if (rgbFlag == Constants.Green) {
                    aColor = new Color(0, luminance, 0);
                } else if (rgbFlag == Constants.Blue) {
                    aColor = new Color(0, 0, luminance);
                } else {
                    aColor = new Color(luminance, luminance, luminance);
                }
                aGraphics.setColor(aColor);
                aGraphics.fillRect(x * scaleFactor.x, y * scaleFactor.y, scaleFactor.x, scaleFactor.y);
            });
        });
        ????????????????????????????????????????????*/
        return anImage;
    }

    /**
     * 離散ウェーブレット2次元変換の係数群を画像に変換して応答する。
     */
    public static BufferedImage generateImage(BufferedImage imageScalingCoefficients,
                                              BufferedImage imageHorizontalWaveletCoefficients,
                                              BufferedImage imageVerticalWaveletCoefficients,
                                              BufferedImage imageDiagonalWaveletCoefficients)
    {
        int w = imageScalingCoefficients.getWidth();
        int h = imageScalingCoefficients.getHeight();
        int width = w + imageHorizontalWaveletCoefficients.getWidth();
        int height = h + imageVerticalWaveletCoefficients.getHeight();
        BufferedImage anImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D aGraphics = anImage.createGraphics();
        aGraphics.drawImage(imageScalingCoefficients, 0, 0, null);
        aGraphics.drawImage(imageHorizontalWaveletCoefficients, w, 0, null);
        aGraphics.drawImage(imageVerticalWaveletCoefficients, 0, h, null);
        aGraphics.drawImage(imageDiagonalWaveletCoefficients, w, h, null);
        return anImage;
    }

    /**
     * 離散ウェーブレット2次元変換のための元データ(Earth)。
     */
    public static BufferedImage imageEarth()
    {
        String aString = "SampleImages/imageEarth512x256.jpg";
        BufferedImage anImage = ImageUtility.readImage(aString);
        return anImage;
    }

    /**
     * 離散ウェーブレット2次元変換のための元データ(SmalltalkBalloon)。
     */
    public static BufferedImage imageSmalltalkBalloon()
    {
        String aString = "SampleImages/imageSmalltalkBalloon256x256.jpg";
        BufferedImage anImage = ImageUtility.readImage(aString);
        return anImage;
    }

    /**
     * 離散ウェーブレット2次元変換のための元データ(SmalltalkBalloon)。
     */
    public static double[][][] lrgbMatrixes(BufferedImage anImage)
    {
        int width = anImage.getWidth();
        int height = anImage.getHeight();
        double[][] luminanceMatrix = new double[width][height];
        double[][] redMatrix = new double[width][height];
        double[][] greenMatrix = new double[width][height];
        double[][] blueMatrix = new double[width][height];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int aRGB = anImage.getRGB(x, y);
                luminanceMatrix[x][y] = ColorUtility.luminanceFromRGB(aRGB);
                double[] rgb = ColorUtility.convertINTtoRGB(aRGB);
                redMatrix[x][y] = rgb[0];
                greenMatrix[x][y] = rgb[1];
                blueMatrix[x][y] = rgb[2];
            }
        }
        /*
        IntStream.range(0, height).forEach(y -> {
            IntStream.range(0, width).forEach(x -> {
                int aRGB = anImage.getRGB(x, y);
                luminanceMatrix[x][y] = ColorUtility.luminanceFromRGB(aRGB);
                double[] rgb = ColorUtility.convertINTtoRGB(aRGB);
                redMatrix[x][y] = rgb[0];
                greenMatrix[x][y] = rgb[1];
                blueMatrix[x][y] = rgb[2];
            });
        });
        *////////////////////////////////////////////////////////
        return new double[][][] { luminanceMatrix, redMatrix, greenMatrix, blueMatrix };
    }
}
