package com.womenhz.swee.indextemplate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserIndexTemplate {

    private Long userId;

    private String name;

    private String hobe;

    private String email;

    private String description;

    private String address;

    private String mobile;

}
