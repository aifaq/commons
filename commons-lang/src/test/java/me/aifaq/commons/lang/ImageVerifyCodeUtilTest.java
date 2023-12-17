package me.aifaq.commons.lang;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 19:14 2017/6/13
 */
public class ImageVerifyCodeUtilTest {
	@Test
	public void testGenerate() throws IOException {
		final Pair<String, BufferedImage> pair = ImageVerifyCodeUtil.INSTANCE.generate();

		System.out.println(pair.getKey());

		final String file = "~/Downloads/VerifyCode.PNG";

		ImageIO.write(pair.getRight(), "PNG", new File(file));
	}
}
