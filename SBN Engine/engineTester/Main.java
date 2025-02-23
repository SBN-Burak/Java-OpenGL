package engineTester;

import java.io.File;

import launcher.EngineLauncher;

public class Main {
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("lib/NATIVE").getAbsolutePath());
		EngineLauncher.runLauncher();
	}
}
