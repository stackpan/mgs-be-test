package io.github.stackpan.mgs_be_test.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePasswordDto {

    private String currentPassword;

    private String newPassword;

}
