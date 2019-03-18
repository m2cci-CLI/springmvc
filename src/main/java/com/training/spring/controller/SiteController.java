package com.training.spring.controller;

import com.training.spring.model.Site;
import com.training.spring.repository.CaptorDao;
import com.training.spring.repository.MeasureDao;
import com.training.spring.repository.SiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@Transactional
@RequestMapping(path = "/sites")
public class SiteController {

    @Autowired
    private SiteDao siteDao;
    @Autowired
    private CaptorDao captorDao;
    @Autowired
    private MeasureDao measureDao;

    @GetMapping
    public ModelAndView getAll(Model model) {
        ModelAndView mv = new ModelAndView("sites").addObject("sites", siteDao.findAll());
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView findById(@PathVariable String id) {
        return new ModelAndView("site-create").addObject("sites", siteDao.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/create")
    public ModelAndView create(Model model){
        return new ModelAndView("site-create").addObject("sites", new Site());
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView save(Site site) {
        if (site.getId() == null) {
            siteDao.save(site);
            return new ModelAndView("sites").addObject("sites", siteDao.findAll());
        } else {
            Site siteToPersist = siteDao.findById(site.getId()).orElseThrow(IllegalArgumentException::new);
            // L'utilisateur ne peut changer que le nom du site sur l'écran
            siteToPersist.setName(site.getName());
            return new ModelAndView("sites").addObject("sites", siteDao.findAll());
        }
    }

    @PostMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable String id) {
        // Comme les capteurs sont liés à un site et les mesures sont liées à un capteur,nous devons faire
        // le ménage avant pour ne pas avoir d'erreur à la suppression d'un site utilisé ailleurs dans la base
        Site site = siteDao.findById(id).orElseThrow(IllegalArgumentException::new);
        site.getCaptors().forEach(c -> measureDao.deleteByCaptorId(c.getId()));
        captorDao.deleteBySiteId(id);
        siteDao.deleteById(id);
        return new ModelAndView("sites").addObject("sites", siteDao.findAll());
    }

    @GetMapping("/{id}/measures")
    public ModelAndView findMeasuresById(@PathVariable String id) {
        Site site = siteDao.findById(id).orElseThrow(IllegalArgumentException::new);
        // Comme les templates ont une intelligence limitée on concatène ici les id de captor dans une chaine
        // de caractères qui pourra être exeploitée tel quelle
        String captors = site.getCaptors()
                .stream()
                .map(c -> "{ id: '" + c.getId() + "', name: '" + c.getName() + "'}")
                .collect(Collectors.joining(","));
        return new ModelAndView("site-measures")
                .addObject("site", site)
                .addObject("captors", captors);
    }
}
