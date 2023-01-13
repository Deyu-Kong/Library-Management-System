import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author C.W
 * @date 2022/5/9 17:17
 * @desc bash工具
 */
@Slf4j
public class BashUtils {

    public static final Integer SUCCESS_CODE = 0;

    /**
     * 执行命令
     *
     * @param command
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static int execCommand(String command, String dir) throws IOException, InterruptedException {
        String[] commands = command.split(" ");
        List<String> commandList = new ArrayList<>(commands.length);
        for (String s : commands) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            commandList.add(s);
        }
        commands = new String[commandList.size()];
        commands = commandList.toArray(commands);
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        if (StringUtils.isNotBlank(dir)) {
            processBuilder.directory(new File(dir));
        }
        log.info("开始执行命令: {} ", command);
        Process exec = processBuilder.start();
        // 获取外部程序标准输出流
        new Thread(new OutputHandlerRunnable(exec.getInputStream(), false)).start();
        // 获取外部程序标准错误流
        new Thread(new OutputHandlerRunnable(exec.getErrorStream(), true)).start();
        int code = exec.waitFor();
        log.info("命令: {} 执行结果: {}", command, code);
        exec.waitFor();
        return code;
    }

    private static class OutputHandlerRunnable implements Runnable {
        private InputStream in;

        private boolean error;

        public OutputHandlerRunnable(InputStream in, boolean error) {
            this.in = in;
            this.error = error;
        }

        @Override
        public void run() {
            try (BufferedReader bufr = new BufferedReader(new InputStreamReader(this.in))) {
                String line = null;
                while ((line = bufr.readLine()) != null) {
                    if (error) {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}