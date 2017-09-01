package com.codingblocks;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;

public class Server extends NanoHTTPD {
    public Server(int port) throws IOException {
        super(port);
        start();
    }

    public static void serve() {
        try {
            new Server(8080);
        } catch (Exception e) {
            System.err.println("Couldn't start server:\n" + e);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {

        System.out.println(session.getUri());

        if(session.getUri().startsWith("/pause")){
            Application.pause();
            return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "PAUSE");
        }

        if(session.getUri().startsWith("/skip")){
            Application.skip();
            return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "SKIP");
        }

        if(session.getUri().startsWith("/rewind")){
            Application.rewind();
            return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "REWIND");
        }

        InputStream is = Server.class.getClassLoader().getResourceAsStream("index.html");
        final Response resp;
        try {
            resp = newChunkedResponse(Response.Status.OK, "MIME_PLAINTEXT", is);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "ERROR");
        }

    }
}
