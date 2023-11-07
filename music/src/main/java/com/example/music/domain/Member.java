package com.example.music.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "member")
public class Member{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Integer id;

    @NotNull
    private String password;
    private String nickname;

    private String accessToken;

}
