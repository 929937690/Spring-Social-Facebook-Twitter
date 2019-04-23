package com.example.demo.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class TwitterController {

    @Autowired
    private Twitter twitter;

    private ConnectionRepository connectionRepository;

    @Inject
    public TwitterController(Twitter twitter, ConnectionRepository connectionRepository) {
        this.twitter = twitter;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("tweet")
    public String twitterFeed(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        //twitter.timelineOperations().updateStatus("1234");
        List<Tweet> tweet = twitter.timelineOperations().getUserTimeline();
        model.addAttribute("tweet", tweet);
        return "tweet";
    }

    @GetMapping("tfavourite")
    public String twitterFavourite(Model model) {
        if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
            return "redirect:/connect/twitter";
        }
        List<Tweet> favorites = twitter.timelineOperations().getFavorites();
        model.addAttribute("favorites", favorites);
        return "tfavourite";
    }


    @GetMapping("tfriends")
    public String twitterFriend(Model model){
        if(connectionRepository.findPrimaryConnection(Twitter.class)==null){
            return "redirect:/connect/twitter";
        }
        model.addAttribute(twitter.userOperations().getUserProfile());
        CursoredList<TwitterProfile> friends = twitter.friendOperations().getFriends();
        model.addAttribute("friends", friends);

        return "tfriends";
    }


}