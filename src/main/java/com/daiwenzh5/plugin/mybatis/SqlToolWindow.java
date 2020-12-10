package com.daiwenzh5.plugin.mybatis;

import com.google.common.collect.ImmutableMap;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static com.daiwenzh5.plugin.mybatis.LogFilter.SPLIT_LINE;
import static com.daiwenzh5.plugin.mybatis.OpenToolWindowAction.TOOL_WINDOW_ID;

/**
 * @author daiwenzh5
 * @description SQL 打印控制台
 * @date 2020/12/8
 */
public class SqlToolWindow implements ToolWindowFactory {

    /**
     * 标题文本风格
     */
    private static final ConsoleViewContentType TITLE_TYPE = new ConsoleViewContentType("TITLE_TYPE",
            new TextAttributes(new JBColor(new Color(255, 198, 109), new Color(255, 198, 109)), null,
                    null, null, Font.PLAIN));
    /**
     * SQL 文本风格
     */
    private static final ConsoleViewContentType SQL_TYPE = new ConsoleViewContentType("SQL_TYPE",
            new TextAttributes(new JBColor(new Color(188, 63, 60), new Color(188, 63, 60)), null,
                    null, null, Font.PLAIN));

    /**
     * 注释文本风格
     */
    private static final ConsoleViewContentType ANNOTATION_TYPE = new ConsoleViewContentType("ANNOTATION_TYPE",
            new TextAttributes(new JBColor(new Color(113, 114, 107), new Color(113, 114, 107)), null,
                    null, null, Font.PLAIN));

    /**
     * 分隔线文本风格
     */
    private static final ConsoleViewContentType SPLIT_TYPE = new ConsoleViewContentType("SPLIT_TYPE",
            new TextAttributes(new JBColor(new Color(119, 183, 103), new Color(119, 183, 103)), null,
                    null, null, Font.PLAIN));

    public final static Map<String, ConsoleViewContentType> TYPE_MAP = ImmutableMap.<String, ConsoleViewContentType>builder()
            .put("title", TITLE_TYPE)
            .put("sql", SQL_TYPE)
            .put("annotation", ANNOTATION_TYPE)
            .put("split", SPLIT_TYPE)
            .build();
    public static JComponent createConsolePanel(ConsoleView view) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(view.getComponent(), BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        TextConsoleBuilder consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        ConsoleView console = consoleBuilder.getConsole();
        console.print("正在监听日志输出以重组 SQL...\n", TITLE_TYPE);
        console.print(SPLIT_LINE+"\n", SPLIT_TYPE);
        JComponent consolePanel = createConsolePanel(console);
        Content content = toolWindow.getContentManager().getFactory().createContent(consolePanel, TOOL_WINDOW_ID, false);
        toolWindow.getContentManager().addContent(content);
        new ToolWindowConsole(console, project);
        PropertiesCenter.init(project);
    }

}
