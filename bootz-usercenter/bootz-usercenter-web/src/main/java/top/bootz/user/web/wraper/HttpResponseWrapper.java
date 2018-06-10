package top.bootz.user.web.wraper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * reponse相应对象的包装类，用来获取响应体中的数据
 * @author John
 *
 */
public class HttpResponseWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream buffer = null;

	private ServletOutputStream out = null;

	private PrintWriter writer = null;

	public HttpResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		buffer = new ByteArrayOutputStream();// 真正存储数据的流
		out = new WapperedOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
	}

	// 重载父类获取outputstream的方法
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}

	// 重载父类获取writer的方法
	@Override
	public PrintWriter getWriter() throws UnsupportedEncodingException {
		return writer;
	}

	// 重载父类获取flushBuffer的方法
	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		}
		if (writer != null) {
			writer.flush();
		}
	}

	@Override
	public void reset() {
		buffer.reset();
	}

	/**
	 * 获取response中的内容
	 * @return
	 * @throws IOException
	 */
	public byte[] getContent() throws IOException {
		// 将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
		flushBuffer();
		return buffer.toByteArray();
	}

	// 内部类，对ServletOutputStream进行包装
	private class WapperedOutputStream extends ServletOutputStream {
		
		private ByteArrayOutputStream bos = null;

		public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
			bos = stream;
		}

		@Override
		public void write(int b) throws IOException {
			bos.write(b);
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			// do nothing
		}
		
	}

}
