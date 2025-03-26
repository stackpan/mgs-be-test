package io.github.stackpan.mgs_be_test.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UpdateUserPermissionDto {

    @NotNull
    Set<
            @Pattern(
                    regexp = "^(UPDATE_PROFILE|UPDATE_PASSWORD|UPLOAD_PROFILE_PICTURE)$",
                    message = "must be allowed values: 'UPDATE_PROFILE', 'UPDATE_PASSWORD', 'UPLOAD_PROFILE_PICTURE"
            )
                    String
            > permissions;

}
