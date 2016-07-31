package uk.nickbdyer.chatserver.testdoubles;

import java.io.IOException;
import java.io.InputStream;

public class UnReadableInputStreamStub extends InputStream {

    @Override
    public int read() throws IOException {
        throw new IOException();
    }

}
