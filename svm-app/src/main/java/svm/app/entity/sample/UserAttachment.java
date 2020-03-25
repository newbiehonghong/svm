package svm.app.entity.sample;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAttachment {
    private Long attachmentId;

    private String fileName;

    private String fileType;

    private byte[] content;
}
