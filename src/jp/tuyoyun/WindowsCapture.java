package jp.tuyoyun;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class WindowsCapture {
	static String fileName = "capture.jpg";

	/** ファイルのフォーマット */
	private static SimpleDateFormat textFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/** 実行メソッド */
	public static void main(String[] args) {

		// 入力チェック
		if (args.length != 1) {
			throw new IllegalArgumentException("引数の数が異なります（usage:javaw capture.ScreenCaptureTask dir interval）");
		}

		// 入力チェック(1)：出力先ディレクトリ
		baseDir = new File(args[0]);
		if (baseDir.exists() == false) {
			throw new IllegalArgumentException("dirが存在しません（usage:javaw capture.ScreenCaptureTask dir interval）");
		}
		if (args.length > 1) {
			fileName = args[1];
		}
		run();
	}

	/** 出力ベースディレクトリ */
	private static File baseDir;

	public WindowsCapture(File baseDir) {
		this.baseDir = baseDir;
	}

	/** タスクの本体 */
	public static void run() {
		for (int i = 0; i < 10; i++) {
			try {
				getCapture();
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void getCapture() throws AWTException, IOException {
		Date today = new Date();

		// スクリーンサイズの取得
		Rectangle bounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

		// これで画面キャプチャ
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(bounds);
		Graphics g = image.getGraphics();
		// 背景（もっといいやり方ないのか？）
		g.setColor(Color.WHITE);
		g.fillRoundRect(10, 25, 230, 25, 5, 5);
		// 画像に日付を入れる（背景との調整が面倒。簡単にできないのか？）
		g.setFont(new Font("SansSerif", Font.BOLD, 24));
		g.setColor(Color.RED);
		g.drawString(textFormat.format(today), 15, 45);

		// 以下、出力処理
		File file = new File(baseDir, fileName);

		ImageIO.write(image, "jpg", file);
		System.out.println("出力完了:" + file.getAbsolutePath());
	}

}