package com.drpweb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ADMIN on 12/18/2016.
 */
//@WebServlet(name="connectRunKeeperServlet", urlPatterns = {"/connectRunKeeperServlet"},loadOnStartup = 1)
public class connectRunKeeperServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //content type must be set to text/event-stream
        response.setContentType("text/event-stream");
        //cache must be set to no-cache
        response.setHeader("Cache-Control", "no-cache");
        //encoding is set to UTF-8
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        for(int i=0; i<10; i++) {
            System.out.println(i);
            writer.write("data: "+ i +"\r\n");
            writer.flush();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writer.close();
    }
}
