package uk.nickbdyer.chatserver.mockObjects;

import java.io.IOException;
import java.io.InputStream;

public class UnReadableInputStream extends InputStream {

    @Override
    public int read() throws IOException {
        throw new IOException();
    }

}
