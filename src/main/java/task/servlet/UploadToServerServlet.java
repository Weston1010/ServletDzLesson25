package task.servlet;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/load-book")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 100)
public class UploadToServerServlet extends HttpServlet {
    //Написать приложение, которое при запуске будет сообщать в консоль, что оно
    //работает. На любой запрос в консоли должна отображаться запись со временем этого
    //запроса.
    //Создать 2 эндпоинта:
    // /book - который будет скачивать с сервера клиенту любую книгу.
    // /load-book - который позволит загружать свои книги на сервер.


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<title>Load-book</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3> Choose File to Upload in Server </h3>");
            out.println("<form action='/load-book' method='post' enctype='multipart/form-data'>");
            out.println("<input type='file' name='file' />");
            out.println("<input type='submit' value='Upload'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Part filePart = req.getPart("file");
            String name = filePart.getSubmittedFileName();
            filePart.write("D:\\Books\\" + name);
            resp.getWriter().print("The file has been successfully uploaded!");
        } catch (Exception e) {
            resp.getWriter().print("Something went wrong!");
        }
    }
}
