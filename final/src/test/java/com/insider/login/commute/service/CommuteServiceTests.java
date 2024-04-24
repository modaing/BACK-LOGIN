package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

@SpringBootTest
public class CommuteServiceTests {

    @Autowired
    private CommuteService commuteService;

    private static Stream<Arguments> getStartWork() {
        return Stream.of(
                Arguments.of(
                        240106001,
                        LocalDate.now(),
                        LocalTime.of(8,50),
                        null,
                        "근무중",
                        null
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getStartWork")
    void testInsertTimeOfCommute(int memberId, LocalDate workingDate, LocalTime startWork, LocalTime endWork,
                                 String workingStatus, Duration totalWorkingHours) {
        //given
        CommuteDTO newCommute = new CommuteDTO(
                memberId,
                workingDate,
                startWork,
                endWork,
                workingStatus,
                totalWorkingHours
        );

        //when

        //then
        Assertions.assertDoesNotThrow(
                () -> commuteService.insertTimeOfCommute(newCommute)
        );
    }

}
