package com.violin.testTool.ui;

import com.violin.testTool.annotation.UnitTest;
import com.violin.testTool.annotation.Test;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.violin.testTool.utils.ClassFinder;
import com.violin.testTool.utils.TestConstance;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * lin
 * 2019/1/12
 * 846271633@qq.com
 */
public class TestUI extends JPanel {
    public static Map<Class<?>, Object> instanceCache = Maps.newHashMap();
    public static Map<Class<?>, List<Method>> testMethodCache = Maps.newHashMap();

    JPanel jpanel;
    JProgressBar jProgressBar;

    public TestUI() {
        super();
        init();
    }

    /**
     * Initialize the interface layout and elements
     */
    public void init() {
        prepareTestCase();
        int progressMax = 0;
        for (Map.Entry<Class<?>, List<Method>> testMethodEntry : testMethodCache.entrySet()) {
            progressMax += testMethodEntry.getValue().size();
        }

        jpanel = new JPanel();
        TextField textField = new TextField();
        textField.setText("找到"+progressMax+"个Test");
        jpanel.add(textField);
        JButton button = new JButton("开启测试");
        jpanel.add(button);
        jProgressBar = new JProgressBar(SwingConstants.HORIZONTAL,0,progressMax);;
        jpanel.add(jProgressBar);

        button.addActionListener(actionEvent -> {

            new Thread( ()->{
                int count = 0;
                for(Map.Entry<Class<?>, List<Method>> testMethodEntry : testMethodCache.entrySet()){
                    for(Method method : testMethodEntry.getValue()){
                        try {
                            // 这边可以使用 返回值 或者异常判断test是否成功
                            method.invoke(instanceCache.get(testMethodEntry.getKey()),null);
                            count++;
                            jProgressBar.setValue(count);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        });
        add(jpanel);
    }

    private void prepareTestCase() {
        Set<Class<?>> set = scanTestClass();
        for (Class<?> testClass : set) {
            if (!instanceCache.containsKey(testClass)) {
                try {
                    instanceCache.put(testClass, testClass.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            List<Method> methodList = Lists.newArrayList();
            // class 和method 作为一个简单的测试参数
            Method[] methods = testClass.getMethods();
            for (Method method : methods) {
                if (method.getAnnotation(Test.class) != null) {
                    methodList.add(method);
                }
            }
            testMethodCache.put(testClass, methodList);
        }
    }

    private Set<Class<?>> scanTestClass() {
        String packagePath = System.getProperty(TestConstance.CLASS_PATH_PROPERTY);
        Set<Class<?>> classSet = ClassFinder.getClzFromPkg(packagePath);
        // 使用lambda 表达式优化
        Set<Class<?>> classSet2 = new HashSet<>();
        for (Class<?> classType : classSet) {
            if (classType.getAnnotation(UnitTest.class) != null) {
                classSet2.add(classType);
            }
        }
        return classSet2;
    }
}
