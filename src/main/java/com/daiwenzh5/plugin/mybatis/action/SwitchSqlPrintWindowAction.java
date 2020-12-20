package com.daiwenzh5.plugin.mybatis.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.daiwenzh5.plugin.mybatis.ui.SqlPrintWindow.SQL_PRINT_WINDOW_ID;

/**
 * @author daiwenzh5
 * @description 打开 SQL 监听面板的动作
 * @date 2020/12/8
 */
public class SwitchSqlPrintWindowAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 显示控制台
        Objects.requireNonNull(ToolWindowManager.getInstance(Objects.requireNonNull(event.getProject()))
                .getToolWindow(SQL_PRINT_WINDOW_ID))
                .show();
    }
}
