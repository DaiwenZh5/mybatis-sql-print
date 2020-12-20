package com.daiwenzh5.plugin.mybatis.ui;

import com.daiwenzh5.plugin.mybatis.type.LogType;
import com.daiwenzh5.plugin.mybatis.util.ConsoleViewUtils;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author daiwenzh5
 * @description SQL 打印控制台
 * @date 2020/12/8
 */
public class SqlPrintWindow implements ToolWindowFactory {

    public static final String SQL_PRINT_WINDOW_ID = "输出面板";

    /**
     * 容器
     */
    private JPanel container;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ConsoleView consoleView = ConsoleViewUtils.get(project);
        this.createUi(consoleView);
        Content content = toolWindow.getContentManager().getFactory()
                .createContent(container, SQL_PRINT_WINDOW_ID, false);
        toolWindow.getContentManager().addContent(content);
        ConsoleViewUtils.logN(project, "正在监听日志输出以重组 SQL...", LogType.TITLE);
        ConsoleViewUtils.logN(project, ConsoleViewUtils.SPLIT_LINE, LogType.SPLIT_LINE);
//        PropertiesCenter.init(project);
    }

    /**
     * 创建 UI 组件
     *
     * @param consoleView 控制台视图组件
     */
    private void createUi(ConsoleView consoleView) {
        // 创建容器
        this.container = new JPanel();
        this.container.setLayout(new BorderLayout());
        // 将控制台加入容器
        this.container.add(consoleView.getComponent(), BorderLayout.CENTER, 0);
        // 创建工具栏
        final DefaultActionGroup actions = new DefaultActionGroup();
        actions.addAll(consoleView.createConsoleActions());
        ActionToolbar actionToolbar = ActionManager.getInstance()
                .createActionToolbar(ActionPlaces.TOOLBAR, actions, false);
        // 指定组件插入位置，防止控制台被覆盖
        this.container.add(actionToolbar.getComponent(), BorderLayout.WEST, 1);
    }

}
