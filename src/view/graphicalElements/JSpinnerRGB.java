package view.graphicalElements;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A JSpinner with lower and upper bounds.
 * @author tommy
 *
 */
@SuppressWarnings("serial")
public class JSpinnerRGB extends JSpinner implements ChangeListener {

	
	private DialogColor dialogColor;
	private int lowerBound;
	private int upperBound;
	
	
	public JSpinnerRGB(int lowerBound, int upperBound, DialogColor dialogColor) {
		super();
		this.dialogColor = dialogColor;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.setValue(lowerBound);
		this.addChangeListener(this);
	}

	public int getInt() {
		return (int) getValue();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this) {
			/* volume > upperBound or volume < lowerBound is not allowed */
			if((int) getValue() > upperBound) {
				setValue(upperBound);
			}
			else if((int) getValue() < lowerBound) {
				setValue(lowerBound);
			}
			dialogColor.rgbChanged();
		}
	}
	
	
	
}
