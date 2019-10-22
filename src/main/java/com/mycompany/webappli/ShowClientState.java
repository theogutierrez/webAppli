package com.mycompany.webappli;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;

@WebServlet(name = "ShowClientState", urlPatterns = {"/ShowClientState"})
public class ShowClientState extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet ShowClient</title>");
			out.println("</head>");
			out.println("<body>");
			try {	// Trouver la valeur du paramètre HTTP state
                                String state = request.getParameter("state");
				if (state == null) {
					throw new Exception("La paramètre state n'a pas été transmis");
				}
                                
				DAO dao = new DAO(DataSourceFactory.getDataSource());
                                List<CustomerEntity> listCustomer = dao.customersInState(state);
				if (listCustomer == null) {
					throw new Exception("state inconnu");
				}
				// Afficher les propriétés des clients	
                                out.println("<table style =\"width:100%\">");
                                out.println("<tr><th>Id</th><th>Name</th><th>Address</th></tr>");
                                 
                                for(int i=0; i<listCustomer.size(); i++) {
                                            out.println("<tr>");
                                            out.println("<td>" + listCustomer.get(i).getCustomerId() + "</td>");
                                            out.println("<td>" + listCustomer.get(i).getName() + "</td>");
                                            out.println("<td>" + listCustomer.get(i).getAddressLine1() + "</td>");
                                            out.println("</tr>");
                                }
                                out.println("</table>");
			} catch (Exception e) {
				out.printf("Erreur : %s", e.getMessage());
			}
			out.printf("<hr><a href='%s'>Retour au menu</a>", request.getContextPath());
			out.println("</body>");
			out.println("</html>");
		} catch (Exception ex) {
			Logger.getLogger("servlet").log(Level.SEVERE, "Erreur de traitement", ex);
		}
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}

}