package com.insider.login.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonController {

    /** 페이징 관련 DATA 가져오는 메서드 */
    public static Pageable getPageable(int page, int size, String sort, String direction) {
        return PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
    }

    /** 현재 시간 가져오는 메서드 */
    public String nowDate () {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        String nowDate = sdf.format(now);

        return nowDate;
    }

}
