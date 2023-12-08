package wavelet;

import pane.PaneModel;
import pane.PaneView;
import utility.ImageUtility;

import javax.swing.*;

import condition.Condition;
import condition.ValueHolder;

import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.*;

@SuppressWarnings("serial")
public class WaveletPaneView extends pane.PaneView {

    private JPopupMenu popupMenu;

    // /**
    // * modelに関連したビューを作成する。再表示？
    // * @author
    // * @version 1.0
    // * @date 2023/5/26
    // * @param aModel モデル
    // */
    // public WaveletPaneView(WaveletPaneModel aModel) {
    // super(aModel);
    // }

    /**
     * modelとcontrollerに関連したビューを作成する。
     * 
     * @author
     * @version 1.0
     * @date 2023/5/26
     * @param aModel      モデル
     * @param aController コントローラ
     */
    public WaveletPaneView(WaveletPaneModel aModel, WaveletPaneController aController) {
        super(aModel, aController);
        this.intialize();
    }

    /**
     * ポップアップメニューを表示する。
     * 
     * @param aMouseEvent マウスイベント
     */
    public void showPopupMenu(MouseEvent aMouseEvent) {
        if(((WaveletPaneModel)getModel()).listener instanceof Wavelet1dModel) {
            showPopupMenu1d(aMouseEvent);
        } else if(((WaveletPaneModel)getModel()).listener instanceof Wavelet2dModel) {
            showPopupMenu2d(aMouseEvent);
        }
    }

    /**
     * ポップアップメニューを表示する。
     * 
     * @param aMouseEvent マウスイベント
     */
    public void showPopupMenu1d(MouseEvent aMouseEvent) {
        Component component = aMouseEvent.getComponent();
        int x = aMouseEvent.getX();
        int y = aMouseEvent.getY();
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("sample coefficient");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目1が選択された時の処理を追加
                System.out.println("Menu Item 1 Clicked");
            }
        });
        popupMenu.add(menuItem1);

        JMenuItem menuItem2 = new JMenuItem("all coefficients");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目2が選択された時の処理を追加
                System.out.println("Menu Item 2 Clicked");
            }
        });
        popupMenu.addSeparator();//メニューの境界線
        popupMenu.add(menuItem2);

        JMenuItem menuItem3 = new JMenuItem("clear coefficients");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目3が選択された時の処理を追加
                System.out.println("Menu Item 3 Clicked");
                ((WaveletPaneModel)getModel()).listener.doClearCoeffients();
            }

        });
        popupMenu.add(menuItem3);

        popupMenu.show(component, x, y);
    }


    /**
     * ポップアップメニューを表示する。
     * 
     * @param aMouseEvent マウスイベント
     */
    public void showPopupMenu2d(MouseEvent aMouseEvent) {
        Component component = aMouseEvent.getComponent();
        int x = aMouseEvent.getX();
        int y = aMouseEvent.getY();
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem1 = new JMenuItem("sample coefficient");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目1が選択された時の処理を追加
                System.out.println("Menu Item 1 Clicked");
                ((WaveletPaneModel)getModel()).listener.openSampleCoefficients();
            }
        });
        popupMenu.add(menuItem1);

        JMenuItem menuItem2 = new JMenuItem("smalltalk balloon");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目1が選択された時の処理を追加
                System.out.println("Menu Item 2 Clicked");
                ((WaveletPaneModel)getModel()).listener.openSmalltalkBalloon();
            }
        });
        popupMenu.add(menuItem2);

        JMenuItem menuItem3 = new JMenuItem("earth");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目1が選択された時の処理を追加
                System.out.println("Menu Item 3 Clicked");
                ((WaveletPaneModel)getModel()).listener.openEarth();
            }
        });
        popupMenu.add(menuItem3);

        JMenuItem menuItem4 = new JMenuItem("all coefficients");
        menuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目2が選択された時の処理を追加
                System.out.println("Menu Item 4 Clicked");
                ((WaveletPaneModel)getModel()).listener.AllCoefficients();
            }
        });
        popupMenu.addSeparator();//メニューの境界線
        popupMenu.add(menuItem4);

        JMenuItem menuItem5 = new JMenuItem("clear coefficients");
        menuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // メニュー項目3が選択された時の処理を追加
                System.out.println("Menu Item 5 Clicked");
                ((WaveletPaneModel)getModel()).listener.doClearCoeffients();
            }

        });
        popupMenu.add(menuItem5);

        popupMenu.show(component, x, y);
    }

}
