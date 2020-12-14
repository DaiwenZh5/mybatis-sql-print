package com.daiwenzh5.plugin.mybatis.type;

import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;

import java.awt.*;

/**
 * @author daiwenzh5
 * @description 日志风格
 * @date 2020/12/14
 */
public enum LogType {

    /**
     * 标题样式
     */
    TITLE( new ConsoleViewContentType("TITLE_TYPE",
            new TextAttributes(new JBColor(new Color(255, 198, 109), new Color(255, 198, 109)), null,
                    null, null, Font.PLAIN))),

    /**
     * SQL 样式
     */
    SQL(new ConsoleViewContentType("SQL_TYPE",
                                        new TextAttributes(new JBColor(new Color(188, 63, 60), new Color(188, 63, 60)), null,
            null, null, Font.PLAIN))),

    /**
     * 注释样式
     */
    ANNOTATION(new ConsoleViewContentType("ANNOTATION_TYPE",
                                        new TextAttributes(new JBColor(new Color(113, 114, 107), new Color(113, 114, 107)), null,
            null, null, Font.PLAIN))),

    /**
     * 分割符样式
     */
    SPLIT_LINE(new ConsoleViewContentType("SPLIT_TYPE",
                                       new TextAttributes(new JBColor(new Color(119, 183, 103), new Color(119, 183, 103)), null,
            null, null, Font.PLAIN)))
    ;

    public final ConsoleViewContentType style;

    LogType(ConsoleViewContentType style) {
        this.style = style;
    }
}
