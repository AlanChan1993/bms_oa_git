package com.infinitus.bms_oa.service.impl;


import com.infinitus.bms_oa.mapper.BmsBillAdujestMapper;
import com.infinitus.bms_oa.mapper.InfinitusMapper;
import com.infinitus.bms_oa.pojo.Infinitus;
import com.infinitus.bms_oa.service.InfinitusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InfinitusServiceImpl implements InfinitusService {

    @Autowired
    private InfinitusMapper mapper;

    @Autowired
    private BmsBillAdujestMapper bmsBillAdujestMapper;

    @Override
    public Infinitus getInfinitusById(Integer id) {
        return bmsBillAdujestMapper.getInfinitusBill(id);
    }
}
