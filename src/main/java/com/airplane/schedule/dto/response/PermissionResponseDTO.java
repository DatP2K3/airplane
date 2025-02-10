package com.airplane.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResponseDTO {
    private int id;
    private String resourceId;
    private String scope;
    private int roleId;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String lastModifiedBy;
}
