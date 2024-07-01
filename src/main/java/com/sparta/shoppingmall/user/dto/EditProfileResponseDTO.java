package com.sparta.shoppingmall.user.dto;


import com.sparta.shoppingmall.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EditProfileResponseDTO {

    private final Long id;

    private final String username;

    private final String email;

    private final String address;

    private final LocalDateTime createAt;

    private final LocalDateTime updateAt;

    private int likedProductsCount;

    private int likedCommentsCount;


    public EditProfileResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.createAt = user.getCreateAt();
        this.updateAt = user.getUpdateAt();
    }

    public EditProfileResponseDTO(Long userId, String username, String email, String address, int likedProductsCount, int likedCommentsCount) {
        this.id = userId;
        this.username = username;
        this.email = email;
        this.address = address;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.likedProductsCount = likedProductsCount;
        this.likedCommentsCount = likedCommentsCount;
    }
}
