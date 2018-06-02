package com.common.utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;


public class SpellHelperUtils {
	public static Set<String> getPinyin(String src) {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = src.toCharArray();
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
			// 输出设置，大小写，音标方式等
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 无音调
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V); // '¨¹'
			// is
			// "v"
			String[][] temp = new String[src.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
				if (String.valueOf(c).matches("[//u4E00-//u9FA5]+")) { // 中文字符
					try {
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(
								srcChar[i], hanYuPinOutputFormat);
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else if (((int) c >= 65 && (int) c <= 90)
						|| ((int) c >= 97 && (int) c <= 122)) { // 英文字母
					temp[i] = new String[] { String.valueOf(srcChar[i]) };
				} else { // 其他字符
					temp[i] = new String[] { "" };
				}
			}
			String[] pinyinArray = ExChange(temp);
			Set<String> pinyinSet = new HashSet<String>();
			for (int i = 0; i < pinyinArray.length; i++) {
				pinyinSet.add(pinyinArray[i]);
			}
			return pinyinSet;
		}
		return null;
	}

	/**
	 * 字符串集合转换字符串（逗号分隔）
	 * 
	 * @param stringSet
	 * @return
	 */
	public static String makeStringByStringSet(Set<String> stringSet,
                                               String separator) {
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringSet) {
			if (i == stringSet.size() - 1) {
				str.append(s);
			} else {
				str.append(s + separator);
			}
			i++;
		}
		return str.toString().toLowerCase();
	}

	private static String[] ExChange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}

	/**
	 * 只转换汉字为拼音，其他字符不变
	 * 
	 * @param src
	 * @return
	 */
	public String getPinyinWithMark(String src) {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = src.toCharArray();
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
			// 输出设置，大小写，音标方式等
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写
			hanYuPinOutputFormat
					.setToneType(HanyuPinyinToneType.WITH_TONE_MARK); // 无音调
			hanYuPinOutputFormat
					.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE); // '¨¹'
			// is
			// "v"
			StringBuffer output = new StringBuffer();
			// String[][] temp = new String[src.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// 是中文转换拼音(我的需求，是保留中文)
				if (String.valueOf(c).matches("[//u4E00-//u9FA5]+")) { // 中文字符
					try {
						String[] temp = PinyinHelper.toHanyuPinyinStringArray(
								srcChar[i], hanYuPinOutputFormat);
						output.append(temp[0]);
						output.append(" ");
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else { // 其他字符
					output.append(String.valueOf(srcChar[i]));
				}
			}
			return output.toString();
		}
		return null;
	}

	/**
	 * 只转换汉字为拼音，其他字符不变
	 * 
	 * @param inputString
	 * @return
	 */
	public String getPinyinWithMark2(String inputString) {
		// 汉语拼音格式输出类
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式等
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK); // 有音调
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE); // '¨¹'
		// is
		// "u:"
		char[] input = inputString.trim().toCharArray();
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < input.length; i++) {
			// 是中文转换拼音(我的需求，是保留中文)
			if (Character.toString(input[i]).matches("[//u4E00-//u9FA5]+")) { // 中文字符
				try {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(
							input[i], hanYuPinOutputFormat);
					output.append(temp[0]);
					output.append(" ");
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else { // 其他字符
				output.append(Character.toString(input[i]));
			}
		}
		return output.toString();
	}

	/**
	 * 汉字转换为汉语拼音，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			int a = nameChar[i];
			if (nameChar[i] > 128) {// 中文字符
				try {
					if (PinyinHelper.toHanyuPinyinStringArray(nameChar[i],
							defaultFormat) != null) {
						pinyinName += PinyinHelper.toHanyuPinyinStringArray(
								nameChar[i], defaultFormat)[0];
					} else {
						pinyinName += nameChar[i];
					}

				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else if (nameChar[i] >= 97 && nameChar[i] <= 122) {// 小写英文字母
				pinyinName += nameChar[i];
			} else if (nameChar[i] >= 65 && nameChar[i] <= 90) {// 大写英文字母
				pinyinName += (char) (nameChar[i] + 32);
			} else {// 其他字符
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 获取第一个字的拼音的首字母
	 * @param chinese
	 * @return
	 */
	public static String getFirstSpell(String chinese){
		String firstSpell = "";
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		char firstFont = chinese.charAt(0);
		if (firstFont > 128) {// 中文字符
			try {
				if (PinyinHelper.toHanyuPinyinStringArray(firstFont,
						defaultFormat) != null) {
					firstSpell += PinyinHelper.toHanyuPinyinStringArray(
							firstFont, defaultFormat)[0];
				} else {
					firstSpell += firstFont;
				}

			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else if (firstFont>= 97 && firstFont <= 122) {// 小写英文字母
			firstSpell += firstFont;
		} else if (firstFont >= 65 && firstFont <= 90) {// 大写英文字母
			firstSpell += (char) (firstFont + 32);
		} else {// 其他字符
			firstSpell += firstFont;
		}
		return firstSpell.substring(0,1);
	}


	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,
			2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
			4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
	private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e",
			"f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "w", "x", "y", "z" };

	/** 取得给定汉字串的首字母串 */
	public static String getAllFirstLetter(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}

		StringBuilder string = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			string.append(getFirstLetter(str.substring(i, i + 1)));
		}

		return string.toString();
	}

	/** 获取汉字的首字母 */
	public  static String getFirstLetter(String chinese) {
		if (chinese == null || chinese.trim().length() == 0) {
			return "";
		}
		try {
			chinese = new String(chinese.getBytes("GB2312"), "ISO8859-1");
			// 判断是不是汉字
			if (chinese.length() > 1) {
				int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
				int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
				li_SectorCode = li_SectorCode - 160;
				li_PositionCode = li_PositionCode - 160;
				int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
				if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
					for (int i = 0; i < 23; i++) {
						if (li_SecPosCode >= li_SecPosValue[i]
								&& li_SecPosCode < li_SecPosValue[i + 1]) {
							chinese = lc_FirstLetter[i];
							break;
						}
					}
				} // 非汉字字符,如图形符号或ASCII码
				else {
					chinese = new String(chinese.getBytes("ISO8859-1"),
							"GB2312");
					chinese = chinese.substring(0, 1);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return chinese;
	}

	/**
	 * 全汉字
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpellHZ(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {// 中文字符
				pinyinName += nameChar[i];
			} else {// 其他字符
				pinyinName += "";
			}
		}
		return pinyinName;
	}

	public static boolean checkFirstCharIsLetter(String str){
		if(!TextUtils.isEmpty(str)){
			int char_asscia = str.toUpperCase().charAt(0);
			if(char_asscia>=65&&char_asscia<=90){
				return true;
			}
		}
		return false;
	}
}