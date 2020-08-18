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
public class JSpinnerInteger extends JSpinner implements ChangeListener {

	
	int lowerBound;
	int upperBound;
	
	
	public JSpinnerInteger(int lowerBound, int upperBound, int value) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.setValue(value);
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
				return;
			}
			if((int) getValue() < lowerBound) {
				setValue(lowerBound);
				return;
			}
		}
	}
	
	
	
}
