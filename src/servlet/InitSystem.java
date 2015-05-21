package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import commons.CalculateSetting;
import commons.RemoteServiceBridge;

import dao.DaoFactory;
import flk.PhotoQuerySearch;

/**
 * Servlet implementation class InitSystem
 * for initialisation of some pre-setting datas
 */
public class InitSystem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitSystem() {
        super();
    }

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("start to load singleton bean in the simple e-comerce system");
		CalculateSetting.getInstance();
		PhotoQuerySearch.getInstance();
		RemoteServiceBridge.getInstance();
		DaoFactory.getInstance();
		System.out.println("end load singleton bean in the simple e-comerce system");
	}

}
