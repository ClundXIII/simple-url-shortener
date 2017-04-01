package co.clund;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * class for with main function
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("starting program ...");

		File f = new File(args[0]);

		try (FileReader in = new FileReader(f); BufferedReader r = new BufferedReader(in)) {

			MainHttpListener l = new MainHttpListener(r);
			l.run();

			System.in.read();

			// wait for listener to stop
			l.stop_join();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println("stopped!");
	}
}
