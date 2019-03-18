package com.training.spring.controller;

import com.training.spring.controller.dto.MeasureDto;
import com.training.spring.model.*;
import com.training.spring.repository.CaptorDao;
import com.training.spring.service.measure.SimulatedMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping(path = "/measures/captors/{id}/last/hours/{nbHours}")
public class MeasureController {

    @Autowired
    private CaptorDao captorDao;
    @Autowired
    private SimulatedMeasureService simulatedMeasureService;

    @GetMapping
    public List<MeasureDto> getAllMeasures(Model model, @PathVariable String id, @PathVariable Long nbHours) {
        Captor captor = captorDao.findById(id).get();
        if (captor.getPowerSource() == PowerSource.SIMULATED) {
            return simulatedMeasureService.readMeasures(((SimulatedCaptor) captor),
                    Instant.now().minus(Duration.ofHours(nbHours)).truncatedTo(ChronoUnit.MINUTES),
                    Instant.now().truncatedTo(ChronoUnit.MINUTES),
                    MeasureStep.ONE_MINUTE)
                    .stream()
                    .map(m -> new MeasureDto(m.getInstant(), m.getValueInWatt()))
                    .collect(Collectors.toList());
        }
// Pour le moment on ne gère qu'un type
        return new ArrayList<>();
    }
}
