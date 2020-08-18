package safeLoad;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import observer.SettingsObservable;
import observer.SettingsObserver;

public class Settings implements SettingsObservable {
	
	public static final String LETTER_LEFT       = "L";
	public static final String LETTER_RIGHT      = "R";
	public static final String LETTER_MIDDLE     = "M";
	//public static final String LETTER_WHEEL_DOWN = "W-D";
	//public static final String LETTER_WHEEL_UP   = "W-U";
	
	public static final int CIRCLE_RADIUS_MIN   = 4;
	public static final int CIRCLE_RADIUS_MAX   = 64;
	
	public static final int CIRCLE_LINE_SIZE_MIN   = 1;
	public static final int CIRCLE_LINE_SIZE_MAX   = 16;

	public static final int ARROW_THICKNESS_MIN   = 12;
	public static final int ARROW_THICKNESS_MAX   = 64;

	public static final int ARROW_DISTANCE_MIN   = 0;
	public static final int ARROW_DISTANCE_MAX   = 64;
	
	public static final long MILLIS_ARROWS       = 1000;

	//public static final int ARROW_LINE_SIZE_MIN   = 1;
	//public static final int ARROW_LINE_SIZE_MAX   = 16;

	public static final int LETTER_SIZE_MIN   = 4;
	public static final int LETTER_SIZE_MAX   = 32;
	
	public static final int LETTER_DISTANCE_X_MIN   = 4;
	public static final int LETTER_DISTANCE_X_MAX   = 64;
	
	public static final int LETTER_DISTANCE_Y_MIN   = -64;
	public static final int LETTER_DISTANCE_Y_MAX   = 64;
	
	
	
	// standards	
	private static final boolean CIRCLE_LEFT_SELECTED   = false;
	private static final boolean CIRCLE_RIGHT_SELECTED  = false;
	private static final boolean CIRCLE_MIDDLE_SELECTED = false;
	//private static final boolean ARROW_WHEEL_SELECTED   = false;
	
	private static final boolean DISC_LEFT_SELECTED          = true;
	private static final boolean DISC_RIGHT_SELECTED         = true;
	private static final boolean DISC_MIDDLE_SELECTED        = false;
	private static final boolean FILLED_ARROW_WHEEL_SELECTED = false;
	
	private static final boolean LETTER_LEFT_SELECTED   = true;
	private static final boolean LETTER_RIGHT_SELECTED  = true;
	private static final boolean LETTER_MIDDLE_SELECTED = false;
	//private static final boolean LETTER_WHEEL_SELECTED  = false;
	
	
	private static final int RADIUS_CIRCLE          = 20;
	private static final int LINE_SIZE_CIRCLE       = 2;
	private static final int RADIUS_DISC            = 20;
	
	//private static final int THICKNESS_ARROW        = 16;
	//private static final int LINE_SIZE_ARROW        = 2;
	private static final int THICKNESS_FILLED_ARROW = 20;
	private static final int DISTANCE_FILLED_ARROW  = 8;
	
	
	private static final int LETTER_SIZE_START      = 20;
	private static final int LETTER_DISTANCE_X      = 20;
	private static final int LETTER_DISTANCE_Y      = 0;
	
	private static final Color COL_CIRCLE_LEFT   = new Color(255,0,0,255);
	private static final Color COL_CIRCLE_RIGHT  = new Color(0,0,255,255);
	private static final Color COL_CIRCLE_MIDDLE = new Color(0,102,0,255);
	//private static final Color COL_ARROW_WHEEL   = new Color(0,0,255,255);

	private static final Color COL_DISC_LEFT           = new Color(255,0,0,150);
	private static final Color COL_DISC_RIGHT          = new Color(0,0,255,150);
	private static final Color COL_DISC_MIDDLE         = new Color(0,102,0,150);
	private static final Color COL_FILLLED_ARROW_WHEEL = new Color(0,0,0,150);
	
	private static final Color COL_LETTER_LEFT   = new Color(255,0,0,255);
	private static final Color COL_LETTER_RIGHT  = new Color(0,0,255,255);
	private static final Color COL_LETTER_MIDDLE = new Color(0,102,0,255);
	//private static final Color COL_LETTER_WHEEL  = new Color(0,0,255,255);
	
	
	
	// current settings	
	private boolean circleLeftSelected   = CIRCLE_LEFT_SELECTED;
	private boolean circleRightSelected  = CIRCLE_RIGHT_SELECTED;
	private boolean circleMiddleSelected = CIRCLE_MIDDLE_SELECTED;
	//private boolean arrowWheelSelected   = ARROW_WHEEL_SELECTED;
	
	private boolean discLeftSelected         = DISC_LEFT_SELECTED;
	private boolean discRightSelected        = DISC_RIGHT_SELECTED;
	private boolean discMiddleSelected       = DISC_MIDDLE_SELECTED;
	private boolean filledArrowWheelSelected = FILLED_ARROW_WHEEL_SELECTED;
	
	private boolean letterLeftSelected   = LETTER_LEFT_SELECTED;
	private boolean letterRightSelected  = LETTER_RIGHT_SELECTED;
	private boolean letterMiddleSelected = LETTER_MIDDLE_SELECTED;
	//private boolean letterWheelSelected  = LETTER_WHEEL_SELECTED;
	
	
	private int radiusCircle         = RADIUS_CIRCLE;
	private int lineSizeCircle       = LINE_SIZE_CIRCLE;
	private int radiusDisc           = RADIUS_DISC;
	//private int thicknessArrow       = THICKNESS_ARROW;
	//private int lineSizeArrow        = LINE_SIZE_ARROW;
	private int thicknessFilledArrow = THICKNESS_FILLED_ARROW;
	private int distanceFilledArrow  = DISTANCE_FILLED_ARROW;
	private int letterSize           = LETTER_SIZE_START;
	private int letterDistX          = LETTER_DISTANCE_X;
	private int letterDistY          = LETTER_DISTANCE_Y;
	
	private Color circleLeft   = COL_CIRCLE_LEFT;
	private Color circleRight  = COL_CIRCLE_RIGHT;
	private Color circleMiddle = COL_CIRCLE_MIDDLE;
	//private Color arrowWheel   = COL_ARROW_WHEEL;

	private Color discLeft         = COL_DISC_LEFT;
	private Color discRight        = COL_DISC_RIGHT;
	private Color discMiddle       = COL_DISC_MIDDLE;
	private Color filledArrowWheel = COL_FILLLED_ARROW_WHEEL;
	
	private Color letterLeft   = COL_LETTER_LEFT;
	private Color letterRight  = COL_LETTER_RIGHT;
	private Color letterMiddle = COL_LETTER_MIDDLE;
	//private Color letterWheel  = COL_LETTER_WHEEL;
	
	
	
	private static Settings uniqueDataBase = new Settings();
	public static Settings getInstance() {
		return uniqueDataBase;
	}
	
	
	private Settings() {
		load();
	}


	
	public void resetSettings() {

		circleLeftSelected   = CIRCLE_LEFT_SELECTED;
		circleRightSelected  = CIRCLE_RIGHT_SELECTED;
		circleMiddleSelected = CIRCLE_MIDDLE_SELECTED;
		//arrowWheelSelected   = ARROW_WHEEL_SELECTED;
		
		discLeftSelected         = DISC_LEFT_SELECTED;
		discRightSelected        = DISC_RIGHT_SELECTED;
		discMiddleSelected       = DISC_MIDDLE_SELECTED;
		filledArrowWheelSelected = FILLED_ARROW_WHEEL_SELECTED;
		
		letterLeftSelected   = LETTER_LEFT_SELECTED;
		letterRightSelected  = LETTER_RIGHT_SELECTED;
		letterMiddleSelected = LETTER_MIDDLE_SELECTED;
		//private boolean letterWheelSelected  = LETTER_WHEEL_SELECTED;
		
		
		radiusCircle         = RADIUS_CIRCLE;
		lineSizeCircle       = LINE_SIZE_CIRCLE;
		radiusDisc           = RADIUS_DISC;
		//thicknessArrow       = THICKNESS_ARROW;
		//lineSizeArrow        = LINE_SIZE_ARROW;
		thicknessFilledArrow = THICKNESS_FILLED_ARROW;
		distanceFilledArrow  = DISTANCE_FILLED_ARROW;
		letterSize           = LETTER_SIZE_START;
		letterDistX          = LETTER_DISTANCE_X;
		letterDistY          = LETTER_DISTANCE_Y;
		
		circleLeft   = COL_CIRCLE_LEFT;
		circleRight  = COL_CIRCLE_RIGHT;
		circleMiddle = COL_CIRCLE_MIDDLE;
		//arrowWheel   = COL_ARROW_WHEEL;

		discLeft         = COL_DISC_LEFT;
		discRight        = COL_DISC_RIGHT;
		discMiddle       = COL_DISC_MIDDLE;
		filledArrowWheel = COL_FILLLED_ARROW_WHEEL;
		
		letterLeft   = COL_LETTER_LEFT;
		letterRight  = COL_LETTER_RIGHT;
		letterMiddle = COL_LETTER_MIDDLE;
		//letterWheel  = COL_LETTER_WHEEL;
		
		notifySettingChanged();
	}
	
	
	

	/******************************************/
	/************* SAFE AND LOAD **************/
	/******************************************/
	
	private void safe() {
		Preferences prefs = Preferences.userNodeForPackage(getClass());

		prefs.putBoolean(Key.CIRCLE_LEFT_SELECTED, circleLeftSelected);
		prefs.putBoolean(Key.CIRCLE_RIGHT_SELECTED, circleRightSelected);
		prefs.putBoolean(Key.CIRCLE_MIDDLE_SELECTED, circleMiddleSelected);
		
		prefs.putBoolean(Key.DISC_LEFT_SELECTED, discLeftSelected);
		prefs.putBoolean(Key.DISC_RIGHT_SELECTED, discRightSelected);
		prefs.putBoolean(Key.DISC_MIDDLE_SELECTED, discMiddleSelected);
		prefs.putBoolean(Key.FILLED_ARROW_WHEEL_SELECTED, filledArrowWheelSelected);

		prefs.putBoolean(Key.LETTER_LEFT_SELECTED, letterLeftSelected);
		prefs.putBoolean(Key.LETTER_RIGHT_SELECTED, letterRightSelected);
		prefs.putBoolean(Key.LETTER_MIDDLE_SELECTED, letterMiddleSelected);
		

		prefs.putInt(Key.RADIUS_CIRCLE, radiusCircle);
		prefs.putInt(Key.LINE_SIZE_CIRCLE, lineSizeCircle);
		prefs.putInt(Key.RADIUS_DISC, radiusDisc);
		prefs.putInt(Key.THICKNESS_FILLED_ARROW, thicknessFilledArrow);
		prefs.putInt(Key.DISTANCE_FILLED_ARROW, distanceFilledArrow);
		prefs.putInt(Key.LETTER_SIZE_START, letterSize);
		prefs.putInt(Key.LETTER_DISTANCE_X, letterDistX);
		prefs.putInt(Key.LETTER_DISTANCE_Y, letterDistY);

		
		prefs.putInt(Key.COL_CIRCLE_LEFT_R, circleLeft.getRed());
		prefs.putInt(Key.COL_CIRCLE_LEFT_G, circleLeft.getGreen());
		prefs.putInt(Key.COL_CIRCLE_LEFT_B, circleLeft.getBlue());
		prefs.putInt(Key.COL_CIRCLE_LEFT_A, circleLeft.getAlpha());
		prefs.putInt(Key.COL_CIRCLE_RIGHT_R, circleRight.getRed());
		prefs.putInt(Key.COL_CIRCLE_RIGHT_G, circleRight.getGreen());
		prefs.putInt(Key.COL_CIRCLE_RIGHT_B, circleRight.getBlue());
		prefs.putInt(Key.COL_CIRCLE_RIGHT_A, circleRight.getAlpha());
		prefs.putInt(Key.COL_CIRCLE_MIDDLE_R, circleMiddle.getRed());
		prefs.putInt(Key.COL_CIRCLE_MIDDLE_G, circleMiddle.getGreen());
		prefs.putInt(Key.COL_CIRCLE_MIDDLE_B, circleMiddle.getBlue());
		prefs.putInt(Key.COL_CIRCLE_MIDDLE_A, circleMiddle.getAlpha());

		prefs.putInt(Key.COL_DISC_LEFT_R, discLeft.getRed());
		prefs.putInt(Key.COL_DISC_LEFT_G, discLeft.getGreen());
		prefs.putInt(Key.COL_DISC_LEFT_B, discLeft.getBlue());
		prefs.putInt(Key.COL_DISC_LEFT_A, discLeft.getAlpha());
		prefs.putInt(Key.COL_DISC_RIGHT_R, discRight.getRed());
		prefs.putInt(Key.COL_DISC_RIGHT_G, discRight.getGreen());
		prefs.putInt(Key.COL_DISC_RIGHT_B, discRight.getBlue());
		prefs.putInt(Key.COL_DISC_RIGHT_A, discRight.getAlpha());
		prefs.putInt(Key.COL_DISC_MIDDLE_R, discMiddle.getRed());
		prefs.putInt(Key.COL_DISC_MIDDLE_G, discMiddle.getGreen());
		prefs.putInt(Key.COL_DISC_MIDDLE_B, discMiddle.getBlue());
		prefs.putInt(Key.COL_DISC_MIDDLE_A, discMiddle.getAlpha());
		prefs.putInt(Key.COL_FILLLED_ARROW_WHEEL_R, filledArrowWheel.getRed());
		prefs.putInt(Key.COL_FILLLED_ARROW_WHEEL_G, filledArrowWheel.getGreen());
		prefs.putInt(Key.COL_FILLLED_ARROW_WHEEL_B, filledArrowWheel.getBlue());
		prefs.putInt(Key.COL_FILLLED_ARROW_WHEEL_A, filledArrowWheel.getAlpha());

		prefs.putInt(Key.COL_LETTER_LEFT_R, letterLeft.getRed());
		prefs.putInt(Key.COL_LETTER_LEFT_G, letterLeft.getGreen());
		prefs.putInt(Key.COL_LETTER_LEFT_B, letterLeft.getBlue());
		prefs.putInt(Key.COL_LETTER_LEFT_A, letterLeft.getAlpha());
		prefs.putInt(Key.COL_LETTER_RIGHT_R, letterRight.getRed());
		prefs.putInt(Key.COL_LETTER_RIGHT_G, letterRight.getGreen());
		prefs.putInt(Key.COL_LETTER_RIGHT_B, letterRight.getBlue());
		prefs.putInt(Key.COL_LETTER_RIGHT_A, letterRight.getAlpha());
		prefs.putInt(Key.COL_LETTER_MIDDLE_R, letterMiddle.getRed());
		prefs.putInt(Key.COL_LETTER_MIDDLE_G, letterMiddle.getGreen());
		prefs.putInt(Key.COL_LETTER_MIDDLE_B, letterMiddle.getBlue());
		prefs.putInt(Key.COL_LETTER_MIDDLE_A, letterMiddle.getAlpha());
	}
	
	
	private boolean getPrefs(Preferences prefs, String key, boolean fallBack) {
		return prefs.getBoolean(key, fallBack);
	}
	private int getPrefs(Preferences prefs, String key, int fallBack) {
		return prefs.getInt(key, fallBack);
	}
	private Color getPrefs(Preferences prefs, String keyR, String keyG, String keyB, String keyA, Color fallBack) {
		int r = prefs.getInt(keyR, fallBack.getRed());
		int g = prefs.getInt(keyG, fallBack.getGreen());
		int b = prefs.getInt(keyB, fallBack.getBlue());
		int a = prefs.getInt(keyA, fallBack.getAlpha());
		return new Color(r, g, b, a);
	}
	
	public void load() {
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		if (prefs == null) {
			resetSettings();
			safe();
			return;
		}
		
		circleLeftSelected = getPrefs(prefs, Key.CIRCLE_LEFT_SELECTED, circleLeftSelected);
		circleRightSelected = getPrefs(prefs, Key.CIRCLE_RIGHT_SELECTED, circleRightSelected);
		circleMiddleSelected = getPrefs(prefs, Key.CIRCLE_MIDDLE_SELECTED, circleMiddleSelected);

		discLeftSelected = getPrefs(prefs, Key.DISC_LEFT_SELECTED, discLeftSelected);
		discRightSelected = getPrefs(prefs, Key.DISC_RIGHT_SELECTED, discRightSelected);
		discMiddleSelected = getPrefs(prefs, Key.DISC_MIDDLE_SELECTED, discMiddleSelected);
		filledArrowWheelSelected = getPrefs(prefs, Key.FILLED_ARROW_WHEEL_SELECTED, filledArrowWheelSelected);
		
		letterLeftSelected = getPrefs(prefs, Key.LETTER_LEFT_SELECTED, letterLeftSelected);
		letterRightSelected = getPrefs(prefs, Key.LETTER_RIGHT_SELECTED, letterRightSelected);
		letterMiddleSelected = getPrefs(prefs, Key.LETTER_MIDDLE_SELECTED, letterMiddleSelected);
		

		radiusCircle = getPrefs(prefs, Key.RADIUS_CIRCLE, radiusCircle);
		lineSizeCircle = getPrefs(prefs, Key.LINE_SIZE_CIRCLE, lineSizeCircle);
		radiusDisc = getPrefs(prefs, Key.RADIUS_DISC, radiusDisc);
		thicknessFilledArrow = getPrefs(prefs, Key.THICKNESS_FILLED_ARROW, thicknessFilledArrow);
		distanceFilledArrow = getPrefs(prefs, Key.DISTANCE_FILLED_ARROW, distanceFilledArrow);
		letterSize = getPrefs(prefs, Key.LETTER_SIZE_START, letterSize);
		letterDistX = getPrefs(prefs, Key.LETTER_DISTANCE_X, letterDistX);
		letterDistY = getPrefs(prefs, Key.LETTER_DISTANCE_Y, letterDistY);
		

		circleLeft = getPrefs(prefs, Key.COL_CIRCLE_LEFT_R, Key.COL_CIRCLE_LEFT_G, Key.COL_CIRCLE_LEFT_B, Key.COL_CIRCLE_LEFT_A, circleLeft);
		circleRight = getPrefs(prefs, Key.COL_CIRCLE_RIGHT_R, Key.COL_CIRCLE_RIGHT_G, Key.COL_CIRCLE_RIGHT_B, Key.COL_CIRCLE_RIGHT_A, circleRight);
		circleMiddle = getPrefs(prefs, Key.COL_CIRCLE_MIDDLE_R, Key.COL_CIRCLE_MIDDLE_G, Key.COL_CIRCLE_MIDDLE_B, Key.COL_CIRCLE_MIDDLE_A, circleMiddle);

		discLeft = getPrefs(prefs, Key.COL_DISC_LEFT_R, Key.COL_DISC_LEFT_G, Key.COL_DISC_LEFT_B, Key.COL_DISC_LEFT_A, discLeft);
		discRight = getPrefs(prefs, Key.COL_DISC_RIGHT_R, Key.COL_DISC_RIGHT_G, Key.COL_DISC_RIGHT_B, Key.COL_DISC_RIGHT_A, discRight);
		discMiddle = getPrefs(prefs, Key.COL_DISC_MIDDLE_R, Key.COL_DISC_MIDDLE_G, Key.COL_DISC_MIDDLE_B, Key.COL_DISC_MIDDLE_A, discMiddle);
		filledArrowWheel = getPrefs(prefs, Key.COL_FILLLED_ARROW_WHEEL_R, Key.COL_FILLLED_ARROW_WHEEL_G, Key.COL_FILLLED_ARROW_WHEEL_B, Key.COL_FILLLED_ARROW_WHEEL_A, filledArrowWheel);

		letterLeft = getPrefs(prefs, Key.COL_LETTER_LEFT_R, Key.COL_LETTER_LEFT_G, Key.COL_LETTER_LEFT_B, Key.COL_LETTER_LEFT_A, letterLeft);
		letterRight = getPrefs(prefs, Key.COL_LETTER_RIGHT_R, Key.COL_LETTER_RIGHT_G, Key.COL_LETTER_RIGHT_B, Key.COL_LETTER_RIGHT_A, letterRight);
		letterMiddle = getPrefs(prefs, Key.COL_LETTER_MIDDLE_R, Key.COL_LETTER_MIDDLE_G, Key.COL_LETTER_MIDDLE_B, Key.COL_LETTER_MIDDLE_A, letterMiddle);

	}
	
	
	


	/******************************************/
	/*********** GETTER AND SETTER ************/
	/******************************************/
	
	public boolean isCircleLeftSelected() {
		return circleLeftSelected;
	}
	public void setCircleLeftSelected(boolean circleLeftSelected) {
		this.circleLeftSelected = circleLeftSelected;
		notifySettingChanged();
	}

	public boolean isCircleRightSelected() {
		return circleRightSelected;
	}
	public void setCircleRightSelected(boolean circleRightSelected) {
		this.circleRightSelected = circleRightSelected;
		notifySettingChanged();
	}

	public boolean isCircleMiddleSelected() {
		return circleMiddleSelected;
	}
	public void setCircleMiddleSelected(boolean circleMiddleSelected) {
		this.circleMiddleSelected = circleMiddleSelected;
		notifySettingChanged();
	}

	/*
	public boolean isArrowWheelSelected() {
		return arrowWheelSelected;
	}
	public void setArrowWheelSelected(boolean arrowWheelSelected) {
		this.arrowWheelSelected = arrowWheelSelected;
		notifySettingChanged();
	}*/

	public boolean isDiscLeftSelected() {
		return discLeftSelected;
	}
	public void setDiscLeftSelected(boolean discLeftSelected) {
		this.discLeftSelected = discLeftSelected;
		notifySettingChanged();
	}

	public boolean isDiscRightSelected() {
		return discRightSelected;
	}
	public void setDiscRightSelected(boolean discRightSelected) {
		this.discRightSelected = discRightSelected;
		notifySettingChanged();
	}

	public boolean isDiscMiddleSelected() {
		return discMiddleSelected;
	}
	public void setDiscMiddleSelected(boolean discMiddleSelected) {
		this.discMiddleSelected = discMiddleSelected;
		notifySettingChanged();
	}

	public boolean isFilledArrowWheelSelected() {
		return filledArrowWheelSelected;
	}
	public void setFilledArrowWheelSelected(boolean filledArrowWheelSelected) {
		this.filledArrowWheelSelected = filledArrowWheelSelected;
		notifySettingChanged();
	}

	public boolean isLetterLeftSelected() {
		return letterLeftSelected;
	}
	public void setLetterLeftSelected(boolean letterLeftSelected) {
		this.letterLeftSelected = letterLeftSelected;
		notifySettingChanged();
	}

	public boolean isLetterRightSelected() {
		return letterRightSelected;
	}
	public void setLetterRightSelected(boolean letterRightSelected) {
		this.letterRightSelected = letterRightSelected;
		notifySettingChanged();
	}

	public boolean isLetterMiddleSelected() {
		return letterMiddleSelected;
	}
	public void setLetterMiddleSelected(boolean letterMiddleSelected) {
		this.letterMiddleSelected = letterMiddleSelected;
		notifySettingChanged();
	}
/*
	public boolean isLetterWheelSelected() {
		return letterWheelSelected;
	}
	public void setLetterWheelSelected(boolean letterWheelSelected) {
		this.letterWheelSelected = letterWheelSelected;
		notifySettingChanged();
	}
*/
	
	
	public int getRadiusCircle() {
		return radiusCircle;
	}
	public void setRadiusCircle(int radiusCircle) {
		this.radiusCircle = radiusCircle;
		notifySettingChanged();
	}
	
	public int getLineSizeCircle() {
		return lineSizeCircle;
	}
	public void setLineSizeCircle(int size) {
		this.lineSizeCircle = size;
		notifySettingChanged();
	}

	public int getRadiusDisc() {
		return radiusDisc;
	}
	public void setRadiusDisc(int radiusDisc) {
		this.radiusDisc = radiusDisc;
		notifySettingChanged();
	}
/*
	public int getThicknessArrow() {
		return thicknessArrow;
	}
	public void setThicknessArrow(int thicknessArrow) {
		this.thicknessArrow = thicknessArrow;
		notifySettingChanged();
	}
	
	public int getLineSizeArrow() {
		return lineSizeArrow;
	}
	public void setLineSizeArrow(int size) {
		this.lineSizeArrow = size;
		notifySettingChanged();
	}
*/
	public int getThicknessFilledArrow() {
		return thicknessFilledArrow;
	}
	public void setThicknessFilledArrow(int thicknessFilledArrow) {
		this.thicknessFilledArrow = thicknessFilledArrow;
		notifySettingChanged();
	}
	
	public int getDistanceFilledArrow() {
		return distanceFilledArrow;
	}
	public void setDistanceFilledArrow(int distanceFilledArrow) {
		this.distanceFilledArrow = distanceFilledArrow;
		notifySettingChanged();
	}
	
	public int getLetterSize() {
		return letterSize;
	}
	public void setLetterSize(int letterSize) {
		this.letterSize = letterSize;
		notifySettingChanged();
	}
	
	public int getLetterDistanceX() {
		return letterDistX;
	}
	public void setLetterDistanceX(int dist) {
		this.letterDistX = dist;
		notifySettingChanged();
	}
	
	public int getLetterDistanceY() {
		return letterDistY;
	}
	public void setLetterDistanceY(int dist) {
		this.letterDistY = dist;
		notifySettingChanged();
	}


	
	public Color getColorCircleLeft() {
		return circleLeft;
	}
	public void setColorCircleLeft(Color circleLeft) {
		this.circleLeft = circleLeft;
		notifySettingChanged();
	}

	public Color getColorCircleRight() {
		return circleRight;
	}
	public void setColorCircleRight(Color circleRight) {
		this.circleRight = circleRight;
		notifySettingChanged();
	}

	public Color getColorCircleMiddle() {
		return circleMiddle;
	}
	public void setColorCircleMiddle(Color circleMiddle) {
		this.circleMiddle = circleMiddle;
		notifySettingChanged();
	}
/*
	public Color getColorArrowWheel() {
		return arrowWheel;
	}
	public void setColorArrowWheel(Color arrowWheel) {
		this.arrowWheel = arrowWheel;
		notifySettingChanged();
	}
*/
	public Color getColorDiscLeft() {
		return discLeft;
	}
	public void setColorDiscLeft(Color discLeft) {
		this.discLeft = discLeft;
		notifySettingChanged();
	}

	public Color getColorDiscRight() {
		return discRight;
	}
	public void setColorDiscRight(Color discRight) {
		this.discRight = discRight;
		notifySettingChanged();
	}

	public Color getColorDiscMiddle() {
		return discMiddle;
	}
	public void setColorDiscMiddle(Color discMiddle) {
		this.discMiddle = discMiddle;
		notifySettingChanged();
	}

	public Color getColorFilledArrowWheel() {
		return filledArrowWheel;
	}
	public void setColorFilledArrowWheel(Color filledArrowWheel) {
		this.filledArrowWheel = filledArrowWheel;
		notifySettingChanged();
	}

	public Color getColorLetterLeft() {
		return letterLeft;
	}
	public void setColorLetterLeft(Color letterLeft) {
		this.letterLeft = letterLeft;
		notifySettingChanged();
	}

	public Color getColorLetterRight() {
		return letterRight;
	}
	public void setColorLetterRight(Color letterRight) {
		this.letterRight = letterRight;
		notifySettingChanged();
	}

	public Color getColorLetterMiddle() {
		return letterMiddle;
	}
	public void setColorLetterMiddle(Color letterMiddle) {
		this.letterMiddle = letterMiddle;
		notifySettingChanged();
	}
/*
	public Color getColorLetterWheel() {
		return letterWheel;
	}
	public void setColorLetterWheel(Color letterWheel) {
		this.letterWheel = letterWheel;
		notifySettingChanged();
	}
*/
	
	




	/******************************************/
	/************** OBSERVABLE ****************/
	/******************************************/

	private List<SettingsObserver> observer = new ArrayList<SettingsObserver>();
	
	@Override
	public void registerObserver(SettingsObserver o) {
		if(!observer.contains(o)) {
			observer.add(o);
		}
	}
	@Override
	public void removeObserver(SettingsObserver o) {
		if(observer.contains(o)) {
			observer.remove(o);
		}
	}


	@Override
	public void notifySettingChanged() {
		safe();
		
		for (SettingsObserver o : observer) {
			o.fireSettingChanged();
		}
	}


	public void activateAllCircles(boolean activate) {
		circleLeftSelected   = activate;
		circleRightSelected  = activate;
		circleMiddleSelected = activate;
	//	arrowWheelSelected   = activate;
		notifySettingChanged();
	}
	public void activateAllDiscs(boolean activate) {
		discLeftSelected         = activate;
		discRightSelected        = activate;
		discMiddleSelected       = activate;
		filledArrowWheelSelected = activate;
		notifySettingChanged();
	}
	public void activateAllLetters(boolean activate) {
		letterLeftSelected   = activate;
		letterRightSelected  = activate;
		letterMiddleSelected = activate;
	//	letterWheelSelected  = activate;
		notifySettingChanged();
	}


	public void activateAll(boolean activate) {
		circleLeftSelected   = activate;
		circleRightSelected  = activate;
		circleMiddleSelected = activate;
	//	arrowWheelSelected   = activate;

		discLeftSelected         = activate;
		discRightSelected        = activate;
		discMiddleSelected       = activate;
		filledArrowWheelSelected = activate;
		
		letterLeftSelected   = activate;
		letterRightSelected  = activate;
		letterMiddleSelected = activate;
	//	letterWheelSelected  = activate;
		
		notifySettingChanged();
	}

	
}
