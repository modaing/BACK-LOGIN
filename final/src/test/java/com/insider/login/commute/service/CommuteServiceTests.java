package com.insider.login.commute.service;

import com.insider.login.commute.dto.CommuteDTO;
import com.insider.login.commute.dto.UpdateTimeOfCommuteDTO;
import com.insider.login.commute.entity.Commute;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
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

    @DisplayName("출근 시간 등록 테스트")
    @ParameterizedTest
    @MethodSource("getStartWork")
    @Transactional
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

//    private static Stream<Arguments> getEndWork() {
//        return Stream.of(
//                Arguments.of(
//
//                )
//        );
//    }

//    @MethodSource("getEndWork")
    @DisplayName("퇴근 시간 등록 테스트")
    @Test
    void testUpdateTimeOfCommuteByCommuteNo() {
        //given
        int commuteNo = 1;
        LocalTime endWork = LocalTime.of(18,00);
        String workingStatus = "퇴근";
        Duration totalWorkingHours = Duration.between(LocalTime.of(8,50), endWork).minusHours(60);

        UpdateTimeOfCommuteDTO updateTimeOfCommute = new UpdateTimeOfCommuteDTO(
                commuteNo,
                endWork,
                workingStatus,
                totalWorkingHours
        );

        //when
//        Commute updateCommute = commuteService.updateTimeOfCommuteByCommuteNo(updateTimeOfCommute);

        //then
//        Assertions.assertDoesNotThrow(
//                () -> commuteService.updateTimeOfCommuteByCommuteNo()
//        );

    }

}
