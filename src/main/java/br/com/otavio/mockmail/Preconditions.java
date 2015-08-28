package br.com.otavio.mockmail;

import static java.nio.file.Files.isWritable;

import java.nio.file.Path;

/**
 * Helper class to check some preconditions.
 * 
 * @author Otávio S Garcia
 */
final class Preconditions {

	/**
	 * Check if the String is not null or empty throwing an exception otherwise.
	 */
	static void checkNotEmptyString(String str, String message, Object... parameters) {
		if (str == null || str.isEmpty()) {
			throw new IllegalArgumentException(String.format(message, parameters));
		}
	}

	/**
	 * Check if path is writable throwing an exception otherwise.
	 */
	static void checkWritable(Path path, String message, Object... parameters) {
		if (!isWritable(path)) {
			throw new IllegalArgumentException(String.format(message, parameters));
		}
	}
}