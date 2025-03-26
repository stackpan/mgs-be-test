package io.github.stackpan.mgs_be_test.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfilePictureDto {

    @NotNull
    @Pattern(
            regexp = "^data:([a-zA-Z0-9+/.-]+/[a-zA-Z0-9+/.-]+);base64,(.+)$",
            message = "must be a valid base64 Data URI format"
    )
    private String profilePicture;

}
