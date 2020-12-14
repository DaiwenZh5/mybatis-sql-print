package com.daiwenzh5.plugin.mybatis.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

/**
 * @author daiwenzh5
 * @description 格式化工具
 * @date 2020/12/14
 */
@UtilityClass
public class FormatUtils {

    private static final String QUESTION_MARK = "?";

    public String restore(final ConsoleViewUtils.OriginSqlLog log) {
        String[] fragments  = StringUtils.split(log.getPreparing(), QUESTION_MARK);
        String[] args = log.getParameters().split(",");
        StringBuilder builder = new StringBuilder(fragments[0]);
        for (int i = 1; i < fragments.length; i++) {
            String arg = args[i-1];
            // 找到最后一个 （）的位置，提取参数类型，如参数： 1(Long)
            int typeIndex = arg.lastIndexOf("(");
            String type = arg.substring(typeIndex + 1, arg.length() - 1);
            arg = arg.substring(0, typeIndex);
            if (isNumber(type)) {
                builder.append(arg).append(fragments[i]);
            } else {
                builder.append("'").append(arg).append("'").append(fragments[i]);
            }
        }
        log.setEndFlag(false);
        return simpleFormat(builder.toString());
    }

    private boolean isNumber(String arg) {
        return arg.contains("Integer")
                || arg.contains("Long")
                || arg.contains("Double")
                || arg.contains("Float")
                || arg.contains("Short");
    }

    public static String simpleFormat(@NotNull String sql) {
        if(!StringUtils.isEmpty(sql)) {
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