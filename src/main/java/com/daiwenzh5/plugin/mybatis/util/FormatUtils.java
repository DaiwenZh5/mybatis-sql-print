package com.daiwenzh5.plugin.mybatis.util;

import lombok.experimental.UtilityClass;

/**
 * @author daiwenzh5
 * @description 格式化工具
 * @date 2020/12/14
 */
@UtilityClass
public class FormatUtils {

    private static final String QUESTION_MARK = "?";

    public String restore(final ConsoleViewUtils.OriginSqlLog log) {
        return restore(log, true);
    }

    public String restore(final ConsoleViewUtils.OriginSqlLog log, boolean isFormat) {
        String[] fragments = StringUtils.split(log.getPreparing(), QUESTION_MARK);
        String[] args = log.getParameters().split(",");
        StringBuilder builder = new StringBuilder(fragments[0]);
        for (int i = 1; i < fragments.length; i++) {
            String arg = args[i - 1];
            // 找到最后一个 （）的位置，提取参数类型，如参数： 1(Long)
            int typeIndex = arg.lastIndexOf("(");
            String type = arg.substring(typeIndex + 1, arg.length() - 1);
            arg = arg.substring(0, typeIndex).trim();
            if (isNumber(type)) {
                builder.append(arg).append(fragments[i]);
            } else {
                builder.append("'").append(arg).append("'").append(fragments[i]);
            }
        }
        log.setEndFlag(false);
        if (isFormat) {
            return SqlFormatter.format(builder.toString());
        }
        return builder.toString();
    }

        private boolean isNumber(String arg) {
        return arg.contains("Integer")
                || arg.contains("Long")
                || arg.contains("BigDecimal")
                || arg.contains("Double")
                || arg.contains("Float")
                || arg.contains("Short");
    }

}
