package ir.ac.ut.ece.ie;

import ir.ac.ut.ece.ie.dynamiccontentserver.DynamicContentServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DynamicContentServer dcs = new DynamicContentServer();
        dcs.start();
    }
}
