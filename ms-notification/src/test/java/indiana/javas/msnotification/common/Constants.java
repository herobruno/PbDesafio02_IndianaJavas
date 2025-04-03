package indiana.javas.msnotification.common;

import indiana.javas.msnotification.dtos.EmailRecordDto;
import indiana.javas.msnotification.entities.EmailModel;

public class Constants {
    public static final EmailModel VALID_EMAIL = new EmailModel(
            1L,
            "email@email.com",
            "Subject",
            "Hello world"
    );


    public static final EmailModel INVALID_EMAIL = new EmailModel(
            1l,
            "email123",
            "Subject",
            "Hello world"
    );

    public static final EmailModel INVALID_SUBJECT = new EmailModel(
            1l,
            "email@email.com",
            "",
            "Hello world"
    );

    public static final EmailModel INVALID_TEXT = new EmailModel(
            1l,
            "email@email.com",
            "Subject",
            ""
    );

}


