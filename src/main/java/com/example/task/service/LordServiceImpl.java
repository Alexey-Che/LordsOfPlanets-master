package com.example.task.service;

import com.example.task.converters.LordConverter;
import com.example.task.dto.LordDto;
import com.example.task.models.Lord;
import com.example.task.repository.LordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LordServiceImpl implements LordService {

    private final LordRepository lordRepository;
    private final LordConverter lordConverter;

    @Autowired
    public LordServiceImpl(LordRepository lordRepository, LordConverter lordConverter) {
        this.lordRepository = lordRepository;
        this.lordConverter = lordConverter;
    }

    @Override
    public LordDto createLord(LordDto lordDto) {
        return lordConverter.toDto(lordRepository.save(lordConverter.toEntity(lordDto)));
    }

    @Override
    public List<LordDto> findLordsWithoutPlanet() {
        return lordRepository.findAll()
                .stream()
                .filter(lord -> lord.getPlanets().isEmpty())
                .map(lordConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LordDto> findTenYoungestLords() {
        return lordRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Lord::getAge))
                .limit(10)
                .map(lordConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LordDto> findAll() {
        return lordRepository.findAll()
                .stream()
                .map(lordConverter::toDto)
                .collect(Collectors.toList());
    }
}
