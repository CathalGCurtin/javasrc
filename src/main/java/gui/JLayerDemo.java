package swingui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;

/**
 * A JLayer is a wrapper on a component that allows for decorating it in various
 * ways
 * 
 * @author Ian Darwin
 */
public class JLayerDemo {
	final static Color COLOR = new Color(0, 64, 0, 64);

	/**
	 * A simple example of a LayerUI, the helper for the JLayer.
	 * See the Oracle JavaDoc for JLayer for more details.
	 */
	private static LayerUI<JComponent> layerUI = new LayerUI<JComponent>() {

		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g, JComponent c) {
			super.paint(g, c);
			g.setColor(COLOR);
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
		}

		@Override
		public void installUI(JComponent c) {
			super.installUI(c);
			if (!(c instanceof JLayer)) {
				return;
			}
			// enable mouse motion events for the layer's subcomponents
			((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_MOTION_EVENT_MASK);
		}

		@Override
		public void uninstallUI(JComponent c) {
			super.uninstallUI(c);
			// reset the layer event mask
			if (!(c instanceof JLayer)) {
				return;
			}
			((JLayer) c).setLayerEventMask(0);
		}

		// Catch and gab about MouseMotion events
		@Override
		public void eventDispatched(AWTEvent e, JLayer<? extends JComponent> l) {
			System.out.println("Event: " + e);
		}
	};

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				final JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel p = new JPanel();
				JButton jb = new JButton("A Button");
				jb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("You pressed my button!");
					}
				});
				p.add(jb);

				// Wrap the JPanel in the JLayer, and add to a JFrame
				frame.add(new JLayer<JComponent>(p, layerUI));

				frame.setSize(200, 150);
				frame.setVisible(true);
			}
		});
	}
}