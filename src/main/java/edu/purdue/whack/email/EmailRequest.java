package edu.purdue.whack.email;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmailRequest {

    private final String emailAddr;
    private final Collection<Attachment> attachments;

    public static class Attachment {

        private final File file;

        public Attachment(File file) {
            this.file = file;
        }

        public byte[] readBytes() throws IOException {
            var reader = new FileInputStream(this.file);
            return reader.readAllBytes();
        }

        public File getFile() {
            return this.file;
        }
    }

    public EmailRequest(String emailAddr, Collection<File> files) {
        this.emailAddr = emailAddr;
        this.attachments = files.stream().map(Attachment::new).collect(Collectors.toUnmodifiableList());
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public Collection<Attachment> getAttachments() {
        return attachments;
    }
}
