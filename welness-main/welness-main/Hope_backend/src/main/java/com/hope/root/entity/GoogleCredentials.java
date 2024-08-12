package com.hope.root.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "google_credentials")
@NamedQuery(name = "GoogleCredentials.findAll", query = "SELECT d FROM GoogleCredentials d")
public class GoogleCredentials implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "access_token", nullable = false)
    private String accessToken;
    @Column(name = "token_type", nullable = false)
    private String tokenType;
    @Column(name = "expires_in", nullable = false)
    private Integer expiresIn;
    @Column(name = "scope", nullable = false)
    private String scope;

    public GoogleCredentials() {

    }
}
