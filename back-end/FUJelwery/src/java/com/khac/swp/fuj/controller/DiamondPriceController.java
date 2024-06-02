package com.khac.swp.fuj.controller;

import com.khac.swp.fuj.diamondprice.DiamondPriceDAO;
import com.khac.swp.fuj.diamondprice.DiamondPriceDTO;
import com.khac.swp.fuj.utils.DBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DiamondPriceController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            DiamondPriceDAO dpDAO = new DiamondPriceDAO();
            String action = request.getParameter("action");
            String keyword = request.getParameter("keyword");
            if (keyword == null) {
                keyword = "";
            }
            String sortCol = request.getParameter("colSort");

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("salessession") == null) {
                response.sendRedirect("saleslogin.jsp");
                return;
            } else if (action == null || action.equals("list")) {//lists

                DiamondPriceDAO dao = new DiamondPriceDAO();
                List<DiamondPriceDTO> list = dao.getAllDiamondPrice(keyword, sortCol);
                request.setAttribute("dplist", list);

                request.getRequestDispatcher("/dplist.jsp").forward(request, response);

            } else if (action.equals("details")) {//details

                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                DiamondPriceDTO dp = null;
                if (id != null) {
                    dp = dpDAO.load(id);
                }

                request.setAttribute("dp", dp);//object
                RequestDispatcher rd = request.getRequestDispatcher("dpdetails.jsp");
                rd.forward(request, response);
            } else if (action.equals("edit")) {//edit
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                DiamondPriceDTO dp = null;
                if (id != null) {
                    dp = dpDAO.load(id);
                }

                request.setAttribute("dp", dp);
                request.setAttribute("nextaction", "update");
                RequestDispatcher rd = request.getRequestDispatcher("dpedit.jsp");
                rd.forward(request, response);

            } else if (action.equals("create")) {//create
                DiamondPriceDTO dp = new DiamondPriceDTO();
                request.setAttribute("dp", dp);
                request.setAttribute("nextaction", "insert");
                RequestDispatcher rd = request.getRequestDispatcher("dpedit.jsp");
                rd.forward(request, response);

            } else if (action.equals("update")) {//update
                Integer dpid = null;
                try {
                    dpid = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }
                Double size = null;
                try {
                    size = Double.parseDouble(request.getParameter("diamondSize"));
                } catch (NumberFormatException ex) {
                    log("Parameter size has wrong format.");
                }
                Double caratWeight = null;
                try {
                    caratWeight = Double.parseDouble(request.getParameter("caratWeight"));
                } catch (NumberFormatException ex) {
                    log("Parameter caratWeight has wrong format.");
                }
                String color = request.getParameter("color");
                String clarity = request.getParameter("clarity");
                String cut = request.getParameter("cut");
                Integer price = null;
                try {
                    price = Integer.parseInt(request.getParameter("price"));
                } catch (NumberFormatException ex) {
                    log("Parameter price has wrong format.");
                }

                DiamondPriceDTO dp = null;
                if (dpid != null) {
                    dp = dpDAO.load(dpid);
                }
                dp.setId(dpid);
                dp.setSize(size);
                dp.setCaratWeight(caratWeight);
                dp.setColor(color);
                dp.setClarity(clarity);
                dp.setCut(cut);
                dp.setPrice(price);
                dpDAO.update(dp);

                request.setAttribute("dp", dp);
                RequestDispatcher rd = request.getRequestDispatcher("dpdetails.jsp");
                rd.forward(request, response);

            } else if (action.equals("insert")) {//insert
                try {
                    Connection conn = DBUtils.getConnection();
                    int dpid = 0;
                    Double size = null;
                    try {
                        size = Double.parseDouble(request.getParameter("diamondSize"));
                    } catch (NumberFormatException ex) {
                        log("Parameter size has wrong format.");
                    }
                    Double caratWeight = null;
                    try {
                        caratWeight = Double.parseDouble(request.getParameter("caratWeight"));
                    } catch (NumberFormatException ex) {
                        log("Parameter caratWeight has wrong format.");
                    }
                    String color = request.getParameter("color");
                    String clarity = request.getParameter("clarity");
                    String cut = request.getParameter("cut");
                    Integer price = null;
                    try {
                        price = Integer.parseInt(request.getParameter("price"));
                    } catch (NumberFormatException ex) {
                        log("Parameter price has wrong format.");
                    }

                    PreparedStatement ps = conn.prepareStatement("select max(dpID) from [DiamondPrice]");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        dpid = rs.getInt(1);
                        dpid++;
                    }
                    DiamondPriceDTO dp = new DiamondPriceDTO();
                    dp.setId(dpid);
                    dp.setSize(size);
                    dp.setCaratWeight(caratWeight);
                    dp.setColor(color);
                    dp.setClarity(clarity);
                    dp.setCut(cut);
                    dp.setPrice(price);
                    dpDAO.insert(dp);
                    request.setAttribute("dp", dp);
                    RequestDispatcher rd = request.getRequestDispatcher("dpdetails.jsp");
                    rd.forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Insert dp error!" + ex.getMessage());
                    ex.printStackTrace();
                }

            } else if (action.equals("delete")) {//delete

                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                dpDAO.delete(id);

                List<DiamondPriceDTO> list = dpDAO.getAllDiamondPrice(keyword, sortCol);
                request.setAttribute("dplist", list);
                RequestDispatcher rd = request.getRequestDispatcher("dplist.jsp");
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    }// </editor-fold>

}
