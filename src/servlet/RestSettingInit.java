package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import commons.CalculateSetting;

/**
 * Servlet implementation class RestSettingInit
 */
@WebServlet("/RestSettingInit")
public class RestSettingInit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestSettingInit() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
		CalculateSetting.getInstance();
	}

	

}
