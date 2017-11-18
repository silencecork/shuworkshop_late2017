package com.shuworkshop.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Utils {
	public static String streamToString(InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		for (;;) {
		    int nread = in.read(buf, 0, buf.length);
		    if (nread <= 0) {
		        break;
		    }
		    baos.write(buf, 0, nread);
		}
		in.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		return new String(bytes, StandardCharsets.UTF_8);
	}
}
