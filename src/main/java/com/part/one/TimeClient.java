package com.part.one;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**时间测试类.
 *
 */
public class TimeClient {
	/**主方法.
	 * @param args 入参
	 */
	public static void main(final String[] args) {

		int port = 0;

		if (args != null && args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 9090;
		}

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket("127.0.0.1", port);
			in = new BufferedReader(new InputStreamReader(socket
							.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("QUERY TIME ORDER");
			String resp = in.readLine();
			System.out.println(resp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			if (out != null) {
				out.close();
				out = null;
			}

			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}

