package lang.detector;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class LangDetector implements NativeKeyListener {

	private JFrame frame;

	private JLabel lblLangoyo;

	private Clip clip;

	private String[] letters;

	private AudioInputStream inputStream;

	public LangDetector() {
		initialize();
	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 300, 80);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Language Detector V0.7");
		frame.setResizable(false);

		lblLangoyo = new JLabel("LangoYO!");
		lblLangoyo.setBounds(115, 18, 68, 14);
		frame.getContentPane().add(lblLangoyo);

		try {
			inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/hebrew.wav")));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		letters = new String[] { "א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט",
				"י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ", "ק", "ר", "ש", "ת" };
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		if (Arrays.asList(letters).contains(String.valueOf(e.getKeyChar()))) {
			System.out.println("its there");
			clip.setMicrosecondPosition(0);
			clip.start();
		} else {
			System.out.println("its NOT there");
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlobalScreen.registerNativeHook();
					LangDetector window = new LangDetector();
					window.frame.setVisible(true);
					GlobalScreen.getInstance().addNativeKeyListener(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
