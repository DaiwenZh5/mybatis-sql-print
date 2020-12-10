package com.daiwenzh5.plugin.mybatis;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;

import static com.intellij.execution.ui.ConsoleViewContentType.*;

/**
 * @author daiwenzh5
 * @date 2020年12月10日
 */
public class ToolWindowConsole {

    private static Project project;
    private static ConsoleView console;

    public ToolWindowConsole(ConsoleView console, Project project) {
        ToolWindowConsole.console = console;
        ToolWindowConsole.project = project;
    }

    public static void log(String s) {
        log(s, NORMAL_OUTPUT);
    }

    public static void log(String s, String type) {
        log(s, SqlToolWindow.TYPE_MAP.getOrDefault(type, NORMAL_OUTPUT));
    }

    public static void log(String s, ConsoleViewContentType consoleViewContentType) {
        if (console == null) {
            System.out.println("console为空，重新获取console");
            console = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        }
        if (console.isOutputPaused()) {
            System.out.println("console已暂停,设置启动");
            console.setOutputPaused(false);
        }
        console.print(s + "\n", consoleViewContentType);
        System.out.println("【SQL 格式化】重建查询日志结束");
    }
}
