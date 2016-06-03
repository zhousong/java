package charset;

import java.io.UnsupportedEncodingException;

public class CharsetTest {
	private static String hexStr = "0123456789ABCDEF";

	public static void main(String[] args) {
		String s = "中国";
		System.out.println(s.getBytes().length);

		BinaryToHexString(s.getBytes());

		try {
			String ss = new String("中国".getBytes("ASCII"));
			System.out.println(ss.getBytes().length);
			BinaryToHexString(ss.getBytes());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String BinaryToHexString(byte[] bytes) {
		String result = "";
		String hex = "";
		for (int i = 0; i < bytes.length; i++) {
			// 字节高4位
			hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
			// 字节低4位
			hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
			result += hex + " ";
		}
		System.out.println(result);
		return result;
	}

}
