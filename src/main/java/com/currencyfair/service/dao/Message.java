package com.currencyfair.service.dao;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /*{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP",
            "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471,
            "timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"}*/

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private String currencyFrom;

    @Column(nullable = false)
    private String currencyTo;

    @Column(nullable = false)
    private double amountSell;

    @Column(nullable = false)
    private double amountBuy;

    @Column(nullable = false)
    private double rate;

    @Column(nullable = false)
    private LocalDateTime timePlaced;

    @Column(nullable = false)
    private String originatingCountry;

    @JsonSetter("timePlaced")
    public void setTimePlaced(String timePlaced) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive() .appendPattern("dd-MMM-yy HH:mm:ss").toFormatter(Locale.ENGLISH);
        this.timePlaced = LocalDateTime.parse(timePlaced, formatter);
    }



}
