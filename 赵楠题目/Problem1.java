import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2 {

    public static void main(String args[]) {
        String baseUrl = "http://tju.com/";
        Map<String, Long> result = staticProfits(baseUrl);
        if (result == null || result.size() == 0) {
            System.out.println("统计收益信息失败，请检查网址是否正确或网络连接是否正常");
            return;
        }
        List<Map.Entry<String, Long>> profits = new ArrayList<>(result.entrySet());
        //根据收益排序
        Collections.sort(profits, (p1, p2) -> (int) (p1.getValue() - p2.getValue()));
        System.out.println("收益最好的为：" + profits.get(0));
        System.out.println("收益最差的为" + profits.get(profits.size() - 1));
    }

    static class MyTask implements Runnable {

        private final CountDownLatch latch;
        //保存统计结果
        private final ConcurrentHashMap<String, Long> profitsMap;
        //文件网址
        private final String baseUrl;
        //当前下载的文件编号
        private AtomicInteger counter = new AtomicInteger(0);
        //保存文件的临时目录
        private String tmpDir;

        MyTask(ConcurrentHashMap<String, Long> profitsMap, String baseUrl, CountDownLatch latch, String tmpDir) {
            this.profitsMap = profitsMap;
            this.baseUrl = baseUrl;
            this.latch = latch;
            this.tmpDir = tmpDir;
        }

        MyTask(ConcurrentHashMap<String, Long> profitsMap, String baseUrl, CountDownLatch latch) {
            //默认使用当前目录
            this(profitsMap, baseUrl, latch, System.getProperty("user.dir"));
        }

        @Override
        public void run() {
            while (true) {
                String filename = String.format("%03d.dat", counter.getAndIncrement());
                String fileUrl = baseUrl + filename;
                if (counter.get() > 36) {
                    latch.countDown();
                    System.err.printf("线程%s结束\n", Thread.currentThread().getName());
                    break;
                }
                //从网络下载文件
                try {
                    downLoadFromUrl(fileUrl, tmpDir, filename);
                } catch (IOException e) {
                    System.out.printf("从服务器下载文件%s出错, 文件地址为%s\n", filename, fileUrl);
//                    e.printStackTrace();
                    continue;
                }
                List<String> lines = null;
                try {
                    lines = Files.readAllLines(new File(tmpDir + File.separator + filename).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //文件为空
                if (lines == null) {
                    continue;
                }
                for (String line : lines) {
                    String[] nameAndProfit = line.split(",");
                    //跳过空白行
                    if (line.length() < 2) {
                        continue;
                    }
                    String name = nameAndProfit[0].intern();
                    StringBuilder profitStr = new StringBuilder();
                    //把收益的数组拼接起来,比如100,00会被split城100和00
                    for (int i = 1; i < nameAndProfit.length; i++) {
                        //去掉空格
                        profitStr.append(nameAndProfit[i].intern());
                    }
                    Long profit = Long.valueOf(profitStr.toString());
                    //把收益保存到map
                    profitsMap.merge(name, profit, (oldVal, nowProfit) -> oldVal + nowProfit);
                }
            }
        }
    }

    private static Map<String, Long> staticProfits(String baseUrl) {
        ConcurrentHashMap<String, Long> profitsMap = new ConcurrentHashMap<>();
        int runnerNum = 20;
        CountDownLatch latch = new CountDownLatch(runnerNum);
        Runnable runnable = new MyTask(profitsMap, baseUrl, latch);
        ExecutorService executorService = Executors.newFixedThreadPool(runnerNum);
        for (int i = 0; i < 20; i++) {
            executorService.execute(runnable);
        }
        //等待线程执行完成
        try {
            latch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return profitsMap;
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        fos.close();
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}

