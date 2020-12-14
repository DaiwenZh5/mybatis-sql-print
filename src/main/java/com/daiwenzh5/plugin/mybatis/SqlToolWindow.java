package com.daiwenzh5.plugin.mybatis;

import com.daiwenzh5.plugin.mybatis.type.LogType;
import com.daiwenzh5.plugin.mybatis.util.ConsoleViewUtils;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import static com.daiwenzh5.plugin.mybatis.OpenToolWindowAction.TOOL_WINDOW_ID;

/**
 * @author daiwenzh5
 * @description SQL 打印控制台
 * @date 2020/12/8
 */
public class SqlToolWindow implements ToolWindowFactory {


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
        JComponent consolePanel = createConsolePanel(console);
        Content content = toolWindow.getContentManager().getFactory().createContent(consolePanel, TOOL_WINDOW_ID, false);
        toolWindow.getContentManager().addContent(content);
        ConsoleViewUtils.put(project, console);
        ConsoleViewUtils.logN(project, "正在监听日志输出以重组 SQL...", LogType.TITLE);
        ConsoleViewUtils.logN(project, ConsoleViewUtils.SPLIT_LINE, LogType.SPLIT_LINE);
        PropertiesCenter.init(project);
    }

}
