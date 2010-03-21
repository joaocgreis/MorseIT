import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class MorseIT {

	private static final double d_speed = 0.04;

	private double speed = d_speed;

	private static final double d_frequency = 1200;

	private double frequency = d_frequency;

	private boolean visual = false;

	private void a(byte a[], List<Byte> b) {
		for (byte c : a)
			b.add(c);
	}

	private boolean isSubsetOf(String s, Set<Character> c) {
		for (char cc : s.toCharArray()) {
			if (!c.contains(cc))
				return false;
		}
		return true;
	}

	private void play_dumb(String word) {
		/*
		 * Sounds should be separated by one space Letters should be separated
		 * by three spaces Words should be separated by seven spaces Every . or -
		 * generates a space before ' ' is two spaces So, separate letters with " "
		 * (1 space char) and words with " " (3 space chars)
		 */
		int sampleRate = 48000;
		double RAD = 2.0 * Math.PI;
		try {
			AudioFormat af = new AudioFormat((float) sampleRate, 8, 1, true,
					true);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
			source.open(af);
			source.start();
			source.flush();
			source.drain();
			source.flush();

			byte[] buf = new byte[sampleRate / 4];

			byte dot[] = new byte[(int) (sampleRate * speed)];
			for (int i = 0; i < dot.length; i++) {
				dot[i] = (byte) (Math.sin(RAD * frequency / sampleRate * i) * 127.0);
			}
			byte space[] = new byte[(int) (sampleRate * speed)];

			List<Byte> sound = new ArrayList<Byte>();
			a(buf, sound);
			for (char c : word.toCharArray()) {
				switch (c) {
				case ' ':
					a(space, sound);
					a(space, sound);
					break;
				case '.':
					a(space, sound);
					a(dot, sound);
					break;
				case '-':
					a(space, sound);
					a(dot, sound);
					a(dot, sound);
					a(dot, sound);
					break;
				default:
					throw new Error();
				}
			}
			a(buf, sound);

			buf = new byte[sound.size()];
			int i = 0;
			for (byte b : sound) {
				buf[i++] = b;
			}

			source.write(buf, 0, buf.length);
			source.drain();
			source.stop();
			source.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void play(String word) {
		String a = "";
		for (char c : word.toCharArray()) {
			switch (c) {
			case 'a':
				a += ".-";
				break;
			case 'b':
				a += "-...";
				break;
			case 'c':
				a += "-.-.";
				break;
			case 'd':
				a += "-..";
				break;
			case 'e':
				a += ".";
				break;
			case 'f':
				a += "..-.";
				break;
			case 'g':
				a += "--.";
				break;
			case 'h':
				a += "....";
				break;
			case 'i':
				a += "..";
				break;
			case 'j':
				a += ".---";
				break;
			case 'k':
				a += "-.-";
				break;
			case 'l':
				a += ".-..";
				break;
			case 'm':
				a += "--";
				break;
			case 'n':
				a += "-.";
				break;
			case 'o':
				a += "---";
				break;
			case 'p':
				a += ".--.";
				break;
			case 'q':
				a += "--.-";
				break;
			case 'r':
				a += ".-.";
				break;
			case 's':
				a += "...";
				break;
			case 't':
				a += "-";
				break;
			case 'u':
				a += "..-";
				break;
			case 'v':
				a += "...-";
				break;
			case 'w':
				a += ".--";
				break;
			case 'x':
				a += "-..-";
				break;
			case 'y':
				a += "-.--";
				break;
			case 'z':
				a += "--..";
				break;
			case '0':
				a += "-----";
				break;
			case '1':
				a += ".----";
				break;
			case '2':
				a += "..---";
				break;
			case '3':
				a += "...--";
				break;
			case '4':
				a += "....-";
				break;
			case '5':
				a += ".....";
				break;
			case '6':
				a += "-....";
				break;
			case '7':
				a += "--...";
				break;
			case '8':
				a += "---..";
				break;
			case '9':
				a += "----.";
				break;
			case ' ':
				a += "   ";
				break;
			case '.':
				a += ".-.-.-";
				break;
			case ',':
				a += "--..--";
				break;
			case '?':
				a += "..--..";
				break;
			case '\'':
				a += ".----.";
				break;
			case '!':
				a += "-.-.--";
				break;
			case '/':
				a += "-..-.";
				break;
			case '(':
				a += "-.--.";
				break;
			case ')':
				a += "-.--.-";
				break;
			case '&':
				a += ".-...";
				break;
			case ':':
				a += "---...";
				break;
			case ';':
				a += "-.-.-.";
				break;
			case '=':
				a += "-...-";
				break;
			case '+':
				a += ".-.-.";
				break;
			case '-':
				a += "-....-";
				break;
			case '_':
				a += "..--.-";
				break;
			case '"':
				a += ".-..-.";
				break;
			case '$':
				a += "...-..-";
				break;
			case '@':
				a += ".--.-.";
				break;
			default:
				throw new Error("Unrecognized caracter: " + c);
			}
			a += " ";
		}

		if (visual) {
			System.out.println("Playing: " + a);
		}
		play_dumb(a);
	}

	private int numLevels;

	private Map<Integer, Character> levelCharacter;

	private Map<Integer, List<String>> newWordsPerLevel;

	private Map<Integer, List<String>> wordsPerLevel;

	private void load(String fileName) throws IOException {
		List<String> strings = new LinkedList<String>();

		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			strLine = strLine.trim().toLowerCase();
			if (!strLine.equals("")) {
				strings.add(strLine);
			}
		}
		in.close();

		List<String> better = new LinkedList<String>();
		int l = 1;
		for (String s : strings) {
			s = s.trim().toLowerCase();
			for (char c : s.toCharArray()) {
				switch (c) {
				case 'a':
				case 'b':
				case 'c':
				case 'd':
				case 'e':
				case 'f':
				case 'g':
				case 'h':
				case 'i':
				case 'j':
				case 'k':
				case 'l':
				case 'm':
				case 'n':
				case 'o':
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
				case 'u':
				case 'v':
				case 'w':
				case 'x':
				case 'y':
				case 'z':
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case ' ':
				case '.':
				case ',':
				case '?':
				case '\'':
				case '!':
				case '/':
				case '(':
				case ')':
				case '&':
				case ':':
				case ';':
				case '=':
				case '+':
				case '-':
				case '_':
				case '"':
				case '$':
				case '@':
					break;
				default:
					throw new RuntimeException(
							"Unrecognized caracter in data file (line " + l
									+ "): " + c);
				}
			}
			if (!s.equals("")) {
				better.add(s);
			}
			l++;
		}

		Map<Character, Integer> pop = new HashMap<Character, Integer>();
		for (String s : strings) {
			for (char c : s.toCharArray()) {
				if (!pop.containsKey(c)) {
					pop.put(c, 0);
				}
				pop.put(c, pop.get(c) + 1);
			}
		}

		SortedMap<Integer, Set<Character>> ordered = new TreeMap<Integer, Set<Character>>();
		for (Character c : pop.keySet()) {
			Integer i = pop.get(c);
			Set<Character> s = null;
			if (ordered.containsKey(i)) {
				s = ordered.get(i);
			} else {
				s = new HashSet<Character>();
			}
			s.add(c);
			ordered.put(i, s);
		}

		numLevels = 0;
		levelCharacter = new HashMap<Integer, Character>();
		newWordsPerLevel = new HashMap<Integer, List<String>>();
		wordsPerLevel = new HashMap<Integer, List<String>>();
		Set<Character> allowedCharactersSoFar = new HashSet<Character>();
		while (ordered.size() != 0) {
			Integer numOccur = ordered.lastKey();
			Set<Character> s = ordered.remove(numOccur);
			for (Character c : s) {
				levelCharacter.put(numLevels, c);
				newWordsPerLevel.put(numLevels, new ArrayList<String>());
				wordsPerLevel.put(numLevels, new ArrayList<String>());
				allowedCharactersSoFar.add(c);

				for (String str : better) {
					if (isSubsetOf(str, allowedCharactersSoFar)) {
						wordsPerLevel.get(numLevels).add(str);
						if ((numLevels == 0)
								|| (!wordsPerLevel.get(numLevels - 1).contains(
										str))) {
							newWordsPerLevel.get(numLevels).add(str);
						}
					}
				}

				// System.out.println(numLevels + " " + c + " "
				// + newWordsPerLevel.get(numLevels));
				numLevels++;
			}
		}

		if (numLevels == 0) {
			// q == quebas
			throw new RuntimeException("Empty file for our purposes.");
		}

		System.out.println("Loaded " + fileName + " with " + numLevels
				+ " levels.");
	}

	private int currLev;

	int gen;

	private boolean reviewStage;

	private void cli() {
		System.out.println("Hello! This is MorseIT.");
		System.out.println("Type what you hear in morse, or h for help.");

		try {
			load("words.txt");
			currLev = 0;
			gen = 0;
			reviewStage = false;
		} catch (IOException e) {
			// No exists, no opens.
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String repeatLastWord = null;
		Random r = new Random();

		while (true) {
			System.out.println();

			if (levelCharacter != null) {
				if (repeatLastWord == null) {
					if (currLev >= numLevels) {
						System.out
								.println("You have completed all avaliable levels!");
						currLev = numLevels - 1;
						reviewStage = true;
					}
					if (currLev < 0) {
						currLev = 0;
					}
					if (reviewStage) {
						if (gen > 10
								|| gen >= (wordsPerLevel.get(currLev).size())) {
							currLev++;
							gen = 0;
							reviewStage = false;
							continue;
						}
					} else {
						if (gen > 10
								|| gen > (2 * newWordsPerLevel.get(currLev)
										.size())) {
							gen = 0;
							reviewStage = true;
							continue;
						}
					}

					if (gen == 0 && reviewStage == false) {
						System.out.println("Introducing a new level: "
								+ currLev);
						repeatLastWord = levelCharacter.get(currLev).toString();
						System.out
								.println("The character for this level will be: "
										+ repeatLastWord);
					} else {
						if (gen == 0 && reviewStage == true) {
							System.out
									.println("Starting review stage for level: "
											+ currLev);
						}
						List<String> w = null;
						if (reviewStage) {
							w = wordsPerLevel.get(currLev);
						} else {
							w = newWordsPerLevel.get(currLev);
						}
						repeatLastWord = w.get(r.nextInt(w.size()));
					}
					gen++;
				}

				play(repeatLastWord);
			}

			System.out.print("> ");
			String command = null;
			try {
				command = br.readLine().trim();
			} catch (IOException e) {
				System.err.println("Error reading command from command line.");
				e.printStackTrace();
				return;
			}

			if (levelCharacter != null) {
				if (command.equals(repeatLastWord)) {
					System.out.println("Correct.");
					repeatLastWord = null;
					continue;
				}
			}

			if (command.length() == 0)
				continue;

			String argument = command.substring(1).trim();

			switch (Character.toLowerCase(command.charAt(0))) {
			case 'l':
				try {
					load(argument);
					currLev = 0;
					gen = 0;
					reviewStage = false;
				} catch (IOException e) {
					System.err.println("Error loading from file.");
					e.printStackTrace();
					levelCharacter = null;
				}
				break;
			case 's':
				if (argument.length() == 0) {
					System.out.println("Current speed is " + speed
							+ " and the default is " + d_speed);
				} else {
					try {
						speed = Double.parseDouble(argument);
					} catch (NumberFormatException e) {
						System.out.println("Could not understand number.");
					}
					System.out.println("Speed changed to " + speed);
				}
				break;
			case 'f':
				if (argument.length() == 0) {
					System.out.println("Current frequency is " + frequency
							+ " and the default is " + d_frequency);
				} else {
					try {
						frequency = Double.parseDouble(argument);
					} catch (NumberFormatException e) {
						System.out.println("Could not understand number.");
					}
					System.out.println("Frequency changed to " + frequency);
				}
				break;
			case 'q':
				System.out.println("Bye bye!");
				return;
			case 'g':
				if (argument.length() == 0) {
					System.out.println("Skiping to next level.");
					currLev++;
					reviewStage = false;
					gen = 0;
					repeatLastWord = null;
				} else {
					try {
						currLev = Integer.parseInt(argument);
						reviewStage = false;
						gen = 0;
						repeatLastWord = null;
						System.out.println("Skiping to level " + currLev);
					} catch (NumberFormatException e) {
						System.out.println("Could not understand number.");
					}
				}
				break;
			case 'u':
				if (argument.length() == 0) {
					System.out.println("Skiping to review stage.");
					reviewStage = true;
					gen = 0;
					repeatLastWord = null;
				} else {
					try {
						currLev = Integer.parseInt(argument);
						reviewStage = true;
						gen = 0;
						repeatLastWord = null;
						System.out.println("Skiping to review stage of level "
								+ currLev);
					} catch (NumberFormatException e) {
						System.out.println("Could not understand number.");
					}
				}
				break;
			case 'v':
				visual = !visual;
				System.out.println("Visual help "
						+ (visual ? "enabled" : "disabled") + ".");
				break;
			case 'd':
				System.out.println("Gave up the word.");
				repeatLastWord = null;
				break;
			case 'p':
				play(argument.toLowerCase());
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
				}
				break;
			case 'h':
				System.out.println("Help:");
				System.out.println("l [FILE]    "
						+ "loads a file (default: words.txt)");
				System.out.println("s [NUMBER]  "
						+ "changes the speed (default: " + d_speed
						+ ", current: " + speed + ")");
				System.out.println("f [NUMBER]  "
						+ "changes the frequency (default: " + d_frequency
						+ ", current: " + frequency + ")");
				System.out.println("q           " + "quit");
				System.out.println("g [NUMBER]  "
						+ "go to level (default: next one)");
				System.out.println("u [NUMBER]  "
						+ "review up to level (default: this one)");
				System.out.println("v           "
						+ "toggle visual help (default: off, current:"
						+ (visual ? "on" : "off") + ")");
				System.out.println("d           " + "give up this word");
				System.out.println("p [WORDS]   " + "play words");
				System.out.println("h           " + "show this help");
				break;
			}
		}
	}

	public static void main(String[] args) {
		MorseIT a = new MorseIT();
		// a.play("joao reis");

		a.cli();
	}
}
