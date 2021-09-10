package com.lista.teste.controller;

import com.lista.teste.model.Convidado;
import com.lista.teste.model.Evento;
import com.lista.teste.repository.ConvidadoRepository;
import com.lista.teste.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ControllerEvento {
    
    @Autowired
    private EventoRepository er;
    
    @Autowired
    private ConvidadoRepository cr;
    
    @RequestMapping(value="/cadastrarEvento" , method= RequestMethod.GET)
    public ModelAndView form(){
        return new ModelAndView("formEvento");
    }
    @RequestMapping(value="/cadastrarEvento" , method= RequestMethod.POST)
    public ModelAndView form(@Valid Evento evento,BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return new ModelAndView("redirect:/cadastrarEvento");
        }
        er.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento Cadastrado com Sucesso");
         return new ModelAndView("redirect:/cadastrarEvento");
    }
    
    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos",eventos);
        return mv;
    }
    @RequestMapping(value = "/{codigo}" , method= RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findById(codigo);
        ModelAndView mv = new ModelAndView("detalhesEvento");
        mv.addObject("evento",evento);
        Iterable<Convidado> convidado = cr.findAllByEvento(evento);
        mv.addObject("convidados", convidado);
        return mv;
    }
    
    @RequestMapping("/deletar")
    public ModelAndView deletarEvento(long codigo){
        Evento evento = er.findById(codigo);
        er.delete(evento);
        return new ModelAndView("redirect:/eventos");
    }
    
    @RequestMapping(value = "/{codigo}" , method= RequestMethod.POST)
    public ModelAndView detalhesEventoPost(@PathVariable("codigo") long codigo ,@Valid Convidado convidado ,BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return new ModelAndView("redirect:/{codigo}");
        }
        Evento evento = er.findById(codigo);
        convidado.setEvento(evento);
        cr.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado Adicionado com sucesso");
        ModelAndView mv = new ModelAndView("redirect:/{codigo}");
        return mv;
    }
    
    @RequestMapping("/deletarConvidado")
    public ModelAndView deletarConvidado(String rg){
        Convidado convidado = cr.findByRg(rg);
        cr.delete(convidado);
        Evento evento = convidado.getEvento();
        
        return new ModelAndView("redirect:/"+String.valueOf(evento.getId()));
    }
    
}
