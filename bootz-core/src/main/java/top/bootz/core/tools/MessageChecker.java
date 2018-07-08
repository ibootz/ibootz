package top.bootz.core.tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

import top.bootz.commons.helper.JsonHelper;
import top.bootz.core.base.dto.RestMessage;

/**
 * 检查message资源文件中的json格式符不符合规范
 * 
 * @author John
 *
 */
public class MessageChecker {

    private MessageChecker() {

    }

    // 国际化资源文件路径，需根据实际情况自己配置。
    private static final String MESSAGE_FILE_PATH = "I:/git_repository/orion/orion-manage/orion-user/src/main/resources/i18n/message.properties";

    public static void main(String[] args) {
        Properties prop = new Properties();
        boolean hasError = false;
        try (InputStream inStream = new BufferedInputStream(new FileInputStream(MESSAGE_FILE_PATH))) {
            prop.load(inStream);
            for (Map.Entry<Object, Object> e : prop.entrySet()) {
                String source = (String) e.getValue();
                if (!source.matches(".*\\{\\d\\}.*")) {
                    hasError = checkJson(hasError, e, source);
                } else {
                    System.out.println(source);
                    MessageFormat.format(source, new Object[] { 401, "未认证错误" });
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!hasError) {
            System.out.println("文件没有检查到语法错误，请继续保持！");
        }
    }

    private static boolean checkJson(boolean hasError, Map.Entry<Object, Object> e, String source) {
        try {
            JsonHelper.fromJSON(source, RestMessage.class);
        } catch (Exception e2) {
            if (!hasError) {
                hasError = true;
            }
            System.err.println("[" + e.getKey() + "] has a json syntax error");
        }
        return hasError;
    }

}
