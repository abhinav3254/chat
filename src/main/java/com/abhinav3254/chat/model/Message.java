package com.abhinav3254.chat.model;


import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User sender;
    private String message;

    @OneToOne
    private User receiver;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
}
