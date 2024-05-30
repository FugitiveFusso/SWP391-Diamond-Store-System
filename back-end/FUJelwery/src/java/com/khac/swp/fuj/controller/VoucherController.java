/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khac.swp.fuj.controller;

import com.khac.swp.fuj.voucher.VoucherDAO;
import com.khac.swp.fuj.voucher.VoucherDTO;
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

/**
 *
 * @author phucu
 */
public class VoucherController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
            /* TODO output your page here. You may use following sample code. */
            VoucherDAO voucherDAO = new VoucherDAO();
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

                VoucherDAO dao = new VoucherDAO();
                List<VoucherDTO> list = dao.getAllVoucher(keyword, sortCol);
                request.setAttribute("voucherlist", list);

                request.getRequestDispatcher("/voucherlist.jsp").forward(request, response);

            } else if (action.equals("details")) {//details

                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                VoucherDTO voucher = null;
                if (id != null) {
                    voucher = voucherDAO.load(id);
                }

                request.setAttribute("voucher", voucher);//object
                RequestDispatcher rd = request.getRequestDispatcher("voucherdetails.jsp");
                rd.forward(request, response);
            } else if (action.equals("edit")) {//edit
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                VoucherDTO voucher = null;
                if (id != null) {
                    voucher = voucherDAO.load(id);
                }

                request.setAttribute("voucher", voucher);
                request.setAttribute("nextaction", "update");
                RequestDispatcher rd = request.getRequestDispatcher("voucheredit.jsp");
                rd.forward(request, response);

            } else if (action.equals("create")) {//create
                VoucherDTO voucher = new VoucherDTO();
                request.setAttribute("voucher", voucher);
                request.setAttribute("nextaction", "insert");
                RequestDispatcher rd = request.getRequestDispatcher("voucheredit.jsp");
                rd.forward(request, response);

            } else if (action.equals("update")) {//update
                Integer voucherid = null;
                try {
                    voucherid = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }
                String vouchername = request.getParameter("voucherName");
                String voucherimage = request.getParameter("voucherImage");
                String description = request.getParameter("description");
                String coupon = request.getParameter("coupon");
                Integer percentage = null;
                try {
                    percentage = Integer.parseInt(request.getParameter("percentage"));
                } catch (NumberFormatException ex) {
                    log("Parameter percentage has wrong format.");
                }
                VoucherDTO voucher = null;
                if (voucherid != null) {
                    voucher = voucherDAO.load(voucherid);
                }
                voucher.setId(voucherid);
                voucher.setName(vouchername);
                voucher.setImage(voucherimage);
                voucher.setDescription(description);
                voucher.setCoupon(coupon);
                voucher.setPercentage(percentage);
                voucherDAO.update(voucher);

                request.setAttribute("voucher", voucher);
                RequestDispatcher rd = request.getRequestDispatcher("voucherdetails.jsp");
                rd.forward(request, response);

            } else if (action.equals("insert")) {//insert
                try {
                    Connection conn = DBUtils.getConnection();
                    int voucherid = 0;
                    String vouchername = request.getParameter("voucherName");
                    String voucherimage = request.getParameter("voucherImage");
                    String description = request.getParameter("description");
                    String coupon = request.getParameter("coupon");
                    Integer percentage = null;
                    try {
                        percentage = Integer.parseInt(request.getParameter("percentage"));
                    } catch (NumberFormatException ex) {
                        log("Parameter percentage has wrong format.");
                    }
                    PreparedStatement ps = conn.prepareStatement("select max(voucherID) from [Voucher]");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        voucherid = rs.getInt(1);
                        voucherid++;
                    }
                    VoucherDTO voucher = new VoucherDTO();
                    voucher.setId(voucherid);
                    voucher.setName(vouchername);
                    voucher.setImage(voucherimage);
                    voucher.setDescription(description);
                    voucher.setCoupon(coupon);
                    voucher.setPercentage(percentage);
                    voucherDAO.insert(voucher);
                    request.setAttribute("voucher", voucher);
                    RequestDispatcher rd = request.getRequestDispatcher("voucherdetails.jsp");
                    rd.forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Insert voucher error!" + ex.getMessage());
                    ex.printStackTrace();
                }

            } else if (action.equals("delete")) {//delete

                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException ex) {
                    log("Parameter id has wrong format.");
                }

                voucherDAO.delete(id);

                List<VoucherDTO> list = voucherDAO.getAllVoucher(keyword, sortCol);
                request.setAttribute("voucherlist", list);
                RequestDispatcher rd = request.getRequestDispatcher("voucherlist.jsp");
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
