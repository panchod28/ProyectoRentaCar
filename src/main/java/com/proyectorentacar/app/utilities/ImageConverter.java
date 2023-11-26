package com.proyectorentacar.app.utilities;

import java.io.IOException;

import org.springframework.stereotype.Component;

import net.iharder.Base64;


@Component
public class ImageConverter {
	
	public String encode(byte[] imageBytes) {
		byte[] encodedBytes = Base64.encodeBytesToBytes(imageBytes);
        return new String(encodedBytes);
	}
	
	public  static byte[] decode(String base64String) throws IOException {
		byte[] decodedBytes = Base64.decode(base64String);
        return decodedBytes;
	}
}
