package me.aifaq.commons.lang;

import com.google.common.base.Preconditions;
import me.aifaq.commons.lang.annotation.ThreadSafe;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 获取图片验证码
 */
@ThreadSafe
public class ImageVerifyCodeUtil {
	public static final ImageVerifyCodeUtil INSTANCE = new ImageVerifyCodeUtil();

	private static final int DEFAULT_IMG_WIDTH = 112;
	private static final int DEFAULT_IMG_HEIGHT = 56;
	// 默认的验证码数量，默认四个
	private static final int DEFAULT_CODE_COUNT = 4;

	public static enum CodeType {
		// 数字
		NUMBER,
		// 字母
		CHAR,
		// 混合
		MIXED,
	}

	private final int imgWidth;
	private final int imgHeight;
	private final CodeType codeType;
	private final int codeCount;

	private final int codeX;

	private final int fontHeight;

	private final int codeY;

	ImageVerifyCodeUtil() {
		this(DEFAULT_IMG_WIDTH, DEFAULT_IMG_HEIGHT, CodeType.MIXED, DEFAULT_CODE_COUNT);
	}

	/**
	 * @param imgWidth  验证码图片宽度
	 * @param imgHeight 验证码图片高度
	 * @param codeType  验证码类型
	 * @param codeCount 验证码数量
	 */
	public ImageVerifyCodeUtil(int imgWidth, int imgHeight, CodeType codeType, int codeCount) {
		Preconditions.checkArgument(imgWidth > 0);
		Preconditions.checkArgument(imgHeight > 0);
		Preconditions.checkNotNull(codeType);
		Preconditions.checkArgument(codeCount > 0);

		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.codeType = codeType;
		this.codeCount = codeCount;

		this.codeX = imgWidth / (codeCount + 1);
		this.fontHeight = imgHeight - 2;
		this.codeY = imgHeight - 12;
	}

	/**
	 * 生成图片验证码
	 *
	 * @return Pair<验证码, 图片流>
	 */
	public Pair<String/*验证码*/, BufferedImage/*图片流*/> generate() {
		// 在内存中创建图象
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics2D g = image.createGraphics();
		image = g.getDeviceConfiguration()
				.createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);

		g.dispose();

		g = image.createGraphics();

		// 设定背景色
		//	g.setColor(new Color(250,0,0));
		//	g.fillRect(0, 0, imgWidth, imgHeight);

		// 设定字体
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN + Font.ITALIC, fontHeight));

		// 画边框
		//g.setColor(new Color(55, 55, 12));
		//g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 160; i++) {
			int x = ThreadLocalRandom.current().nextInt(imgWidth);
			int y = ThreadLocalRandom.current().nextInt(imgHeight);
			int xl = ThreadLocalRandom.current().nextInt(12);
			int yl = ThreadLocalRandom.current().nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		String sRand = "";
		int red = 0, green = 0, blue = 0;
		char[] arr;
		switch (codeType) {
		case NUMBER:
			arr = getNumberChar(codeCount);
			break;
		case CHAR:
			arr = getLetterChar(codeCount);
			break;
		default:
			arr = getMixedChar(codeCount);
			break;
		}

		for (int i = 0; i < codeCount; i++) {
			red = ThreadLocalRandom.current().nextInt(255);
			green = ThreadLocalRandom.current().nextInt(255);
			blue = ThreadLocalRandom.current().nextInt(255);
			sRand += String.valueOf(arr[i]);
			g.setColor(new Color(red, green, blue));
			g.drawString(String.valueOf(arr[i]), (i) * codeX + codeX / 3, codeY);
		}
		// 图象生效
		g.dispose();

		return Pair.of(sRand, image);
	}

	/**
	 * 给定范围获得随机颜色
	 */
	static Color getRandColor(int fc, int bc) {
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + ThreadLocalRandom.current().nextInt(bc - fc);
		int g = fc + ThreadLocalRandom.current().nextInt(bc - fc);
		int b = fc + ThreadLocalRandom.current().nextInt(bc - fc);
		return new Color(r, g, b);
	}

	static char[] getNumberChar(int codeCount) {
		char[] arr = new char[codeCount];
		for (int i = 0; i < codeCount; i++) {
			int r = ThreadLocalRandom.current().nextInt(10) + 48;
			arr[i] = (char) r;
		}
		return arr;
	}

	static char[] getLetterChar(int codeCount) {
		char[] arr = new char[codeCount];
		for (int i = 0; i < codeCount; i++) {
			int r = ThreadLocalRandom.current().nextInt(26);
			int t = ThreadLocalRandom.current().nextInt(2);
			// 小写
			if (t == 0) {
				r += 97;
			}
			// 大写
			else {
				r += 65;
			}
			arr[i] = (char) r;
		}
		return arr;
	}

	static char[] getMixedChar(int codeCount) {
		char[] arr = new char[codeCount];
		for (int i = 0; i < codeCount; i++) {
			int r = ThreadLocalRandom.current().nextInt(26);
			int t = ThreadLocalRandom.current().nextInt(3);
			// 小写
			if (t == 0) {
				r += 97;
			}
			// 大写
			else if (t == 1) {
				r += 65;
			}
			//数字
			else {
				r = r % 10 + 48;
			}
			arr[i] = (char) r;
		}
		return arr;
	}
}
