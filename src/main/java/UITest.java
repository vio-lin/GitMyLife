
import com.violin.testTool.annotation.ScannerPath;
import com.violin.testTool.ui.TestUI;
import com.violin.testTool.utils.TestConstance;

import javax.swing.*;
import java.awt.*;
import java.lang.annotation.Annotation;

/**
 * @author guo.lin  2018/12/28
 */
@ScannerPath("com.violin.testTool.testCase")
public class UITest {
  public static void main(String[] args) {
    Annotation[] annotations = UITest.class.getAnnotations();
    for(Annotation annotation : annotations){
      if(annotation instanceof ScannerPath){
        System.setProperty(TestConstance.CLASS_PATH_PROPERTY,((ScannerPath) annotation).value());
      }
    }
    JFrame frame = new JFrame("Frame With Panel");
    Container contentPane = frame.getContentPane();
    contentPane.setBackground(Color.CYAN); // 将JFrame实例背景设置为蓝绿色
    JPanel panel = new TestUI(); // 创建一个JPanel的实例
    panel.setBackground(Color.yellow); // 将JPanel的实例背景设置为黄色
    contentPane.add(panel, BorderLayout.SOUTH); // 将JPanel实例添加到JFrame的南侧
//    JOptionPane.showMessageDialog(panel, "返回结果" + "asj\nhda\nksd", "INFO", JOptionPane.PLAIN_MESSAGE);
    frame.setSize(400, 300);
    frame.setVisible(true);
  }

}
