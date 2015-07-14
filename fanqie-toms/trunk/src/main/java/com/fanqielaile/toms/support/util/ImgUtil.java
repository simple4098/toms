package com.fanqielaile.toms.support.util;

import com.fanqie.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * DESC :
 *
 * @author : 番茄木-ZLin
 * @data : 2015/7/14
 * @version: v1.0.0
 */
public class ImgUtil {
    private ImgUtil(){}

    public static BufferedImage resize(BufferedImage source, double d) {
// targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        int targetW = (int)(d * source.getWidth());
        int targetH = (int)(d * source.getHeight());
        if (type == BufferedImage.TYPE_CUSTOM) {//handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(d, d));
        g.dispose();
        return target;
    }

    public static byte[] compressionImg(String imgUrl)throws Exception{
        byte[] bytes = HttpClientUtil.readImg(imgUrl);
        String imgSuffix = StringUtils.substring(imgUrl, imgUrl.lastIndexOf(".") + 1);
        if (bytes.length >= 500*1024){
            InputStream stream = HttpClientUtil.readImgStream(imgUrl);
            BufferedImage src = ImageIO.read(stream);
            BufferedImage image = resize(src,0.3);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image,imgSuffix,byteArrayOutputStream);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            return toByteArray;
        }
        return bytes;
    }
}
