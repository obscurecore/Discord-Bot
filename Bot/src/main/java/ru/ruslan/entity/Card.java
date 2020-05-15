package ru.ruslan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private StringBuffer question;
    private StringBuffer answer;

}

