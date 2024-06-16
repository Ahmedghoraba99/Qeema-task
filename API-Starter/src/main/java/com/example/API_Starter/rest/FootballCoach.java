package com.example.API_Starter.rest;

import org.springframework.stereotype.Component;

@Component
public class FootballCoach implements Coach {

    @Override
    public String getDailyWorkout() {
        return "Practice your tackles";
    }

    @Override
    public String getDailyFortune() {
        return "Go for the win";
    }

}// end class
