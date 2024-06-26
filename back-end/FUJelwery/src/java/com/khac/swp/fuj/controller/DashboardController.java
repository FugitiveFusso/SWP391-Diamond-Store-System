/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khac.swp.fuj.controller;

import com.khac.swp.fuj.category.CategoryDAO;
import com.khac.swp.fuj.category.CategoryDTO;
import com.khac.swp.fuj.certificate.CertificateDAO;
import com.khac.swp.fuj.certificate.CertificateDTO;
import com.khac.swp.fuj.collection.CollectionDAO;
import com.khac.swp.fuj.collection.CollectionDTO;
import com.khac.swp.fuj.diamond.DiamondDAO;
import com.khac.swp.fuj.diamond.DiamondDTO;
import com.khac.swp.fuj.diamondprice.DiamondPriceDAO;
import com.khac.swp.fuj.diamondprice.DiamondPriceDTO;
import com.khac.swp.fuj.posts.PostDAO;
import com.khac.swp.fuj.posts.PostDTO;
import com.khac.swp.fuj.ringplacementprice.RingPlacementPriceDAO;
import com.khac.swp.fuj.ringplacementprice.RingPlacementPriceDTO;
import com.khac.swp.fuj.users.UserDAO;
import com.khac.swp.fuj.users.UserDTO;
import com.khac.swp.fuj.voucher.VoucherDAO;
import com.khac.swp.fuj.voucher.VoucherDTO;
import com.khac.swp.fuj.warranty.WarrantyDAO;
import com.khac.swp.fuj.warranty.WarrantyDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author phucu
 */
public class DashboardController extends HttpServlet {

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
            String action = request.getParameter("action");
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("managersession") == null) {
                response.sendRedirect("managerlogin.jsp");
                return;
            } else if (action == null || action.equals("list")) {//lists
                //Users
                UserDAO userDao = new UserDAO();
                List<UserDTO> usList = userDao.listStatistics();
                request.setAttribute("uslist", usList);
                //Staffs
                List<UserDTO> usListStaff = userDao.listStaff();
                request.setAttribute("usliststaff", usListStaff);
                //Categories
                CategoryDAO categoyrDao = new CategoryDAO();
                List<CategoryDTO> caList = categoyrDao.listStatistics();
                request.setAttribute("calist", caList);
                //Collections
                CollectionDAO collectionDao = new CollectionDAO();
                List<CollectionDTO> coList = collectionDao.listStatistics();
                request.setAttribute("colist", coList);
                //Vouchers
                VoucherDAO voucherDao = new VoucherDAO();
                List<VoucherDTO> vouList = voucherDao.listStatistics();
                request.setAttribute("voulist", vouList);
                //Posts
                PostDAO postDao = new PostDAO();
                PostDTO post = postDao.loadStatistics();
                request.setAttribute("post", post);
                //Certificates
                CertificateDAO certificateDao = new CertificateDAO();
                List<CertificateDTO> certList = certificateDao.listStatistics();
                request.setAttribute("certlist", certList);
                //DiamondPrices
                DiamondPriceDAO diamondPriceDao = new DiamondPriceDAO();
                DiamondPriceDTO diamondPrice = diamondPriceDao.loadStatistics();
                request.setAttribute("diamondprice", diamondPrice);
                //Diamonds
                DiamondDAO diamondDao = new DiamondDAO();
                DiamondDTO diamond = diamondDao.loadStatistics();
                request.setAttribute("diamond", diamond);
                //RPPs1
                RingPlacementPriceDAO ringPlacementPriceDao = new RingPlacementPriceDAO();
                RingPlacementPriceDTO rppa = ringPlacementPriceDao.loadStatisticsA();
                request.setAttribute("rppa", rppa);
                //RPPs2
                List<RingPlacementPriceDTO> rppListA = ringPlacementPriceDao.getStatisticsA();
                request.setAttribute("rpplista", rppListA);
                //RPPs3
                List<RingPlacementPriceDTO> rppListB = ringPlacementPriceDao.getStatisticsB();
                request.setAttribute("rpplistb", rppListB);
                //Warranties
                WarrantyDAO warrantyDao = new WarrantyDAO();
                WarrantyDTO warranty = warrantyDao.loadStatistics();
                request.setAttribute("warranty", warranty);
                
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

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
