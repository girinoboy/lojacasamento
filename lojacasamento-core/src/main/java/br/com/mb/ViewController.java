package br.com.mb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "viewController", urlPatterns = {"/WEB/*"},
initParams = @WebInitParam(name = "renderer-class-name", value = "com.logicbig.HtmlRenderer"),
loadOnStartup = 1)
public class ViewController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		
		resp.addHeader("Access-Control-Allow-Origin", "https://sandbox.pagseguro.uol.com.br");

		String renderer = getServletConfig().getInitParameter("renderer-class-name");
		PrintWriter writer = resp.getWriter();
		writer.println("renderer: " + renderer);

		String servletName = getServletConfig().getServletName();
		writer.println("servlet name " + servletName);
		
		writer.println("https://sandbox.pagseguro.uol.com.br");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Origin", "https://sandbox.pagseguro.uol.com.br");
		super.doPost(req, resp);
		System.out.println(1);
	}
}
