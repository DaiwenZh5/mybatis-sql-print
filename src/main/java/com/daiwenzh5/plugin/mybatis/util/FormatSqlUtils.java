package com.daiwenzh5.plugin.mybatis.util;

/**
 * @author daiwenzh5
 * @description SQL 格式化工具
 * @date 2020/12/9
 */
public class FormatSqlUtils {
    private static final String QUESTION_MARK = "?";

    public static String restore(final String sql, final String param) {
        System.out.println(sql);
        System.out.println(param);
        String[] fragments  = StringUtils.split(sql.trim(), QUESTION_MARK);
        String[] args = param.trim().split(",");
        StringBuilder builder = new StringBuilder(fragments[0]);
        for (int i = 1; i < fragments.length; i++) {
            String arg = args[i-1];
            System.out.println(arg);
            // 找到最后一个 （）的位置，提取参数类型，如参数： Mr.Z(String)
            int typeIndex = arg.lastIndexOf("(");
            arg = arg.substring(0, typeIndex);
            System.out.println(arg);
            if (isNumber(arg)) {
                builder.append(arg).append(fragments[i]);
            } else {
                builder.append("'").append(arg).append("'").append(fragments[i]);
            }
        }
        System.out.println(builder.toString());
        return simpleFormat(builder.toString());
    }

    private static boolean isNumber(String arg) {
        return arg.contains("Integer")
                || arg.contains("Long")
                || arg.contains("Double")
                || arg.contains("Float");
    }

    public static String simpleFormat(String sql) {
        if(org.apache.commons.lang3.StringUtils.isNotBlank(sql)) {
            return sql.replaceAll("(?i)\\s+from\\s+", "\nFROM ")
                    .replaceAll("(?i)\\s+where\\s+", "\nWHERE ")
                    .replaceAll("(?i)\\s+left join\\s+", "\nLEFT JOIN ")
                    .replaceAll("(?i)\\s+right join\\s+", "\nRIGHT JOIN ")
                    .replaceAll("(?i)\\s+inner join\\s+", "\nINNER JOIN ")
                    .replaceAll("(?i)\\s+limit\\s+", "\nLIMIT ")
                    .replaceAll("(?i)\\s+on\\s+", "\nON ")
                    .replaceAll("(?i)\\s+union\\s+", "\nUNION ");
        }
        return "";
    }
}
