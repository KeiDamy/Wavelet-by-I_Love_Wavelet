package wavelet;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

/**
 * 離散ウェーブレット変換の抽象モデル。
 */
public abstract class WaveletModel extends mvc.Model {
	/**
	 * 精度。
	 */
	public static final double accuracy = 1.0E-5d;

	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
	}

    public void doClearCoeffients() {
    }

	public void openEarth() {
	}

    public void openSampleCoefficients() {
    }

	public void openSmalltalkBalloon() {
	}

	public void AllCoefficients() {
	}

    public void mouseClickeAndKey(Point point, MouseEvent aMouseEvent) {
    };
}
