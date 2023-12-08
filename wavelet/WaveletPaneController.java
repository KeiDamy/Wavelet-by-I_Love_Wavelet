package wavelet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.Point;

import condition.Condition;
import condition.ValueHolder;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class WaveletPaneController extends pane.PaneController implements ActionListener,KeyListener {


	/**
	 * マウスが離れたときの動作を定義している。
	 * 
	 * @author Yokokawa
	 * @version 1.0
	 * @date 2023/6/2
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseReleased(MouseEvent aMouseEvent) {
		return;
	}

	/**
	 * マウスが動いたときの動作を定義している。
	 * 
	 * @author
	 * @version 1.0
	 * @date 2023/5/26
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseMoved(MouseEvent aMouseEvent) {
		return;
	}

	/**
	 * ポップアップメニューを表示する。
	 * 
	 * @author
	 * @version 1.0
	 * @date 2023/5/26
	 * @param aMouseEvent マウスイベント
	 */
	public void showPopupMenu(MouseEvent aMouseEvent) {
		// モデルとviewに従う
		System.out.println("showPopupMenu");
		if (SwingUtilities.isRightMouseButton(aMouseEvent)) {
			//WaveletPaneView.showPopupMenu1d(aMouseEvent.getComponent(), aMouseEvent.getX(), aMouseEvent.getY(),(WaveletPaneView) this.getView());
			((WaveletPaneView)getView()).showPopupMenu(aMouseEvent);
		}
		return;
	}

	/**
	 * コマンドボタンをかんち
	 * 
	 * @author
	 * @version 1.0
	 * @date 2023/5/26
	 * @param anActionEvent アクションイベント
	 */
	public void actionPerformed(ActionEvent anActionEvent) {
		try {
    Object source = anActionEvent.getSource();
    if (source instanceof JButton) {
        JButton button = (JButton) source;
        System.out.println(button.getText() + "が押されました。");
	}

    }catch (RuntimeException anException) {
			return;
		}
}

	/**
	 * マウスが押されたときの動作を定義している。
	 * 
	 * @author Yokokawa
	 * @version 1.0
	 * @date 2023/6/2
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseClicked(MouseEvent aMouseEvent) {
		try {
			if (SwingUtilities.isRightMouseButton(aMouseEvent)){
				((WaveletPaneView)getView()).showPopupMenu(aMouseEvent);
				return;
			}
			ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
			WaveletPaneView aView = (WaveletPaneView) super.getView();
			ValueHolder<Point> point = new ValueHolder<Point>();
			point.set(aView.convertViewPointToPicturePoint(aPoint.get()));
			new Condition(() -> point.get() == null).ifTrue(() -> {
				throw new RuntimeException();
			});
			if (aMouseEvent.isControlDown()) {
				((WaveletPaneModel)aView.getModel()).mouseClickeAndKey(point.get(), aMouseEvent);
			} else{
				aView.getModel().mouseClicked(point.get(), aMouseEvent);
			}
		} catch (RuntimeException anException) {
			return;
		}
		return;
	}

	/**
	 * マウスがドラックされたときの動作を定義している。
	 * 
	 * @author Shibata
	 * @version 1.0
	 * @date 2023/6/2
	 * @param aMouseEvent マウスイベント
	 */
	public void mouseDragged(MouseEvent aMouseEvent) {
		try {
			ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
			WaveletPaneView aView = (WaveletPaneView) super.getView();
			ValueHolder<Point> point = new ValueHolder<Point>();
			point.set(aView.convertViewPointToPicturePoint(aPoint.get()));
			new Condition(() -> point.get() == null).ifTrue(() -> {
				throw new RuntimeException();
			});
			if (aMouseEvent.isControlDown()) {
				((WaveletPaneModel)aView.getModel()).mouseClickeAndKey(point.get(), aMouseEvent);
			} else {
				aView.getModel().mouseDragged(point.get(), aMouseEvent);
			}
			// wavelet1DClicked();
		} catch (RuntimeException anException) {
			return;
		}
		return;
	}

	/**
	 * 上位コンストラクタを継承するただけのコンストラクタ。
	 * 
	 * @author
	 * @version 1.0
	 * @date 2023/5/26
	 */
	public WaveletPaneController() {
		super();
	}

	/**
	 * 
	 * @author
	 * @version 1.0
	 * @date 2023/5/26
	 */
	public void mousePressed(MouseEvent aMouseEvent) {
		return;
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
	}



}


