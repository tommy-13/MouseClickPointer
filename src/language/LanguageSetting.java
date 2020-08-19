package language;

import java.util.prefs.Preferences;

public class LanguageSetting {
	
	private final String KEY_LANGUAGE = "lan";
	
	


	private static LanguageSetting uniqueSetting = new LanguageSetting();
	public static LanguageSetting getInstance() {
		return uniqueSetting;
	}
	private LanguageSetting() {
		lanAtStart = getLanguage();
		lanCurrent = getLanguage();
	}

	
	private Language lanAtStart;
	private Language lanCurrent;
	
	private Language getLanguage() {
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		int idLan = prefs.getInt(KEY_LANGUAGE, Language.ENGLISH.getFixedId());
		return Language.getLanguageFromId(idLan);
	}
	
	
	public void setLanguage(Language language) {
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.putInt(KEY_LANGUAGE, language.getFixedId());
		lanCurrent = language;
	}
	
	public Language getCurrentLanguage() {
		return lanCurrent;
	}
	public Language getLanguageAtStart() {
		return lanAtStart;
	}
	
	
}
