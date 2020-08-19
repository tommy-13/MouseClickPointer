package view.general;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import globals.GlobalConstants;
import globals.GlobalFunctions;
import language.Language;
import language.LanguageSetting;
import language.Messages;
import safeLoad.Settings;
import view.graphicalElements.DialogColor;
import view.graphicalElements.JColorPanel;
import view.graphicalElements.JSpinnerInteger;

@SuppressWarnings("serial")
public class HereIsTheMouse extends JFrame implements ActionListener, ChangeListener, MouseListener {

	// same window with settings (save the settings?)
	// activate spot on the mouse

	private Option circleLeftButton;
	private Option circleRightButton;
	private Option circleMiddleButton;
	//private Option arrowWheel;

	private Option discLeftButton;
	private Option discRightButton;
	private Option discMiddleButton;
	private Option filledArrowWheel;
	
	private Option letterLeftButton;
	private Option letterRightButton;
	private Option letterMiddleButton;
	//private Option letterWheel;
	
	private JButton bStart;
	private JButton bStandards;
	private JCheckBox cbActivateAll;
	private JComboBox<String> cbLanguage;
	
	private JCheckBox cbActivateCircles;
	private JSpinnerInteger sRadiusCircle;
	//private JSpinnerInteger sThicknessArrow;
	private JSpinnerInteger sLineSizeCircle;
	//private JSpinnerInteger sLineSizeArrow;
	
	private JCheckBox cbActivateDiscs;
	private JSpinnerInteger sRadiusDisc;
	private JSpinnerInteger sThicknessFilledArrow;
	private JSpinnerInteger sDistanceFilledArrow;
	
	private JCheckBox cbActivateLetters;
	private JSpinnerInteger sSizeLetters;
	private JSpinnerInteger sLettersDistX;
	private JSpinnerInteger sLettersDistY;
			
			
	private final int generalInsets           = 8;
	private final int tabHorizontalSeperation = 80;
			
			
	private JPanel mainPanel;
	

	private final Font font = new Font("Dialog", 0, 20);
	private final Dimension dimSpinner = new Dimension(70, 30);
	private final Dimension dimColorButtons = new Dimension(34, 24);
	// make mouseaction window an observer of changes in the preferences
	
	private MouseActionWindow  mouseActionWindow;
	private GlobalMouseListener globalMouseListener;
	
	
	
	

	public HereIsTheMouse() {


		/* initialize main frame */
		setProgramTitle();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				endProgram();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});

		/* set icon */
		setIconImages(GlobalFunctions.getIconImages());

		/* main Panel containing all except the menu */
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout()); // change layout
		setContentPane(mainPanel);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(20, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(createHeadPanel(), gbc);

		
		
		
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setFont(font);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(20, 20, 10, 20);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		mainPanel.add(tabPane, gbc);
		
		tabPane.addTab(Messages.getString("Tab.Circle"), createCirclePanel());
		tabPane.addTab(Messages.getString("Tab.Disc"), createDiscPanel());
		tabPane.addTab(Messages.getString("Tab.Letter"), createLetterPanel());

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(20, 20, 30, 20);
		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(createFootPanel(), gbc);
		
		initialize();
		

		pack();
		/* center window on screen */
		setLocationRelativeTo(null);
		setBackground(GlobalConstants.BACKGROUND);
		
		setMinimumSize(getSize());
		
		setVisible(true);
	}

	private void addLabelToPanel(JPanel parent, String childTextKey, int gridX, int gridY, Insets margin) {
		JLabel lbl = new JLabel(Messages.getString(childTextKey));
		addToPanel(parent, lbl, gridX, gridY, margin);
	}

	private JSpinnerInteger createSpinner(int spinnerMin, int spinnerMax, int spinnerValue) {
		JSpinnerInteger spinner = new JSpinnerInteger(spinnerMin, spinnerMax, spinnerValue);
		spinner.setPreferredSize(dimSpinner);
		return spinner;
	}

	private void addToPanel(JPanel parent, Component child, int gridX, int gridY, Insets margin) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NORTHWEST;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = (margin == null) ? new Insets(generalInsets, generalInsets, generalInsets, generalInsets) : margin;
		gbc.gridx = gridX;
		gbc.gridy = gridY;
		child.setFont(font);
		parent.add(child, gbc);
	}

	
	private JPanel createCirclePanel() {
		
		Settings prefs = Settings.getInstance();
		
		
		JPanel circlePanel = new JPanel();
		circlePanel.setLayout(new GridBagLayout());
		circlePanel.setBorder(BorderFactory.createEtchedBorder());
		// circlePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		int gbcRow = 0;

		// activate all circles
		cbActivateCircles = new JCheckBox(Messages.getString("Label.ActivateCircles"));
		cbActivateCircles.addActionListener(this);
		cbActivateCircles.setActionCommand(ActionCommand.cbActivateCircles);
		addToPanel(circlePanel, cbActivateCircles, 0, gbcRow++, new Insets(20,generalInsets,30,generalInsets));

		// size of circle
		JLabel lblRadiusCircle = new JLabel(Messages.getString("Label.CircleRadius"));
		addToPanel(circlePanel, lblRadiusCircle, 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sRadiusCircle = createSpinner(Settings.CIRCLE_RADIUS_MIN, Settings.CIRCLE_RADIUS_MAX, prefs.getRadiusCircle());
		sRadiusCircle.addChangeListener(this);
		addToPanel(circlePanel, sRadiusCircle, 3, gbcRow++, null);
		
		JLabel lblLineSizeCircle = new JLabel(Messages.getString("Label.CircleLineSize"));
		addToPanel(circlePanel, lblLineSizeCircle, 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sLineSizeCircle = createSpinner(Settings.CIRCLE_LINE_SIZE_MIN, Settings.CIRCLE_LINE_SIZE_MAX, prefs.getLineSizeCircle());
		sLineSizeCircle.addChangeListener(this);
		addToPanel(circlePanel, sLineSizeCircle, 3, gbcRow++, null);

		// size of arrows for wheel
		/*
		JLabel lblThicknessArrow = new JLabel(Messages.getString("Label.ArrowThickness"));
		addToPanel(circlePanel, lblThicknessArrow, 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sThicknessArrow = createSpinner(Preferences.ARROW_THICKNESS_MIN, Preferences.ARROW_THICKNESS_MAX, prefs.getThicknessArrow());
		sThicknessArrow.addChangeListener(this);
		addToPanel(circlePanel, sThicknessArrow, 3, gbcRow++, null);
		
		JLabel lblLineSizeArrow = new JLabel(Messages.getString("Label.ArrowLineSize"));
		addToPanel(circlePanel, lblLineSizeArrow, 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sLineSizeArrow = createSpinner(Preferences.ARROW_LINE_SIZE_MIN, Preferences.ARROW_LINE_SIZE_MAX, prefs.getLineSizeArrow());
		sLineSizeArrow.addChangeListener(this);
		addToPanel(circlePanel, sLineSizeArrow, 3, gbcRow++, null);
		*/
		
		
		// color of circles: rgba values (current color shown)
		gbcRow = 1;
		circleLeftButton   = new Option(circlePanel, "Label.LeftButtonCircle", gbcRow++, Color.RED);
		circleRightButton  = new Option(circlePanel, "Label.RightButtonCircle", gbcRow++, Color.BLUE);
		circleMiddleButton = new Option(circlePanel, "Label.MiddleButtonCircle", gbcRow++, Color.GREEN);
		//arrowWheel         = new Option(circlePanel, "Label.WheelArrow", gbcRow++, Color.ORANGE);

		circleLeftButton.colorButton.addMouseListener(this);
		circleRightButton.colorButton.addMouseListener(this);
		circleMiddleButton.colorButton.addMouseListener(this);
		//arrowWheel.colorButton.addActionListener(this);
		
		circleLeftButton.checkBox.addActionListener(this);
		circleLeftButton.checkBox.setActionCommand(ActionCommand.cbCircleLeft);
		circleRightButton.checkBox.addActionListener(this);
		circleRightButton.checkBox.setActionCommand(ActionCommand.cbCircleRight);
		circleMiddleButton.checkBox.addActionListener(this);
		circleMiddleButton.checkBox.setActionCommand(ActionCommand.cbCircleMiddle);
		//arrowWheel.checkBox.addActionListener(this);

		return circlePanel;
	}

	private JPanel createDiscPanel() {
		
		Settings prefs = Settings.getInstance();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createEtchedBorder());
		// circlePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		int gbcRow = 0;

		// activate all discs
		cbActivateDiscs = new JCheckBox(Messages.getString("Label.ActivateDiscs"));
		cbActivateDiscs.addActionListener(this);
		cbActivateDiscs.setActionCommand(ActionCommand.cbActivateDiscs);
		addToPanel(panel, cbActivateDiscs, 0, gbcRow++, new Insets(20,generalInsets,30,generalInsets));

		// size of circle
		JLabel lblSizeDisc = new JLabel(Messages.getString("Label.DiscRadius"));
		addToPanel(panel, lblSizeDisc, 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sRadiusDisc = createSpinner(Settings.CIRCLE_RADIUS_MIN, Settings.CIRCLE_RADIUS_MAX, prefs.getRadiusDisc());
		sRadiusDisc.addChangeListener(this);
		addToPanel(panel, sRadiusDisc, 3, gbcRow++, null);

		// size of arrows for wheel
		gbcRow = 3;
		JLabel lblSizeFilledArrow = new JLabel(Messages.getString("Label.FilledArrowThickness"));
		addToPanel(panel, lblSizeFilledArrow, 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sThicknessFilledArrow = createSpinner(Settings.ARROW_THICKNESS_MIN, Settings.ARROW_THICKNESS_MAX, prefs.getThicknessFilledArrow());
		sThicknessFilledArrow.addChangeListener(this);
		addToPanel(panel, sThicknessFilledArrow, 3, gbcRow++, null);

		JLabel lblDistanceFilledArrow = new JLabel(Messages.getString("Label.FilledArrowDistance"));
		addToPanel(panel, lblDistanceFilledArrow, 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sDistanceFilledArrow = createSpinner(Settings.ARROW_DISTANCE_MIN, Settings.ARROW_DISTANCE_MAX, prefs.getDistanceFilledArrow());
		sDistanceFilledArrow.addChangeListener(this);
		addToPanel(panel, sDistanceFilledArrow, 3, gbcRow++, null);
		

		// color of circles: rgba values (current color shown)
		gbcRow = 1;
		discLeftButton   = new Option(panel, "Label.LeftButtonDisc", gbcRow++, prefs.getColorCircleLeft());
		discRightButton  = new Option(panel, "Label.RightButtonDisc", gbcRow++, prefs.getColorCircleRight());
		discMiddleButton = new Option(panel, "Label.MiddleButtonDisc", gbcRow++, prefs.getColorCircleMiddle());
		filledArrowWheel = new Option(panel, "Label.WheelFilledArrow", gbcRow++, prefs.getColorFilledArrowWheel());

		discLeftButton.colorButton.addMouseListener(this);
		discRightButton.colorButton.addMouseListener(this);
		discMiddleButton.colorButton.addMouseListener(this);
		filledArrowWheel.colorButton.addMouseListener(this);

		discLeftButton.checkBox.addActionListener(this);
		discLeftButton.checkBox.setActionCommand(ActionCommand.cbDiscLeft);
		discRightButton.checkBox.addActionListener(this);
		discRightButton.checkBox.setActionCommand(ActionCommand.cbDiscRight);
		discMiddleButton.checkBox.addActionListener(this);
		discMiddleButton.checkBox.setActionCommand(ActionCommand.cbDiscMiddle);
		filledArrowWheel.checkBox.addActionListener(this);
		filledArrowWheel.checkBox.setActionCommand(ActionCommand.cbFilledArrowWheel);

		return panel;
	}

	private JPanel createLetterPanel() {
		
		Settings prefs = Settings.getInstance();
		

		JPanel letterPanel = new JPanel();
		letterPanel.setLayout(new GridBagLayout());
		letterPanel.setBorder(BorderFactory.createEtchedBorder());
		// letterPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		int gbcRow = 0;

		// activate all letters
		cbActivateLetters = new JCheckBox(Messages.getString("Label.ActivateLetters"));
		cbActivateLetters.addActionListener(this);
		cbActivateLetters.setActionCommand(ActionCommand.cbActivateLetters);
		addToPanel(letterPanel, cbActivateLetters, 0, gbcRow++, new Insets(20,generalInsets,30,generalInsets));

		
		// size of circle: slider
		addLabelToPanel(letterPanel, "Label.LetterSize", 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sSizeLetters = createSpinner(Settings.LETTER_SIZE_MIN, Settings.LETTER_SIZE_MAX,
				prefs.getLetterSize());
		sSizeLetters.addChangeListener(this);
		addToPanel(letterPanel, sSizeLetters, 3, gbcRow++, null);
		
		addLabelToPanel(letterPanel, "Label.LetterDistX", 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sLettersDistX = createSpinner(Settings.LETTER_DISTANCE_X_MIN, Settings.LETTER_DISTANCE_X_MAX,	prefs.getLetterDistanceX());
		sLettersDistX.addChangeListener(this);
		addToPanel(letterPanel, sLettersDistX, 3, gbcRow++, null);
		
		addLabelToPanel(letterPanel, "Label.LetterDistY", 2, gbcRow, new Insets(generalInsets,tabHorizontalSeperation,generalInsets,generalInsets));
		sLettersDistY = createSpinner(Settings.LETTER_DISTANCE_Y_MIN, Settings.LETTER_DISTANCE_Y_MAX,	prefs.getLetterDistanceY());
		sLettersDistY.addChangeListener(this);
		addToPanel(letterPanel, sLettersDistY, 3, gbcRow++, null);

		
		
		gbcRow = 1;
		letterLeftButton   = new Option(letterPanel, "Label.LeftButtonLetter", gbcRow++, prefs.getColorLetterLeft());
		letterRightButton  = new Option(letterPanel, "Label.RightButtonLetter", gbcRow++, prefs.getColorLetterRight());
		letterMiddleButton = new Option(letterPanel, "Label.MiddleButtonLetter", gbcRow++, prefs.getColorLetterMiddle());
		//letterWheel        = new Option(letterPanel, "Label.WheelLetter", gbcRow++, Color.ORANGE);
		
		letterLeftButton.colorButton.addMouseListener(this);
		letterRightButton.colorButton.addMouseListener(this);
		letterMiddleButton.colorButton.addMouseListener(this);
		//letterWheel.colorButton.addActionListener(this);
		//letterWheel.colorButton.setActionCommand(ActionCommand.colorButtonLetterWheel);
		
		letterLeftButton.checkBox.addActionListener(this);
		letterLeftButton.checkBox.setActionCommand(ActionCommand.cbLetterLeft);
		letterRightButton.checkBox.addActionListener(this);
		letterRightButton.checkBox.setActionCommand(ActionCommand.cbLetterRight);
		letterMiddleButton.checkBox.addActionListener(this);
		letterMiddleButton.checkBox.setActionCommand(ActionCommand.cbLetterMiddle);
		//letterWheel.checkBox.addActionListener(this);
		//letterWheel.checkBox.setActionCommand(ActionCommand.cbLetterWheel);

		return letterPanel;
	}

	private JPanel createHeadPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		int gbcRow = 0;

		bStandards = new JButton(Messages.getString("Button.ResetSettings"));
		bStandards.addActionListener(this);
		bStandards.setActionCommand(ActionCommand.bStandards);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 100, generalInsets, generalInsets);
		gbc.gridx = 1;
		gbc.gridy = gbcRow;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		bStandards.setFont(font);
		panel.add(bStandards, gbc);
		

		cbActivateAll = new JCheckBox(Messages.getString("Label.ActivateAll"));
		cbActivateAll.addActionListener(this);
		cbActivateAll.setActionCommand(ActionCommand.cbActivateAll);
		addToPanel(panel, cbActivateAll, 0, gbcRow, new Insets(generalInsets,generalInsets,generalInsets,generalInsets));
		
		return panel;
	}

	private JPanel createFootPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		int gbcRow = 0;

		bStart = new JButton(Messages.getString("Button.Start"));
		bStart.addActionListener(this);
		bStart.setActionCommand(ActionCommand.bStart);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(generalInsets, generalInsets, 0, generalInsets);
		gbc.gridx = 0;
		//gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridy = gbcRow;
		bStart.setFont(font);
		panel.add(bStart, gbc);
		
		JLabel emptyLabel = new JLabel("");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(generalInsets, 100, 0, 100);
		gbc.gridx = 1;
		gbc.gridy = gbcRow;
		panel.add(emptyLabel, gbc);
		

		cbLanguage = new JComboBox<String>(Language.getArray());
		cbLanguage.setSelectedItem(LanguageSetting.getInstance().getCurrentLanguage().toString());
		cbLanguage.addActionListener(this);
		cbLanguage.setActionCommand(ActionCommand.cbLanguage);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(generalInsets, generalInsets, 0, generalInsets);
		gbc.gridx = 2;
		//gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridy = gbcRow++;
		cbLanguage.setFont(font);
		panel.add(cbLanguage, gbc);

		return panel;
	}
	

	
	
	
	private void initialize() {
		Settings prefs = Settings.getInstance();

		circleLeftButton.checkBox.setSelected(prefs.isCircleLeftSelected());
		circleLeftButton.colorButton.setColor(prefs.getColorCircleLeft());
		circleRightButton.checkBox.setSelected(prefs.isCircleRightSelected());
		circleRightButton.colorButton.setColor(prefs.getColorCircleRight());
		circleMiddleButton.checkBox.setSelected(prefs.isCircleMiddleSelected());
		circleMiddleButton.colorButton.setColor(prefs.getColorCircleMiddle());
		//arrowWheel.checkBox.setSelected(prefs.isArrowWheelSelected());
		//arrowWheel.colorButton.setColor(prefs.getColorArrowWheel());

		discLeftButton.checkBox.setSelected(prefs.isDiscLeftSelected());
		discLeftButton.colorButton.setColor(prefs.getColorDiscLeft());
		discRightButton.checkBox.setSelected(prefs.isDiscRightSelected());
		discRightButton.colorButton.setColor(prefs.getColorDiscRight());
		discMiddleButton.checkBox.setSelected(prefs.isDiscMiddleSelected());
		discMiddleButton.colorButton.setColor(prefs.getColorDiscMiddle());
		filledArrowWheel.checkBox.setSelected(prefs.isFilledArrowWheelSelected());
		filledArrowWheel.colorButton.setColor(prefs.getColorFilledArrowWheel());

		letterLeftButton.checkBox.setSelected(prefs.isLetterLeftSelected());
		letterLeftButton.colorButton.setColor(prefs.getColorLetterLeft());
		letterRightButton.checkBox.setSelected(prefs.isLetterRightSelected());
		letterRightButton.colorButton.setColor(prefs.getColorLetterRight());
		letterMiddleButton.checkBox.setSelected(prefs.isLetterMiddleSelected());
		letterMiddleButton.colorButton.setColor(prefs.getColorLetterMiddle());
		//letterWheel.checkBox.setSelected(prefs.isLetterWheelSelected());
		//letterWheel.colorButton.setColor(prefs.getColorLetterWheel());

		areAllCheckboxesSelected();
		
		sRadiusCircle.setValue(prefs.getRadiusCircle());
		//sThicknessArrow.setValue(prefs.getThicknessArrow());
		sRadiusDisc.setValue(prefs.getRadiusDisc());
		sThicknessFilledArrow.setValue(prefs.getThicknessFilledArrow());
		sDistanceFilledArrow.setValue(prefs.getDistanceFilledArrow());
		sSizeLetters.setValue(prefs.getLetterSize());
		sLettersDistX.setValue(prefs.getLetterDistanceX());;
		sLettersDistY.setValue(prefs.getLetterDistanceY());;
	}
	
	

	public void endProgram() {
		if (globalMouseListener != null) {
			globalMouseListener.shutdownHook();
		}
		if (mouseActionWindow != null) {
			mouseActionWindow.close();
		}
		System.exit(0);
	}

	private void setProgramTitle() {
		setTitle(Messages.getString("Program.Title"));
	}

	private class Option {

		public JCheckBox checkBox;
		public JColorPanel colorButton;
		//public JSlider slider;

		Option(JPanel parent, String labelLanguageKey, int row, Color color) {
			checkBox = new JCheckBox(Messages.getString(labelLanguageKey));
			addToParent(parent, checkBox, 0, row);

			colorButton = new JColorPanel(color, dimColorButtons);
			addToParent(parent, colorButton, 1, row);

			//slider = new JSlider(0, 255, 150);
			//addToParent(parent, slider, 2, row);
		}

		private void addToParent(JPanel parent, Component child, int gridX, int gridY) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.NORTHWEST;
			gbc.anchor = GridBagConstraints.NORTHWEST;
			gbc.insets = new Insets(generalInsets, generalInsets, generalInsets, generalInsets);
			gbc.gridx = gridX;
			gbc.gridy = gridY;
			child.setFont(font);
			parent.add(child, gbc);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(ActionCommand.bStart)) {
			bStart.setText(Messages.getString("Button.Stop"));
			bStart.setActionCommand(ActionCommand.bStop);
			mouseActionWindow   = new MouseActionWindow();
			globalMouseListener = new GlobalMouseListener(mouseActionWindow);
			Settings.getInstance().registerObserver(mouseActionWindow);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.bStop)) {
			Settings.getInstance().removeObserver(mouseActionWindow);
			globalMouseListener.shutdownHook();
			mouseActionWindow.dispose();
			
			bStart.setText(Messages.getString("Button.Start"));
			bStart.setActionCommand(ActionCommand.bStart);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.bStandards)) {
			Settings.getInstance().resetSettings();
			this.initialize();
			this.repaint();
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbActivateAll)) {
			activateAll(cbActivateAll.isSelected());
			areAllCheckboxesSelected();
			return;
		}
		
		if (event.getActionCommand().equals(ActionCommand.cbActivateCircles)) {
			activateAllCircles(cbActivateCircles.isSelected());
			areAllCheckboxesSelected();
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbActivateDiscs)) {
			activateAllDiscs(cbActivateDiscs.isSelected());
			areAllCheckboxesSelected();
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbActivateLetters)) {
			activateAllLetters(cbActivateLetters.isSelected());
			areAllCheckboxesSelected();
			return;
		}
		
		

		if (event.getActionCommand().equals(ActionCommand.cbCircleLeft)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = circleLeftButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setCircleLeftSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbCircleRight)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = circleRightButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setCircleRightSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbCircleMiddle)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = circleMiddleButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setCircleMiddleSelected(isSelected);
			return;
		}
		/*
		if (event.getActionCommand().equals(ActionCommand.cbArrowWheel)) {
			Preferences prefs = Preferences.getInstance();
			boolean isSelected = arrowWheel.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setArrowWheelSelected(isSelected);
			return;
		}
		*/

		if (event.getActionCommand().equals(ActionCommand.cbDiscLeft)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = discLeftButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setDiscLeftSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbDiscRight)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = discRightButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setDiscRightSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbDiscMiddle)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = discMiddleButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setDiscMiddleSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbFilledArrowWheel)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = filledArrowWheel.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setFilledArrowWheelSelected(isSelected);
			return;
		}

		if (event.getActionCommand().equals(ActionCommand.cbLetterLeft)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = letterLeftButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setLetterLeftSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbLetterRight)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = letterRightButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setLetterRightSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbLetterMiddle)) {
			Settings prefs = Settings.getInstance();
			boolean isSelected = letterMiddleButton.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setLetterMiddleSelected(isSelected);
			return;
		}
		if (event.getActionCommand().equals(ActionCommand.cbLanguage)) {
			String strSelectedLan = (String) cbLanguage.getSelectedItem();
			Language selectedLan  = Language.parseLanguage(strSelectedLan);
			
			LanguageSetting lanSet = LanguageSetting.getInstance();
			Language startLan = lanSet.getLanguageAtStart();
			Language curLan   = lanSet.getCurrentLanguage();
						
			if (selectedLan != curLan) {
				// change the current language
				lanSet.setLanguage(selectedLan);
				
				if (selectedLan != startLan) {
					// show info box
					JOptionPane.showMessageDialog(this, Messages.getString("LanguageChanged.InfoText"));
				}
			}
			return;
		}
		/*
		if (event.getActionCommand().equals(ActionCommand.cbLetterWheel)) {
			Preferences prefs = Preferences.getInstance();
			boolean isSelected = letterWheel.checkBox.isSelected();
			areAllCheckboxesSelected();
			prefs.setLetterWheelSelected(isSelected);
			return;
		}
		*/
		
	}
	
	

	@Override
	public void stateChanged(ChangeEvent event) {
		if (event.getSource().equals(sRadiusCircle)) {
			Settings prefs = Settings.getInstance();
			prefs.setRadiusCircle(sRadiusCircle.getInt());
			return;
		}
		if (event.getSource().equals(sLineSizeCircle)) {
			Settings prefs = Settings.getInstance();
			prefs.setLineSizeCircle(sLineSizeCircle.getInt());
			return;
		}
		/*
		if (event.getSource().equals(sThicknessArrow)) {
			Preferences prefs = Preferences.getInstance();
			prefs.setThicknessArrow(sThicknessArrow.getInt());
			return;
		}
		if (event.getSource().equals(sLineSizeArrow)) {
			Preferences prefs = Preferences.getInstance();
			prefs.setLineSizeArrow(sLineSizeArrow.getInt());
			return;
		}
		*/
		
		if (event.getSource().equals(sThicknessFilledArrow)) {
			Settings prefs = Settings.getInstance();
			prefs.setThicknessFilledArrow(sThicknessFilledArrow.getInt());
			return;
		}
		if (event.getSource().equals(sRadiusDisc)) {
			Settings prefs = Settings.getInstance();
			prefs.setRadiusDisc(sRadiusDisc.getInt());
			return;
		}
		if (event.getSource().equals(sDistanceFilledArrow)) {
			Settings prefs = Settings.getInstance();
			prefs.setDistanceFilledArrow(sDistanceFilledArrow.getInt());
			return;
		}
		if (event.getSource().equals(sSizeLetters)) {
			Settings prefs = Settings.getInstance();
			prefs.setLetterSize(sSizeLetters.getInt());
			return;
		}
		if (event.getSource().equals(sLettersDistX)) {
			Settings prefs = Settings.getInstance();
			prefs.setLetterDistanceX(sLettersDistX.getInt());
			return;
		}
		if (event.getSource().equals(sLettersDistY)) {
			Settings prefs = Settings.getInstance();
			prefs.setLetterDistanceY(sLettersDistY.getInt());
			return;
		}
	}
	

	
	
	private boolean areAllCirclesSelected() {
		boolean allSelected = true;
		allSelected = allSelected && circleLeftButton.checkBox.isSelected();
		allSelected = allSelected && circleRightButton.checkBox.isSelected();
		allSelected = allSelected && circleMiddleButton.checkBox.isSelected();
		//allSelected = allSelected && arrowWheel.checkBox.isSelected();
		cbActivateCircles.setSelected(allSelected);
		return allSelected;
	}
	
	private boolean areAllDiscsSelected() {
		boolean allSelected = true;
		allSelected = allSelected && discLeftButton.checkBox.isSelected();
		allSelected = allSelected && discRightButton.checkBox.isSelected();
		allSelected = allSelected && discMiddleButton.checkBox.isSelected();
		allSelected = allSelected && filledArrowWheel.checkBox.isSelected();
		cbActivateDiscs.setSelected(allSelected);
		return allSelected;
	}
	
	private boolean areAllLettersSelected() {
		boolean allSelected = true;
		allSelected = allSelected && letterLeftButton.checkBox.isSelected();
		allSelected = allSelected && letterRightButton.checkBox.isSelected();
		allSelected = allSelected && letterMiddleButton.checkBox.isSelected();
		//allSelected = allSelected && letterWheel.checkBox.isSelected();
		cbActivateLetters.setSelected(allSelected);
		return allSelected;
	}
	
	private boolean areAllCheckboxesSelected() {
		boolean allSelected = true;
		allSelected = areAllCirclesSelected() && allSelected;
		allSelected = areAllDiscsSelected() && allSelected;
		allSelected = areAllLettersSelected() && allSelected;
		cbActivateAll.setSelected(allSelected);
		return allSelected;
	}

	
	private void activateAllCircles(boolean activate) {
		activateAllCirclesCBs(activate);
		Settings prefs = Settings.getInstance();
		prefs.activateAllCircles(activate);
	}
	private void activateAllCirclesCBs(boolean activate) {
		cbActivateCircles.setSelected(activate);
		circleLeftButton.checkBox.setSelected(activate);
		circleRightButton.checkBox.setSelected(activate);
		circleMiddleButton.checkBox.setSelected(activate);
		//arrowWheel.checkBox.setSelected(activate);
		
	}
	private void activateAllDiscs(boolean activate) {
		activateAllDiscsCBs(activate);
		Settings prefs = Settings.getInstance();
		prefs.activateAllDiscs(activate);
	}
	private void activateAllDiscsCBs(boolean activate) {
		cbActivateDiscs.setSelected(activate);
		discLeftButton.checkBox.setSelected(activate);
		discRightButton.checkBox.setSelected(activate);
		discMiddleButton.checkBox.setSelected(activate);
		filledArrowWheel.checkBox.setSelected(activate);
	}
	private void activateAllLetters(boolean activate) {
		activateAllLettersCBs(activate);
		Settings prefs = Settings.getInstance();
		prefs.activateAllLetters(activate);
	}
	private void activateAllLettersCBs(boolean activate) {
		cbActivateLetters.setSelected(activate);
		letterLeftButton.checkBox.setSelected(activate);
		letterRightButton.checkBox.setSelected(activate);
		letterMiddleButton.checkBox.setSelected(activate);
		//letterWheel.checkBox.setSelected(activate);
	}
	private void activateAll(boolean activate) {
		activateAllCirclesCBs(activate);
		activateAllDiscsCBs(activate);
		activateAllLettersCBs(activate);
		Settings prefs = Settings.getInstance();
		prefs.activateAll(activate);
	}

	
	
	
	@Override
	public void mouseClicked(MouseEvent event) {

		if (event.getSource().equals(circleLeftButton.colorButton)) {
			DialogColor diagColor = new DialogColor(this, Settings.getInstance().getColorCircleLeft());
			Color col = diagColor.getReturnColor();
			Settings.getInstance().setColorCircleLeft(col);
			circleLeftButton.colorButton.setColor(new Color(col.getRed(),col.getGreen(),col.getBlue(),col.getAlpha()));
			this.repaint();
			return;
		}
		if (event.getSource().equals(circleRightButton.colorButton)) {
			DialogColor diagColor = new DialogColor(this, Settings.getInstance().getColorCircleRight());
			Color col = diagColor.getReturnColor();
			Settings.getInstance().setColorCircleRight(col);
			circleRightButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(circleMiddleButton.colorButton)) {
			DialogColor diagColor = new DialogColor(this, Settings.getInstance().getColorCircleMiddle());
			Color col = diagColor.getReturnColor();
			Settings.getInstance().setColorCircleMiddle(col);
			circleMiddleButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(discLeftButton.colorButton)) {
			DialogColor diagColor = new DialogColor(this, Settings.getInstance().getColorDiscLeft());
			Color col = diagColor.getReturnColor();
			Settings.getInstance().setColorDiscLeft(col);
			discLeftButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(discRightButton.colorButton)) {
			DialogColor diagColor = new DialogColor(this, Settings.getInstance().getColorDiscRight());
			Color col = diagColor.getReturnColor();
			Settings.getInstance().setColorDiscRight(col);
			discRightButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(discMiddleButton.colorButton)) {
			DialogColor diagColor = new DialogColor(this, Settings.getInstance().getColorDiscMiddle());
			Color col = diagColor.getReturnColor();
			Settings.getInstance().setColorDiscMiddle(col);
			discMiddleButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(filledArrowWheel.colorButton)) {
			DialogColor diagColor = new DialogColor(this, Settings.getInstance().getColorFilledArrowWheel());
			Color col = diagColor.getReturnColor();
			Settings.getInstance().setColorFilledArrowWheel(col);
			filledArrowWheel.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterLeftButton.colorButton)) {
			Settings prefs = Settings.getInstance();
			DialogColor diagColor = new DialogColor(this, prefs.getColorLetterLeft());
			Color col = diagColor.getReturnColor();
			prefs.setColorLetterLeft(col);
			letterLeftButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterRightButton.colorButton)) {
			Settings prefs = Settings.getInstance();
			DialogColor diagColor = new DialogColor(this, prefs.getColorLetterRight());
			Color col = diagColor.getReturnColor();
			prefs.setColorLetterRight(col);
			letterRightButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterMiddleButton.colorButton)) {
			Settings prefs = Settings.getInstance();
			DialogColor diagColor = new DialogColor(this, prefs.getColorLetterMiddle());
			Color col = diagColor.getReturnColor();
			prefs.setColorLetterMiddle(col);
			letterMiddleButton.colorButton.setColor(col);
			this.repaint();
			return;
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {

		if (event.getSource().equals(circleLeftButton.colorButton)) {
			circleLeftButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(circleRightButton.colorButton)) {
			circleRightButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(circleMiddleButton.colorButton)) {
			circleMiddleButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(discLeftButton.colorButton)) {
			discLeftButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(discRightButton.colorButton)) {
			discRightButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(discMiddleButton.colorButton)) {
			discMiddleButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(filledArrowWheel.colorButton)) {
			filledArrowWheel.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterLeftButton.colorButton)) {
			letterLeftButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterRightButton.colorButton)) {
			letterRightButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterMiddleButton.colorButton)) {
			letterMiddleButton.colorButton.onMouseEntered();
			this.repaint();
			return;
		}
	}

	@Override
	public void mouseExited(MouseEvent event) {

		if (event.getSource().equals(circleLeftButton.colorButton)) {
			circleLeftButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(circleRightButton.colorButton)) {
			circleRightButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(circleMiddleButton.colorButton)) {
			circleMiddleButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(discLeftButton.colorButton)) {
			discLeftButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(discRightButton.colorButton)) {
			discRightButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(discMiddleButton.colorButton)) {
			discMiddleButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(filledArrowWheel.colorButton)) {
			filledArrowWheel.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterLeftButton.colorButton)) {
			letterLeftButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterRightButton.colorButton)) {
			letterRightButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
		if (event.getSource().equals(letterMiddleButton.colorButton)) {
			letterMiddleButton.colorButton.onMouseExited();
			this.repaint();
			return;
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
