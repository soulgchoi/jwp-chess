package chess.dto;

import javax.validation.constraints.Size;

public class TitleDTO {
    @Size(min = 1, max = 20, message = "방 이름은 1글자 이상 20글자 이하여야 합니다.")
    private String title;

    public TitleDTO() {

    }

    public TitleDTO(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}