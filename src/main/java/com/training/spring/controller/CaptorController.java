package com.training.spring.controller;

import com.training.spring.controller.dto.CaptorDto;
import com.training.spring.model.*;
import com.training.spring.repository.CaptorDao;
import com.training.spring.repository.MeasureDao;
import com.training.spring.repository.SiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping(path = "/sites/{siteId}/captors")
public class CaptorController {
    @Autowired
    private CaptorDao captorDao;
    @Autowired
    private SiteDao siteDao;
    @Autowired
    private MeasureDao measureDao;
    public CaptorController(CaptorDao captorDao, SiteDao siteDao) {
        this.captorDao = captorDao;
        this.siteDao = siteDao;
    }
    private CaptorDto toDto(Captor captor){
        if(captor instanceof FixedCaptor){
            return new CaptorDto(captor.getSite(), (FixedCaptor) captor);
        }
        if(captor instanceof SimulatedCaptor){
            return new CaptorDto(captor.getSite(), (SimulatedCaptor) captor);
        }
        if(captor instanceof RealCaptor){
            return new CaptorDto(captor.getSite(), (RealCaptor) captor);
        }
        throw new IllegalStateException("Captor type not managed by app");
    }
    private List<CaptorDto> toDtos(List<Captor> captors){
        return captors.stream()
                .map(this::toDto)
                .sorted(Comparator.comparing(CaptorDto::getName))
                .collect(Collectors.toList());
    }
    @GetMapping
    public ModelAndView findAll(@PathVariable String siteId) {
        return new ModelAndView("captor")
                .addObject("captors", toDtos(captorDao.findBySiteId(siteId)));
    }
    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable String siteId, @PathVariable String id)
    {
        Captor captor = captorDao.findById(id).orElseThrow(IllegalArgumentException::new);
        return new ModelAndView("captor").addObject("captor", toDto(captor));
    }
    @GetMapping("/create")
    public ModelAndView create(@PathVariable String siteId) {
        Site site =
                siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        return new ModelAndView("captor")
                .addObject("captor", new CaptorDto(site, new FixedCaptor(null, site, null)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView save(@PathVariable String siteId, CaptorDto captorDto) {
        Site site = siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new);
        Captor captor = captorDto.toCaptor(site);
        captorDao.save(captor);
        return new ModelAndView("sites").addObject("sites", site);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable String siteId, @PathVariable String id) {
        measureDao.deleteByCaptorId(id);
        captorDao.deleteById(id);
        return new ModelAndView("sites")
                .addObject("sites",
                        siteDao.findById(siteId).orElseThrow(IllegalArgumentException::new));
    }
}
