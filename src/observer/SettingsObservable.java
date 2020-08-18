package observer;

/**
 * Observer pattern - Data observable.
 * @author tommy
 *
 */
public interface SettingsObservable {
	
	public void registerObserver(SettingsObserver o);
	public void removeObserver(SettingsObserver o);
	
	
	public void notifySettingChanged();
}

