package wavelet;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utility.ImageUtility;

import condition.ValueHolder;

public class WaveletPaneModel extends pane.PaneModel {

    private String label = "";

    public WaveletModel listener = null;

    /**
     * アクションイベントが発生した際の動作を受け取る。
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param anActionEvent アクションイベント
     */
    public void actionPerformed(ActionEvent anActionEvent) {
        
    }

    public WaveletModel getListener() {
        return this.listener;
    }


    /**
     * マウスクリックイベントが発生した際の動作を受け取る。
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param aPoint      マウスの座標
     * @param aMouseEvent マウスイベント
     */
    public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
        if (this.label == "Interactive Scaling & Wavelet Coefficients") {
            this.listener.mouseClicked(aPoint, aMouseEvent);
        }
    }

    /**
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param anImage 画像
     * @param aString 文字列
     */
    public WaveletPaneModel(BufferedImage anImage, String aString) {
        super();
    }

    /**
     * 現在のラベルを返すメソッド
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/5/26
     * @return label ラベル
     */
    public String label() {
        return label;
    }

    /**
     * ラベルを設定するメソッド
     * 
     * @author Yokokawa
     * @version 1.0
     * @date 2023/6/2
     * @param aString ラベル
     */
    public void label(String aString) {
        label = aString;
        return;
    }

    /**
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param anImage 画像
     * @param aString 文字列
     * @param aModel  モデル
     */
    public WaveletPaneModel(String aString, String name, WaveletModel aModel) {
        super();
        BufferedImage anImage = ImageUtility.readImage(aString);
        this.picture(anImage);
        this.listener = aModel;
        this.label = name;
    }

    /**
     * 現在のユーザからインタラクションを受け付ける状態かどうかを返す。
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @return インタラクションを受け付ける状態かどうか
     */
    public boolean isInteractive() {
        return false;
    }

    /**
     * ポップアップメニューの情報を保持する。
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param aMouseEvent マウスイベント
     * @param aController コントローラ
     */
    public void showPopupMenu(MouseEvent aMouseEvent, WaveletPaneController aController) {
        if (this.label == "Interactive Scaling & Wavelet Coefficients") {
            aController.showPopupMenu(aMouseEvent);
        }
    }

    /**
     * マウスが動いた際の動作を受け取る。
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param aPoint      マウスの座標
     * @param aMouseEvent マウスイベント
     */
    public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
        if (this.label == "Interactive Scaling & Wavelet Coefficients") {
            this.listener.mouseClicked(aPoint, aMouseEvent);
        }
    }

    /**
     * 
     */
    public WaveletPaneModel() {
        super();
    }

    /**
     * 
     */
    public WaveletPaneModel(String aString) {
        super();
        BufferedImage anImage = ImageUtility.readImage(aString);
        this.picture(anImage);
    }

    public void doClearCoeffients(){
        //this.listener.doClearCoeffients();
    }

    public void mouseClickeAndKey(Point point, MouseEvent aMouseEvent) {
        if (this.label == "Interactive Scaling & Wavelet Coefficients") {
            this.listener.mouseClickeAndKey(point, aMouseEvent);
        }
    }



}
