package com.learn.security.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Ale on 2020/9/7
 */
public class ValidateCodeBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeBuilder.class);

    // 图片的宽度。
    private int width = 160;

    // 图片的高度。
    private int height = 40;

    // 验证码字符个数
    private int codeCount = 4;

    // 验证码干扰线数
    private int lineCount = 150;

    // 验证码
    private String code = null;

    // 绑定验证码的uuid
    private String uuid = null;

    // 验证码图片Buffer
    private BufferedImage buffImg = null;

    // Base64图片前缀
    private static final String DATA = "data:image/jpg;base64,";

    // 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * @param width  图片宽
     * @param height 图片高
     */
    public ValidateCodeBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.createCode();
    }

    public ValidateCodeBuilder() {
        this.width = width;
    }

    /**
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public ValidateCodeBuilder(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.code = this.createCode();
    }

    /**
     * 创建验证码
     *
     * @return
     */
    public String createCode() {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);//每个字符的宽度(左右各空出一个字符)
        fontHeight = height - 2;//字体的高度
        codeY = height - 4;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体,可以修改为其它的
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            // 设置随机开始和结束坐标
            int xs = random.nextInt(width); // x坐标开始
            int ys = random.nextInt(height);// y坐标开始
            int xe = xs + random.nextInt(width / 8);    // x坐标结束
            int ye = ys + random.nextInt(height / 8);   // y坐标结束

            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        code = randomCode.toString();
        return code;
    }

//    public void write(String path) throws IOException {
//        OutputStream sos = new FileOutputStream(path);
//        this.write(sos);
//    }
//
//    public void write(OutputStream sos) throws IOException {
//        ImageIO.write(buffImg, "png", sos);
//        sos.close();
//    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code;
    }

    /**
     * 图片转Base64
     *
     * @param bufferedImage
     * @param png
     * @return
     * @throws IOException
     */
    public String imageToBytes(BufferedImage bufferedImage, String png) throws IOException {
        // io流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // 写入流中
        ImageIO.write(bufferedImage, png, bos);
        // 转换成字节
        byte[] bytes = bos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        // 转换成base64串
        String pngBase64 = encoder.encodeBuffer(bytes).trim();
        // 删除 \r\n
        pngBase64 = pngBase64.replaceAll("\n", "").replaceAll("\r", "");
        String path = DATA.concat(pngBase64);
        return path;
    }
}
