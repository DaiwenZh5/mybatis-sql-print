package com.daiwenzh5.plugin.mybatis.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import static com.daiwenzh5.plugin.mybatis.LogFilter.PREPARING;

/**
 * @author daiwenzh5
 * @description 字符串工具类
 * @date 2020/12/9
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>Splits the provided text into an array, separators specified.
     * This is an alternative to using StringTokenizer.</p>
     *
     * <p>The separator is not included in the returned String array.
     * Adjacent separators are treated as one separator.
     * For more control over the split use the StrTokenizer class.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * A {@code null} separatorChars splits on whitespace.</p>
     *
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("abc def", null) = ["abc", "def"]
     * StringUtils.split("abc def", " ")  = ["abc", "def"]
     * StringUtils.split("abc  def", " ") = ["abc", "def"]
     * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str  the String to parse, may be null
     * @param separatorChars  the characters used as the delimiters,
     *  {@code null} splits on whitespace
     * @return an array of parsed Strings, {@code null} if null String input
     */
    public static String[] split(String str, final String separatorChars) {
        List<String> list = new ArrayList<>();
        int index = str.indexOf(separatorChars);
        while (index > -1) {
            list.add(str.substring(0, index));
            str = str.substring(index + separatorChars.length());
            index = str.indexOf(separatorChars);
        }
        list.add(str);
        return list.toArray(list.toArray(new String[0]));
    }



    public static void main(String[] args) {
        System.out.println(System.getenv("intellijPublishToken"));
    }
}
