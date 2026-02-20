package task.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/book")
public class UploadToUserServlet extends HttpServlet {
    //Написать приложение, которое при запуске будет сообщать в консоль, что оно
    //работает. На любой запрос в консоли должна отображаться запись со временем этого
    //запроса.
    //Создать 2 эндпоинта:
    // /book - который будет скачивать с сервера клиенту любую книгу.
    // /load-book - который позволит загружать свои книги на сервер.

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileName = ("D:\\Books\\Code_The Hidden Language of Computer Hardware and Software Charles Petzold(ru).pdf");
        File downloadFile = new File(fileName);

        if (!downloadFile.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        String mimeType = getServletContext().getMimeType(fileName);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        resp.setContentType(mimeType);

        resp.setContentLength((int) downloadFile.length());

        resp.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());

        try (FileInputStream inStream = new FileInputStream(downloadFile)) {
            OutputStream outStream = resp.getOutputStream();

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
