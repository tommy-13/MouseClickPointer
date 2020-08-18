package view.general;

import java.util.Map.Entry;

import lc.kra.system.mouse.GlobalMouseHook;
import lc.kra.system.mouse.event.GlobalMouseAdapter;
import lc.kra.system.mouse.event.GlobalMouseEvent;



public class GlobalMouseListener {



	private GlobalMouseHook mouseHook;
	
	
	public GlobalMouseListener(MouseActionWindow window) {

		// Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
		mouseHook = new GlobalMouseHook(); // Add true to the constructor, to switch to raw input mode

		System.out.println("Global mouse hook successfully started. Connected Mice:");

		for (Entry<Long,String> mouse:GlobalMouseHook.listMice().entrySet()) {
			System.out.format("%d: %s\n", mouse.getKey(), mouse.getValue());
		}

		mouseHook.addMouseListener(new GlobalMouseAdapter() {

			@Override 
			public void mousePressed(GlobalMouseEvent event)  {
				//System.out.println(event);

				/*
				if ((event.getButtons() & GlobalMouseEvent.BUTTON_LEFT) != GlobalMouseEvent.BUTTON_NO
						&& (event.getButtons() & GlobalMouseEvent.BUTTON_RIGHT) != GlobalMouseEvent.BUTTON_NO) {
					// Both mouse buttons are pressed;

				}
				*/

				if (event.getButton()==GlobalMouseEvent.BUTTON_LEFT) {
					window.onLeftMouseButtonPressed();
				}
				if (event.getButton()==GlobalMouseEvent.BUTTON_RIGHT) {
					window.onRightMouseButtonPressed();
				}
				if (event.getButton()==GlobalMouseEvent.BUTTON_MIDDLE) {
					window.onMiddleMouseButtonPressed();
				}
				//window.repaint(10);
			}


			@Override 
			public void mouseReleased(GlobalMouseEvent event)  {

				if (event.getButton()==GlobalMouseEvent.BUTTON_LEFT) {
					window.onLeftMouseButtonReleased();
				}
				if (event.getButton()==GlobalMouseEvent.BUTTON_RIGHT) {
					window.onRightMouseButtonReleased();
				}
				if (event.getButton()==GlobalMouseEvent.BUTTON_MIDDLE) {
					window.onMiddleMouseButtonReleased();
				}
				//window.repaint(10);
				//System.out.println(event); 
			}

			@Override 
			public void mouseMoved(GlobalMouseEvent event) {
				//System.out.print("moved: ");
				//System.out.println(event);
				window.onMouseMoved(event.getX(), event.getY());
				//window.repaint(10);
			}

			@Override 
			public void mouseWheel(GlobalMouseEvent event) {
				if (event.getDelta() < 0) {
					window.onMouseWheelDown();
				}
				else {
					window.onMouseWheelUp();
				}
				
				//System.out.println(event); 
			}
		});

		/*
		try {
			while(run) { 
				Thread.sleep(10); 
			}
		} catch(InterruptedException e) { 
			//Do nothing
		} finally {
			mouseHook.shutdownHook(); 
			System.exit(0);
		}*/
	}


	public void shutdownHook() {
		mouseHook.shutdownHook();
	}


	/*	public static void showWindow() {
		window.setVisible(true);
	}

	public static void showWindow() {
		window = new Window(null)
		{
		  @Override
		  public void paint(Graphics g)
		  {
		    final Font font = getFont().deriveFont(48f);
		    g.setFont(font);
		    g.setColor(Color.RED);
		    final String message = "Hello";
		    FontMetrics metrics = g.getFontMetrics();
		    g.drawString(message,
		      (getWidth()-metrics.stringWidth(message))/2,
		      (getHeight()-metrics.getHeight())/2);
		  }
		  @Override
		  public void update(Graphics g)
		  {
		    paint(g);
		  }
		};
		window.setAlwaysOnTop(true);
		window.setBounds(window.getGraphicsConfiguration().getBounds());
		window.setBackground(new Color(0, true));
		window.setVisible(true);
	}


	public static void hideWindow() {
		window.setVisible(false);
	}
	 */
}
