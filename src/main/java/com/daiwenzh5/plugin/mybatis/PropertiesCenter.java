package com.daiwenzh5.plugin.mybatis;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

/**
 * @author daiwenzh5
 * @date 2020年12月10日
 */
public class PropertiesCenter {

    final static String LOG_INDEX_KEY = "MybatisSqlPrint_Index";

    public static void init(Project project) {
        if (project != null) {
            PropertiesComponent.getInstance(project).setValue(LOG_INDEX_KEY, 1, 1);
        }
    }

    static void setIndex(Project project, int index) {
        PropertiesComponent.getInstance(project).setValue(LOG_INDEX_KEY, index, 1);
    }

    static int getIndex(Project project) {
        return PropertiesComponent.getInstance(project).getInt(LOG_INDEX_KEY, 1);
    }

}
