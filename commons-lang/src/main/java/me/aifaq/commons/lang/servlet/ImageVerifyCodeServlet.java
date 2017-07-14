package me.aifaq.commons.lang.servlet;

import me.aifaq.commons.lang.ImageVerifyCodeUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Wang Wei [5waynewang@gmail.com]
 * @since 10:09 2017/5/17
 */
public class ImageVerifyCodeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Pair<String/*验证码*/, BufferedImage/*图片流*/> pair = ImageVerifyCodeUtil.INSTANCE.generate();

		// 钩子
		generateImageVerifyCodeHook(req, resp, pair);

		resp.setContentType("image/png");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		ServletOutputStream output = null;
		try {
			output = resp.getOutputStream();

			// 输出图象到页面
			ImageIO.write(pair.getRight(), "PNG", output);

			output.flush();
		}
		finally {
			IOUtils.closeQuietly(output);
		}
	}

	/**
	 * 钩子，可以缓存验证码，校验用到
	 */
	protected void generateImageVerifyCodeHook(HttpServletRequest req, HttpServletResponse resp,
			Pair<String/*验证码*/, BufferedImage/*图片流*/> pair) {
	}
}
