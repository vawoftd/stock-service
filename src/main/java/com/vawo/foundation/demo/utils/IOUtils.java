package com.vawo.foundation.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO 工具类
 * 
 * @author beenkevin
 * @date 2016-12-07
 * @since 0.0.1
 */
public class IOUtils {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	public static final int EOF = -1;

	public static int copy(final InputStream input, final OutputStream output) throws IOException {
		final long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(final InputStream input, final OutputStream output) throws IOException {
		return copy(input, output, DEFAULT_BUFFER_SIZE);
	}

	public static long copy(final InputStream input, final OutputStream output, final int bufferSize)
			throws IOException {
		return copyLarge(input, output, new byte[bufferSize]);
	}

	public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
			throws IOException {
		long count = 0;
		int n;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
