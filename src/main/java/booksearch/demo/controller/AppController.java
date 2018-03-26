package booksearch.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import booksearch.demo.model.RequestDto;
import booksearch.demo.service.BooksService;


@Controller
public class AppController {

    private final BooksService booksService;

    @Autowired
    public AppController(BooksService booksService) {

        this.booksService = booksService;
    }

    @GetMapping(value = "/search")
    public String searchForm() {
        return "search";
    }

    @PostMapping(value = "/searchOffer")
    public String getCheaperOffer(@ModelAttribute  RequestDto request, Model model) {
        model.addAttribute("offers", booksService.getCheaperBookOffer(request.getTitle()));

        return "result";
    }

}
