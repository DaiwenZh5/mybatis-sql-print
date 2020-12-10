package com.daiwenzh5.plugin.mybatis;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author daiwenzh5
 * @description 打开 SQL 监听面板的动作
 * @date 2020/12/8
 */
public class OpenToolWindowAction extends AnAction {

    public static final String TOOL_WINDOW_ID = "Mybatis-SQL-Print";

    @Override
    public void update(AnActionEvent e) {
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 显示控制台
        Objects.requireNonNull(ToolWindowManager.getInstance(Objects.requireNonNull(event.getProject()))
                .getToolWindow(TOOL_WINDOW_ID))
                .show();
    }
}
