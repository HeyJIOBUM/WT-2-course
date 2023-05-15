package com.example.wt_lab4;
import lombok.Builder;
import lombok.Value;
@Value
@Builder
public class WelcomeDto {
    String browser;
    String lastVisit;
}
